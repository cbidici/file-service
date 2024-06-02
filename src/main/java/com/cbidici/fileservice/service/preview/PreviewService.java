package com.cbidici.fileservice.service.preview;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.exception.FileServiceException;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class PreviewService {

  private AppConfig appConfig;
  private Dimension dimension;

  public PreviewService(AppConfig appConfig) {
    this.appConfig = appConfig;
    this.dimension = new Dimension(appConfig.getPreviewsWidth(), appConfig.getPreviewsWidth());
  }

  public abstract boolean isSupported(FileType fileType);
  protected abstract void generate(Path filePath, Path previewPath, Dimension dimension);

  public abstract Path getPath(String fileId);

  public void generate(String fileId) {
    var previewSystemPath =  getPath(fileId);
    var previewPath = Path.of(appConfig.getSystemFilesPath()).resolve(previewSystemPath);
    var filePath = Path.of(appConfig.getFilesPath()).resolve(fileId);
    if(!exists(previewPath)) {
      createDirectories(previewPath.getParent());
      generate(filePath, previewPath, dimension);
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
