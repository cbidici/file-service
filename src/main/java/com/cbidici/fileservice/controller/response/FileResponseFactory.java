package com.cbidici.fileservice.controller.response;

import com.cbidici.fileservice.entity.FileDomain;
import org.springframework.stereotype.Service;

@Service
public class FileResponseFactory {

  public FileResponse getFileResponse(FileDomain fileDomain) {
    return FileResponse.builder()
        .id(fileDomain.getId())
        .name(fileDomain.getName())
        .type(fileDomain.getType().name())
        .build();
  }
}
