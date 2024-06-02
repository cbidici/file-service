package com.cbidici.fileservice.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum FileType {
  DIRECTORY("directory", ""),
  IMAGE_JPEG("image/jpeg", "jpg"),
  IMAGE_PNG("image/png", "png"),
  IMAGE_HEIC("image/heic", "heic"),
  IMAGE_GIF("image/gif", "gif"),
  VIDEO_MP4("video/mp4", "mp4"),
  VIDEO_QUICKTIME("video/quicktime", "mov"),
  IMAGE_ARW("image/x-sony-arw", "arw"),
  UNSUPPORTED("unsupported", "unsupported");

  private static final Map<String, FileType> lookupName = new HashMap<>();
  private static final Map<String, FileType> lookupExtension = new HashMap<>();
  private final String name;
  private final String extension;

  static {
    lookupName.putAll(Arrays.stream(FileType.values())
        .collect(Collectors.toMap(FileType::getName, Function.identity())));
    lookupExtension.putAll(Arrays.stream(FileType.values())
        .collect(Collectors.toMap(FileType::getExtension, Function.identity())));
  }

  FileType(String name, String extension) {
    this.name = name;
    this.extension = extension;
  }

  public static FileType getByNameOrExtension(String name, String extension) {
    var type = lookupName.get(name);
    if(type == null) {
      log.warn("Could not find type with name {}", name);
      type = lookupExtension.get(extension.toLowerCase());
      if(type == null) {
        log.warn("Could not find type with extension {}", extension);
        type = UNSUPPORTED;
      }
    }
    return type;
  }

  private String getName() {
    return this.name;
  }
  private String getExtension() {
    return this.extension;
  }
}
