package one.pkg.tiny.utils.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ICompress {
    int BUFFER_SIZE = 1024;

    static void copyStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
    }

    default void decompress(InputStream is, OutputStream os) throws IOException {
        try (InputStream decompressStream = createDecompressStream(is)) {
            copyStream(decompressStream, os);
        }
    }

    default void compress(InputStream is, OutputStream os) throws IOException {
        try (OutputStream compressStream = createCompressStream(os)) {
            copyStream(is, compressStream);
        }
    }

    InputStream createDecompressStream(InputStream is) throws IOException;

    OutputStream createCompressStream(OutputStream os) throws IOException;

    String getFileExtension();
}
