package com.cbidici.fileservice.service.thumbnail;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.exception.FileServiceException;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ThumbnailService {

  private AppConfig appConfig;
  private Dimension dimension;

  public ThumbnailService(AppConfig appConfig) {
    this.appConfig = appConfig;
    this.dimension = new Dimension(appConfig.getThumbnailsWidth(), appConfig.getThumbnailsWidth());
  }

  public abstract boolean isSupported(FileType fileType);
  public abstract void generate(Path filePath, Path thumbnailPath, Dimension thumbnailDimension);

  public Path getPath(String fileId) {
    return Path.of(AppConfig.THUMBNAILS).resolve(fileId+".jpg");
  }

  public void generate(String fileId) {
    var thumbnailSystemPath =  getPath(fileId);
    var thumbnailPath = Path.of(appConfig.getSystemFilesPath()).resolve(thumbnailSystemPath);
    var filePath = Path.of(appConfig.getFilesPath()).resolve(fileId);
    if(!exists(thumbnailPath)) {
      createDirectories(thumbnailPath.getParent());
      generate(filePath, thumbnailPath, dimension);
    }
  }

  private boolean exists(Path thumbnailPath) {
    return thumbnailPath.toFile().isFile() && thumbnailPath.toFile().exists();
  }

  private void createDirectories(Path path) {
    try {
      if(!path.toFile().exists()) {
        Files.createDirectories(path);
      }
    } catch (IOException ex) {
      throw new FileServiceException(String.format("Could not create directory %s", path.toFile()), ex);
    }
  }
}
