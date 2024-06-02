package com.cbidici.fileservice.service.preview;

import static com.cbidici.fileservice.entity.FileType.IMAGE_ARW;
import static com.cbidici.fileservice.entity.FileType.IMAGE_GIF;
import static com.cbidici.fileservice.entity.FileType.IMAGE_HEIC;
import static com.cbidici.fileservice.entity.FileType.IMAGE_JPEG;
import static com.cbidici.fileservice.entity.FileType.IMAGE_PNG;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.service.media.ImageService;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ImagePreviewService extends PreviewService {

  private final static Set<FileType> SUPPORTED = Set.of(
      IMAGE_ARW, IMAGE_JPEG, IMAGE_PNG, IMAGE_HEIC, IMAGE_GIF
  );

  private final ImageService imageService;

  public ImagePreviewService(AppConfig appConfig, ImageService imageService) {
    super(appConfig);
    this.imageService = imageService;
  }

  @Override
  public boolean isSupported(FileType fileType) {
    return SUPPORTED.contains(fileType);
  }

  @Override
  public void generate(Path filePath, Path thumbnailPath, Dimension thumbnailDimension) {
    imageService.resize(
        filePath,
        thumbnailPath,
        thumbnailDimension
    );
  }

  @Override
  public Path getPath(String fileId)  {
    return Path.of(AppConfig.PREVIEWS).resolve(fileId+".jpg");
  }
}
