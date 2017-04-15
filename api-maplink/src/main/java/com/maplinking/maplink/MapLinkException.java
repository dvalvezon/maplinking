package com.maplinking.maplink;

public class MapLinkException extends Exception {

    public MapLinkException() {
        super();
    }

    public MapLinkException(String message) {
        super(message);
    }

    public MapLinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapLinkException(Throwable cause) {
        super(cause);
    }

    protected MapLinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
