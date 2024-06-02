package com.cbidici.fileservice.exception;

public class FileServiceException extends RuntimeException {
  public FileServiceException(Throwable t) {
    super(t);
  }

  public FileServiceException(String message) {
    super(message);
  }

  public FileServiceException(String message, Throwable t) {
    super(message, t);
  }
}
