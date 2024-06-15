package com.cbidici.fileservice.service.preview;

import static com.cbidici.fileservice.entity.FileType.DIRECTORY;
import static com.cbidici.fileservice.entity.FileType.UNSUPPORTED;
import static com.cbidici.fileservice.entity.FileType.VIDEO_MP4;
import static com.cbidici.fileservice.entity.FileType.VIDEO_QUICKTIME;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class VoidPreviewService extends PreviewService {

  private final static Set<FileType> SUPPORTED = Set.of(
      UNSUPPORTED, DIRECTORY, VIDEO_MP4, VIDEO_QUICKTIME
  );

  public VoidPreviewService(AppConfig appConfig) {
    super(appConfig);
  }

  @Override
  public boolean isSupported(FileType fileType) {
    return SUPPORTED.contains(fileType);
  }

  @Override
  public void generate(Path filePath, Path thumbnailPath, Dimension thumbnailDimension) {

  }

  @Override
  public Path getPath(String fileId) {
    return Path.of(fileId);
  }

}
