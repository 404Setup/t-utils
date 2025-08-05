package one.pkg.tiny.utils.compress;

import one.pkg.tiny.utils.Reflect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZstdCompress implements ICompress {
    private static final ICompress INSTANCE = new ZstdCompress();
    private final boolean hasDependency = Reflect.hasClass("com.github.luben.zstd.ZstdDecompressCtx");

    private ZstdCompress() {
    }

    public static ICompress getInstance() {
        return INSTANCE;
    }

    protected void checkDependencyPresent(String operation) throws IOException {
        if (!hasDependency)
            throw new IOException(operation + " requires the 'com.github.luben:zstd-jni' library to be present on the classpath");
    }

    @Override
    public InputStream createDecompressStream(InputStream is) throws IOException {
        checkDependencyPresent("ZSTD decompression");
        return new com.github.luben.zstd.ZstdInputStream(is);
    }

    @Override
    public OutputStream createCompressStream(OutputStream os) throws IOException {
        checkDependencyPresent("ZSTD compression");
        return new com.github.luben.zstd.ZstdOutputStream(os);
    }

    @Override
    public String getFileExtension() {
        return ".zst";
    }
}
