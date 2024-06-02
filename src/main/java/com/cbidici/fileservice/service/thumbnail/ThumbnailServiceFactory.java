package com.cbidici.fileservice.service.thumbnail;

import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.exception.FileServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThumbnailServiceFactory {

  private final List<ThumbnailService> thumbnailServices;

  public ThumbnailService getByFileType(FileType type) {
    return thumbnailServices.stream()
        .filter(service -> service.isSupported(type))
        .findFirst()
        .orElseThrow(() -> new FileServiceException("Thumbnail service could not be located for file type " + type.name()));
  }

}
