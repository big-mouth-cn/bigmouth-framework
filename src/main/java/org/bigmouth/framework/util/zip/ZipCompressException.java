package org.bigmouth.framework.util.zip;


public class ZipCompressException extends Exception {

    private static final long serialVersionUID = -6582563562593218040L;

    public ZipCompressException() {
        super();
    }

    public ZipCompressException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZipCompressException(String message) {
        super(message);
    }

    public ZipCompressException(Throwable cause) {
        super(cause);
    }
}
