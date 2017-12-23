package pl.net.malinowski.travelagency.controller.exceptions;

import java.io.IOException;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable e) {
        super(message, e);
    }
}
