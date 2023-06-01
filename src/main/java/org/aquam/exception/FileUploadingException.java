package org.aquam.exception;

public class FileUploadingException extends RuntimeException {
    public FileUploadingException() {
        super();
    }

    public FileUploadingException(String message) {
        super(message);
    }
}
