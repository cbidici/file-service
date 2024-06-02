package com.cbidici.fileservice.service.preview;

import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.exception.FileServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreviewServiceFactory {

  private final List<PreviewService> previewServices;

  public PreviewService getByFileType(FileType type) {
    return previewServices.stream()
        .filter(service -> service.isSupported(type))
        .findFirst()
        .orElseThrow(() -> new FileServiceException("Preview service could not be located for file type " + type.name()));
  }

}
