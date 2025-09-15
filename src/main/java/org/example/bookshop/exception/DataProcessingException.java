package org.example.bookshop.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String messege, Throwable cause) {
        super(messege, cause);
    }

    public DataProcessingException(String messege) {
        super(messege);
    }
}
