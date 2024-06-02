package com.cbidici.fileservice.service.thumbnail;

import static com.cbidici.fileservice.entity.FileType.*;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.service.media.ImageService;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ImageThumbnailService extends ThumbnailService {

  private final static Set<FileType> SUPPORTED = Set.of(
      IMAGE_ARW, IMAGE_JPEG, IMAGE_PNG, IMAGE_HEIC, IMAGE_GIF
  );

  private final ImageService imageService;

  public ImageThumbnailService(AppConfig appConfig, ImageService imageService) {
    super(appConfig);
    this.imageService = imageService;
  }

  @Override
  public boolean isSupported(FileType fileType) {
    return SUPPORTED.contains(fileType);
  }

  @Override
  public void generate(Path filePath, Path thumbnailPath, Dimension dimension) {
    imageService.resize(
        filePath,
        thumbnailPath,
        dimension
    );
  }
}
