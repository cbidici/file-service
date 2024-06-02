package com.cbidici.fileservice.exception;

public class FileServiceFileNotFoundException extends RuntimeException {
    public FileServiceFileNotFoundException(String path) {
        super(String.format("File Not Found %s", path));
    }
}
