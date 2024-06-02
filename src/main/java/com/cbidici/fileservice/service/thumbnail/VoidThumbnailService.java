package com.cbidici.fileservice.service.thumbnail;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class VoidThumbnailService extends ThumbnailService {

  private final static Set<FileType> SUPPORTED = Set.of(
      FileType.UNSUPPORTED, FileType.DIRECTORY
  );

  public VoidThumbnailService(AppConfig appConfig) {
    super(appConfig);
  }

  @Override
  public boolean isSupported(FileType fileType) {
    return SUPPORTED.contains(fileType);
  }

  @Override
  public void generate(Path filePath, Path thumbnailPath, Dimension thumbnailDimension) {

  }

}
