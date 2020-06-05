package com.omvoid.community.exception;

public class GraphReaderException extends Exception{

    public GraphReaderException() {
        super();
    }

    public GraphReaderException(String message) {
        super(message);
    }

    public GraphReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphReaderException(Throwable cause) {
        super(cause);
    }
}
