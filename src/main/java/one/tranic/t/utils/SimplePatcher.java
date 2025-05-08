package one.tranic.t.utils;

import org.jetbrains.annotations.ApiStatus;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@ApiStatus.Experimental
// This class has not been tested for usability, please do not use it!
public class SimplePatcher {
    private static final int CHUNK_SIZE = 4096;
    private static final byte COMMAND_EQUAL = 0;
    private static final byte COMMAND_INSERT = 1;
    private static final byte COMMAND_DELETE = 2;

    /**
     * Creates a binary patch that transforms the contents of the source file into the destination file.
     *
     * @param src the InputStream representing the source file's data
     * @param dst the InputStream representing the destination file's data
     * @return an OutputStream containing the binary patch data
     * @throws IOException if an I/O error occurs while reading from the input streams or writing to the patch
     */
    public static OutputStream createPatch(InputStream src, InputStream dst) throws IOException {
        ByteArrayOutputStream patchOutputStream = new ByteArrayOutputStream();
        DataOutputStream patch = new DataOutputStream(patchOutputStream);

        byte[] srcData = readAllBytes(src);
        byte[] dstData = readAllBytes(dst);

        // Patch header
        patch.writeInt(srcData.length);
        patch.writeInt(dstData.length);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] srcHash = md.digest(srcData);
            patch.write(srcHash);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Failed to calculate MD5 checksum", e);
        }

        int i = 0, j = 0;

        while (i < srcData.length || j < dstData.length) {
            int matchLength = 0;
            while (i + matchLength < srcData.length &&
                    j + matchLength < dstData.length &&
                    srcData[i + matchLength] == dstData[j + matchLength]) {
                matchLength++;
            }

            if (matchLength > 0) {
                patch.writeByte(COMMAND_EQUAL);
                patch.writeInt(matchLength);

                i += matchLength;
                j += matchLength;
            } else {
                int srcDiffStart = i;
                int dstDiffStart = j;

                while (true) {
                    boolean foundMatch = false;

                    for (int lookAhead = 1; lookAhead < 32 && i + lookAhead < srcData.length && j < dstData.length; lookAhead++) {
                        if (srcData[i + lookAhead] == dstData[j]) {
                            i += lookAhead;
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch) break;

                    for (int lookAhead = 1; lookAhead < 32 && i < srcData.length && j + lookAhead < dstData.length; lookAhead++) {
                        if (srcData[i] == dstData[j + lookAhead]) {
                            j += lookAhead;
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch) break;

                    if (i < srcData.length) i++;
                    if (j < dstData.length) j++;

                    if (i >= srcData.length && j >= dstData.length) break;
                }

                if (i > srcDiffStart) {
                    patch.writeByte(COMMAND_INSERT);
                    patch.writeInt(i - srcDiffStart);
                    patch.write(srcData, srcDiffStart, i - srcDiffStart);
                }

                if (j > dstDiffStart) {
                    patch.writeByte(COMMAND_DELETE);
                    patch.writeInt(j - dstDiffStart);
                    patch.write(dstData, dstDiffStart, j - dstDiffStart);
                }
            }
        }

        patch.flush();
        return patchOutputStream;
    }

    /**
     * Applies a patch to a target file and returns the resulting file as an output stream.
     *
     * @param patch the input stream containing the patch data to be applied
     * @param dst   the input stream of the target file that will be patched
     * @return an output stream containing the patched file contents
     * @throws IOException           if an I/O error occurs while reading or writing streams
     * @throws IllegalStateException if the patch does not match the target file,
     *                               is corrupted, or contains invalid commands
     */
    public static OutputStream applyPatch(InputStream patch, InputStream dst) throws IOException {
        DataInputStream patchInput = new DataInputStream(patch);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] dstData = readAllBytes(dst);

        int originalSrcSize = patchInput.readInt();
        int originalDstSize = patchInput.readInt();

        if (dstData.length != originalDstSize) {
            throw new IllegalStateException("Patch not applicable to target file: size mismatch, expected " +
                    originalDstSize + ", actual " + dstData.length);
        }

        byte[] expectedMD5 = new byte[16];
        patchInput.readFully(expectedMD5);

        int dstPos = 0;

        try {
            while (patchInput.available() > 0) {
                byte command = patchInput.readByte();

                switch (command) {
                    case COMMAND_EQUAL:
                        int equalLength = patchInput.readInt();
                        if (dstPos + equalLength > dstData.length) {
                            throw new IllegalStateException("Patch application failed: exceeded target file boundary");
                        }
                        output.write(dstData, dstPos, equalLength);
                        dstPos += equalLength;
                        break;

                    case COMMAND_INSERT:
                        int insertLength = patchInput.readInt();
                        byte[] insertData = new byte[insertLength];
                        patchInput.readFully(insertData);
                        output.write(insertData);
                        break;

                    case COMMAND_DELETE:
                        int deleteLength = patchInput.readInt();
                        byte[] expectedDeleteData = new byte[deleteLength];
                        patchInput.readFully(expectedDeleteData);

                        if (dstPos + deleteLength > dstData.length) {
                            throw new IllegalStateException("Patch application failed: exceeded target file boundary");
                        }

                        byte[] actualDeleteData = Arrays.copyOfRange(dstData, dstPos, dstPos + deleteLength);
                        if (!Arrays.equals(expectedDeleteData, actualDeleteData)) {
                            throw new IllegalStateException("Patch application failed: target file content mismatch, cannot apply patch");
                        }

                        dstPos += deleteLength;
                        break;

                    default:
                        throw new IllegalStateException("Patch file format error: unknown command code " + command);
                }
            }

            if (dstPos != dstData.length) {
                throw new IllegalStateException("Patch application failed: target file not fully processed");
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] resultMD5 = md.digest(output.toByteArray());

            if (!Arrays.equals(expectedMD5, resultMD5)) {
                throw new IllegalStateException("Patch application failed: file checksum mismatch, patch may be corrupted");
            }

            if (output.size() != originalSrcSize) {
                Logger.getGlobal().warning("Warning: Size mismatch after applying patch, expected " +
                        originalSrcSize + ", actual " + output.size());
            }

        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Failed to calculate MD5 checksum", e);
        }

        return output;
    }

    /**
     * Reads all the bytes from the provided InputStream and returns them as a byte array.
     *
     * @param is the InputStream to read data from
     * @return a byte array containing all the data read from the InputStream
     * @throws IOException if an I/O error occurs while reading the InputStream
     */
    private static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[CHUNK_SIZE];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }
}