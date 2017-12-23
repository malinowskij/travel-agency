package pl.net.malinowski.travelagency.controller.exceptions;

import java.io.IOException;

public class StorageNotFoundException extends RuntimeException {
    public StorageNotFoundException(String filename) {
        super(filename);
    }

    public StorageNotFoundException(String filename, Throwable e) {
        super(filename, e);
    }
}
