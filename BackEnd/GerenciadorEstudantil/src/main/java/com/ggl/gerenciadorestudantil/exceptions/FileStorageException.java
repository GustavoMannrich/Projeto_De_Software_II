package com.ggl.gerenciadorestudantil.exceptions;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 7399831594384826105L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
