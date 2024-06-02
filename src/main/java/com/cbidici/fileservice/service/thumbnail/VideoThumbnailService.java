package com.cbidici.fileservice.service.thumbnail;

import static com.cbidici.fileservice.entity.FileType.VIDEO_MP4;
import static com.cbidici.fileservice.entity.FileType.VIDEO_QUICKTIME;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.service.media.ImageService;
import com.cbidici.fileservice.service.media.VideoService;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
public class VideoThumbnailService extends ThumbnailService {

  private final static Set<FileType> SUPPORTED = Set.of(VIDEO_MP4, VIDEO_QUICKTIME);

  private final VideoService videoService;
  private final ImageService imageService;
  private final AppConfig appConfig;

  public VideoThumbnailService(
      AppConfig appConfig,
      VideoService videoService,
      ImageService imageService
  ) {
    super(appConfig);
    this.appConfig = appConfig;
    this.imageService = imageService;
    this.videoService = videoService;
  }

  @Override
  public boolean isSupported(FileType fileType) {
    return SUPPORTED.contains(fileType);
  }

  @Override
  public void generate(Path filePath, Path thumbnailPath, Dimension dimension) {
    BufferedImage firstFrame = videoService.getFirstFrame(filePath);
    imageService.resizeSave(firstFrame, dimension, thumbnailPath);
  }
}
