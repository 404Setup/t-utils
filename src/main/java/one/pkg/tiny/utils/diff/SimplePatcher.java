package one.pkg.tiny.utils.diff;

import one.pkg.tiny.utils.compress.ICompress;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * SimplePatcher is just an accidental byproduct, and I don't recommend anyone to use it.
 * <p>
 * Example:
 * <pre>{@code
 * package one.tranic.goldpiglin.command;
 *
 * import one.tranic.goldpiglin.GoldPiglin;
 * import one.tranic.goldpiglin.common.GoldPiglinLogger;
 * import one.tranic.t.utils.SimplePatcher;
 * import org.bukkit.command.Command;
 * import org.bukkit.command.CommandSender;
 * import org.bukkit.plugin.java.JavaPlugin;
 * import org.jetbrains.annotations.NotNull;
 *
 * import java.io.ByteArrayOutputStream;
 * import java.io.FileInputStream;
 * import java.io.IOException;
 * import java.io.OutputStream;
 * import java.nio.file.Files;
 * import java.nio.file.Path;
 * import java.util.ArrayList;
 * import java.util.List;
 * import java.util.stream.Stream;
 *
 * public class DiffCommand extends Command {
 *     private final static List<String> INTERNAL_ERROR = List.of("Internal error");
 *     private final static Path DIFF_DIR = GoldPiglin.getPlugin().getDataFolder().toPath().getParent().resolve("diff");
 *     private final static Path Plugin_DIR = GoldPiglin.getPlugin().getDataFolder().toPath().getParent();
 *
 *     public DiffCommand(JavaPlugin plugin) {
 *         super("diff");
 *         this.setUsage("/diff <create> jar1 jar2 | /diff <merge> patch jar");
 *
 *         if (!DIFF_DIR.toFile().exists()) {
 *             try {
 *                 Files.createDirectories(DIFF_DIR);
 *             } catch (IOException e) {
 *                 e.printStackTrace();
 *             }
 *         }
 *     }
 *
 *     private static List<String> getFiles(String prefix) {
 *         try (final Stream<Path> files = Files.list(Plugin_DIR)) {
 *             return files.filter(path ->
 *                             path.toString().endsWith("." + prefix) && path.toFile().isFile()
 *                     )
 *                     .map(Path::getFileName)
 *                     .map(Path::toString).toList();
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *             return INTERNAL_ERROR;
 *         }
 *     }
 *
 *     public static List<String> getJarFiles() {
 *         return getFiles("jar");
 *     }
 *
 *     public static List<String> getDiffFiles() {
 *         return getFiles("diff");
 *     }
 *
 *     @Override
 *     public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
 *         if (args.length == 0) {
 *             return true;
 *         }
 *         if (args[0].equalsIgnoreCase("create")) {
 *             if (args.length < 3) {
 *                 sender.sendMessage("Usage: /diff create <jar1> <jar2>");
 *                 return true;
 *             }
 *             return runCreateDiff(sender, commandLabel, args);
 *         }
 *         if (args[0].equalsIgnoreCase("merge")) {
 *             if (args.length < 3) {
 *                 sender.sendMessage("Usage: /diff merge <patch> <jar>");
 *                 return true;
 *             }
 *             return runMergeDiff(sender, commandLabel, args);
 *         }
 *         return true;
 *     }
 *
 *     private boolean runCreateDiff(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
 *         GoldPiglinLogger.logger.info("Args: {}", String.join(" ", args));
 *         var jar1 = args[1];
 *         var jar2 = args[2];
 *
 *         var j1 = Plugin_DIR.resolve(jar1).toFile();
 *         var j2 = Plugin_DIR.resolve(jar2).toFile();
 *
 *         if (!j1.exists() || !j2.exists()) {
 *             sender.sendMessage("One of the jars does not exist");
 *             return true;
 *         }
 *
 *         try {
 *             var diff = DIFF_DIR.resolve(jar1 + "-" + jar2 + ".diff");
 *             var diffFile = diff.toFile();
 *             if (!diffFile.exists()) {
 *                 diffFile.createNewFile();
 *             } else {
 *                 diffFile.delete();
 *                 diffFile.createNewFile();
 *             }
 *             try (var fis1 = new FileInputStream(j1); var fis2 = new FileInputStream(j2)) {
 *                 try (ByteArrayOutputStream output = (ByteArrayOutputStream) SimplePatcher.createPatch(fis1, fis2); OutputStream fos = Files.newOutputStream(diff)) {
 *                     output.writeTo(fos);
 *                     fos.flush();
 *                 }
 *                 sender.sendMessage("Diff created at " + diff.toAbsolutePath());
 *             }
 *
 *         } catch (IOException e) {
 *             sender.sendMessage("Error: " + e.getMessage());
 *             e.printStackTrace();
 *         }
 *
 *         return true;
 *     }
 *
 *     private boolean runMergeDiff(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
 *         GoldPiglinLogger.logger.info("Args: {}", String.join(" ", args));
 *         var patch = args[1];
 *         var jar = args[2];
 *
 *         var p = DIFF_DIR.resolve(patch).toFile();
 *         var j = Plugin_DIR.resolve(jar).toFile();
 *         var j2 = Plugin_DIR.resolve(jar + ".new.jar");
 *         try {
 *             j2.toFile().createNewFile();
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *             sender.sendMessage("Error: " + e.getMessage());
 *             return true;
 *         }
 *
 *         try (var pS = new FileInputStream(p); var jS = new FileInputStream(j)) {
 *             try (ByteArrayOutputStream fps = (ByteArrayOutputStream) SimplePatcher.applyPatch(pS, jS); var fos = Files.newOutputStream(j2)) {
 *                 fps.writeTo(fos);
 *                 fos.flush();
 *             }
 *         } catch (Exception exception) {
 *             sender.sendMessage("Error: " + exception.getMessage());
 *             exception.printStackTrace();
 *             return true;
 *         }
 *
 *         return false;
 *     }
 *
 *     @Override
 *     public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
 *         List<String> list = new ArrayList<>();
 *         if (args.length == 1) {
 *             list.add("create");
 *             list.add("merge");
 *         }
 *         if (args.length == 2) {
 *             if (args[0].equalsIgnoreCase("create"))
 *                 list.addAll(getJarFiles());
 *             if (args[0].equalsIgnoreCase("merge"))
 *                 list.addAll(getDiffFiles());
 *         }
 *         if (args.length == 3 && (args[0].equalsIgnoreCase("merge") || args[0].equalsIgnoreCase("create"))) {
 *             list.addAll(getJarFiles());
 *         }
 *         return list;
 *     }
 * }
 * }</pre>
 */
