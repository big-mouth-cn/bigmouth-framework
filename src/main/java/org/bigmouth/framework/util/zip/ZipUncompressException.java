package org.bigmouth.framework.util.zip;


public class ZipUncompressException extends Exception {

    private static final long serialVersionUID = -6541552258328046686L;

    public ZipUncompressException() {
        super();
    }

    public ZipUncompressException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZipUncompressException(String message) {
        super(message);
    }

    public ZipUncompressException(Throwable cause) {
        super(cause);
    }
}
