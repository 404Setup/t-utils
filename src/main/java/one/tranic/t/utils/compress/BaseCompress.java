package one.tranic.t.utils.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public enum BaseCompress implements ICompress {
    DEFLATE(".deflate") {
        @Override
        public InputStream createDecompressStream(InputStream is) {
            return new InflaterInputStream(is);
        }

        @Override
        public OutputStream createCompressStream(OutputStream os) {
            return new DeflaterOutputStream(os);
        }
    },
    GZIP(".gz") {
        @Override
        public InputStream createDecompressStream(InputStream is) throws IOException {
            return new GZIPInputStream(is);
        }

        @Override
        public OutputStream createCompressStream(OutputStream os) throws IOException {
            return new GZIPOutputStream(os);
        }
    };

    final String fileExtension;

    BaseCompress(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }
}