@SuppressWarnings("unused")
@ApiStatus.Experimental
public class SimplePatcher {
    private static final int CHUNK_SIZE = 4096;
    private static final byte COMMAND_EQUAL = 0;
    private static final byte COMMAND_INSERT = 1;
    private static final byte COMMAND_DELETE = 2;

    /**
     * Creates a binary patch that transforms the contents of the source file into the destination file.
     *
     * @param newFile the InputStream representing the source file's data
     * @param oldFile the InputStream representing the destination file's data
     * @return an OutputStream containing the binary patch data
     * @throws IOException if an I/O error occurs while reading from the input streams or writing to the patch
     */
    public static OutputStream createPatch(InputStream newFile, InputStream oldFile) throws IOException {
        ByteArrayOutputStream patchOutputStream = new ByteArrayOutputStream();
        DataOutputStream patch = new DataOutputStream(patchOutputStream);

        byte[] srcData = readAllBytes(newFile);
        byte[] dstData = readAllBytes(oldFile);

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
     * Creates a binary patch file that defines the changes required to transform the contents
     * of the old file into the new file.
     * <p>
     * The patch is stored in the specified patch file.
     *
     * @param newFile   the file containing the target state after applying the patch
     * @param oldFile   the file containing the original state before applying the patch
     * @param patchFile the file where the generated patch will be saved; must not yet exist
     * @throws IOException if any of the input files do not exist, if the patch file already exists,
     *                     or if an I/O error occurs while reading or writing
     */
    public static void createPatch(File newFile, File oldFile, File patchFile) throws IOException {
        createPatch(newFile, oldFile, patchFile, null);
    }

    /**
     * Creates a binary patch file that describes the changes required to transform the contents
     * of the old file into the new file.
     * <p>
     * The patch is written to the specified patch file, with optional compression applied.
     *
     * @param newFile     the file containing the target state after applying the patch
     * @param oldFile     the file containing the original state before applying the patch
     * @param patchFile   the file where the generated patch will be saved; must not yet exist
     * @param compression the compression method to be applied to the patch output; may be null if no compression is desired
     * @throws IOException if any of the input files do not exist, if the patch file already exists,
     *                     or if an I/O error occurs while reading or writing files
     */
    public static void createPatch(File newFile, File oldFile, File patchFile,
                                   @Nullable ICompress compression) throws IOException {
        validatePatchFiles(newFile, oldFile, patchFile);

        try (InputStream newStream = new FileInputStream(newFile);
             InputStream oldStream = new FileInputStream(oldFile);
             OutputStream patchStream = new FileOutputStream(patchFile)) {

            ByteArrayOutputStream patchData = (ByteArrayOutputStream) createPatch(newStream, oldStream);
            processPatchData(patchData, patchStream, compression, true);
        }
    }

    /**
     * Writes the patch data to the specified output stream, optionally compressing it.
     * If a compression method is provided, the patch data will be compressed before writing
     * to the output stream.
     * <p>
     * Otherwise, the raw patch data will be written directly.
     *
     * @param patchData    the {@link ByteArrayOutputStream} containing the patch data to be written
     * @param outputStream the {@link OutputStream} where the patch data will be written
     * @param compress     the {@link ICompress} instance defining the compression algorithm to use;
     *                     if null, no compression will be applied
     * @throws IOException if an I/O error occurs during writing or compression
     */
    private static void writePatchData(ByteArrayOutputStream patchData,
                                       OutputStream outputStream,
                                       @Nullable ICompress compress) throws IOException {
        if (compress != null) {
            try (var patchInputStream = new ByteArrayInputStream(patchData.toByteArray())) {
                compress.compress(patchInputStream, outputStream);
            }
        } else {
            patchData.writeTo(outputStream);
        }
        outputStream.flush();
    }

    /**
     * Creates a temporary binary patch file that represents the differences between
     * the specified new file and old file.
     * <p>
     * The temporary patch file is deleted if an exception occurs during its creation.
     *
     * @param newFile the file representing the new version of the content
     * @param oldFile the file representing the old version of the content
     * @return a temporary file containing the binary patch data
     * @throws IOException if an I/O error occurs, such as issues reading the input files,
     *                     writing the patch file, or if any of the required files does not exist
     */
    public static File createPatch(File newFile, File oldFile) throws IOException {
        File patchFile = File.createTempFile("diff", ".sdiff");
        try {
            createPatch(newFile, oldFile, patchFile);
            return patchFile;
        } catch (IOException e) {
            patchFile.delete();
            throw new IOException(e);
        }
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
     * Applies a binary patch to a target file and writes the patched content to an output file.
     *
     * @param patch      the patch file containing the binary data with instructions for applying the patch
     * @param dst        the target file to which the patch will be applied
     * @param outputFile the file where the patched content will be written; must not yet exist
     * @throws IOException if the patch file does not exist, the target file does not exist, the output
     *                     file already exists, or if an I/O error occurs during the process
     */
    public static void applyPatch(File patch, File dst, File outputFile) throws IOException {
        applyPatch(patch, dst, outputFile, null);
    }

    public static void applyPatch(File patchFile, File targetFile, File outputFile,
                                  @Nullable ICompress compression) throws IOException {
        validateFiles(patchFile, targetFile, outputFile);

        try (InputStream patchStream = new FileInputStream(patchFile);
             InputStream targetStream = new FileInputStream(targetFile);
             OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {

            ByteArrayOutputStream processedPatch = new ByteArrayOutputStream();
            processPatchData(patchStream, processedPatch, compression, false);

            try (InputStream processedPatchStream = new ByteArrayInputStream(processedPatch.toByteArray())) {
                ByteArrayOutputStream patchedContent =
                        (ByteArrayOutputStream) applyPatch(processedPatchStream, targetStream);
                patchedContent.writeTo(outputStream);
                outputStream.flush();
            }
        }
    }


    /**
     * Applies a binary patch file to a specified destination file and generates a new file with the patched content.
     *
     * @param patch the file containing the patch data to be applied
     * @param dst   the file representing the target to which the patch will be applied
     * @return a temporary file containing the patched content after applying the patch
     * @throws IOException if an I/O error occurs during patch application, such as issues reading or writing files,
     *                     if the patch or destination file does not exist,
     *                     or if the generated output file cannot be created
     */
    public static File applyPath(File patch, File dst) throws IOException {
        File outputFile = File.createTempFile("diff", ".tmp");
        try {
            applyPatch(patch, dst, outputFile);
            return outputFile;
        } catch (IOException e) {
            outputFile.delete();
            throw new IOException(e);
        }
    }

    /**
     * Validates the existence and state of the provided files involved in the patching process.
     *
     * @param patchFile       the file containing the patch data to be validated
     * @param destinationFile the file to which the patch will be applied
     * @param outputFile      the file where the patched content will be written;
     * @throws IOException if the patch file or destination file does not exist, the output file already
     *                     exists and does not have a ".tmp" extension, or if an error occurs while
     *                     creating a new output file
     */
    private static void validateFiles(File patchFile, File destinationFile, File outputFile) throws IOException {
        if (!patchFile.exists()) throw new IOException("Patch file does not exist");
        if (!destinationFile.exists()) throw new IOException("Destination file does not exist");

        if (!outputFile.getName().endsWith(".tmp")) {
            if (outputFile.exists()) throw new IOException("Output file already exists");
            else outputFile.createNewFile();
        }
    }

    /**
     * Validates the provided files for creating or handling a binary patch.
     *
     * @param newFile   the file that represents the target state after applying the patch
     * @param oldFile   the file that represents the original state before applying the patch
     * @param patchFile the file where the generated patch will be saved;
     * @throws IOException if the new file or old file does not exist, if the patch file already exists and has an invalid name,
     *                     or if an I/O error occurs during file creation
     */
    private static void validatePatchFiles(File newFile, File oldFile, File patchFile) throws IOException {
        if (!newFile.exists()) throw new IOException("New file does not exist");
        if (!oldFile.exists()) throw new IOException("Old file does not exist");
        if (!patchFile.getName().endsWith(".sdiff")) {
            if (patchFile.exists()) throw new IOException("Patch file already exists");
            else patchFile.createNewFile();
        }
    }

    /**
     * Processes the patch data by either compressing or writing the input directly to the output stream.
     *
     * @param input         the data source, which can be an InputStream or ByteArrayOutputStream, to be processed
     * @param output        the OutputStream to which the processed data will be written
     * @param compression   the optional Compress instance defining the compression or decompression method;
     *                      may be null if no compression is required
     * @param isCompressing a boolean indicating whether to compress or decompress the data if a
     *                      compression method is provided
     * @throws IOException if an I/O error occurs while reading from the input or writing to the output
     */
    private static void processPatchData(Object input, OutputStream output,
                                         @Nullable ICompress compression,
                                         boolean isCompressing) throws IOException {
        if (compression == null) {
            if (input instanceof ByteArrayOutputStream baos) {
                baos.writeTo(output);
            } else if (input instanceof InputStream is) {
                byte[] buffer = new byte[CHUNK_SIZE];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
            }
        } else {
            if (isCompressing) {
                try (ByteArrayInputStream dataStream =
                             input instanceof ByteArrayOutputStream ?
                                     new ByteArrayInputStream(((ByteArrayOutputStream) input).toByteArray()) :
                                     (ByteArrayInputStream) input) {
                    compression.compress(dataStream, output);
                }
            } else {
                compression.decompress((InputStream) input, output);
            }
        }
        output.flush();
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