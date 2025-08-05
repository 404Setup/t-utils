package one.pkg.tiny.utils.compress;

import one.pkg.tiny.utils.Reflect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BrotliCompress implements ICompress {
    private static final ICompress INSTANCE = new BrotliCompress();
    private final boolean hasDependency = Reflect.hasClass("com.aayushatharva.brotli4j.decoder.BrotliInputStream");

    private BrotliCompress() {
    }

    public static ICompress getInstance() {
        return INSTANCE;
    }

    protected void checkDependencyPresent(String operation) throws IOException {
        if (!hasDependency)
            throw new IOException(operation + " requires the 'com.aayushatharva.brotli4j:brotli4j' library to be present on the classpath");
    }

    @Override
    public InputStream createDecompressStream(InputStream is) throws IOException {
        checkDependencyPresent("BROTLI decompression");
        return new com.aayushatharva.brotli4j.decoder.BrotliInputStream(is);
    }

    @Override
    public OutputStream createCompressStream(OutputStream os) throws IOException {
        checkDependencyPresent("BROTLI compression");
        return new com.aayushatharva.brotli4j.encoder.BrotliOutputStream(os);
    }

    @Override
    public String getFileExtension() {
        return ".zst";
    }
}
