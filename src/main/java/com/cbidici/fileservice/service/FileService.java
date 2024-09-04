package com.cbidici.fileservice.service;

import com.cbidici.fileservice.config.AppConfig;
import com.cbidici.fileservice.entity.FileDomain;
import com.cbidici.fileservice.entity.FileType;
import com.cbidici.fileservice.entity.PrevNextFileDomain;
import com.cbidici.fileservice.exception.FileServiceFileNotFoundException;
import com.cbidici.fileservice.service.preview.PreviewServiceFactory;
import com.cbidici.fileservice.service.thumbnail.ThumbnailServiceFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

  private static final Function<String, String> REMOVE_FILE_EXTENSION = f -> f.contains(".") ? f.substring(0, f.lastIndexOf('.')) : f;
  private static final Predicate<FileDomain> IS_IGNORABLE_DUPLICATES = f -> f.getType() == FileType.IMAGE_ARW || f.getType() == FileType.VIDEO_QUICKTIME;
  private final AppConfig appConfig;
  private final ThumbnailServiceFactory thumbnailServiceFactory;
  private final PreviewServiceFactory previewServiceFactory;

  public List<FileDomain> getChildren(String id, int offset, int size, boolean ignoreDuplicates) {
    File directory = resolvePath(id).toFile();

    if (!directory.isDirectory() || !isValidPath(directory.toPath())) {
      throw new FileServiceFileNotFoundException(directory.toPath().toString());
    }

    List<FileDomain> files = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
        .filter(f -> !f.isHidden())
        .sorted(Comparator.comparing(File::getName))
        .map(f -> getById(Path.of(id).resolve(f.getName()).toString()))
        .toList();

    Set<String> unIgnorables = files.stream()
        .filter(IS_IGNORABLE_DUPLICATES.negate())
        .map(f -> REMOVE_FILE_EXTENSION.apply(f.getName()))
        .collect(Collectors.toSet());

    return files.stream()
        .filter(f -> !ignoreDuplicates || IS_IGNORABLE_DUPLICATES.negate().test(f) || !unIgnorables.contains(REMOVE_FILE_EXTENSION.apply(f.getName())))
        .skip(offset).limit(size)
        .toList();
  }

  public FileDomain getById(String id) {
    Path path = resolvePath(id);
    if (!isValidPath(path)) {
      throw new FileServiceFileNotFoundException(path.toString());
    }

    return FileDomain.builder()
        .id(id)
        .name(path.toFile().getName())
        .type(resolveFileType(path.toString()))
        .build();
  }

  public PrevNextFileDomain getByIdWithNextPrev(String id, boolean ignoreDuplicates) {
    FileDomain file = getById(id);
    String prevFileId = null;
    String nextFileId = null;
    List<FileDomain> files = getChildren(Path.of(file.getId()).getParent().toString(), 0, Integer.MAX_VALUE, ignoreDuplicates)
        .stream()
        .filter(f -> f.getType() != FileType.DIRECTORY)
        .toList();
    for(int i=0; i<files.size(); i++) {
      if(file.getId().equals(files.get(i).getId())) {
        if(i>0) {
          prevFileId = files.get(i-1).getId();
        }
        if(i+1<files.size()) {
          nextFileId = files.get(i+1).getId();
        }
        break;
      }
    }

    return PrevNextFileDomain.builder()
        .id(file.getId())
        .type(file.getType())
        .name(file.getName())
        .prevId(prevFileId)
        .nextId(nextFileId)
        .build();
  }

  public String getThumbnailPath(String fileId) {
    var file = getById(fileId);
    var thumbnailService = thumbnailServiceFactory.getByFileType(file.getType());
    thumbnailService.generate(file.getId());
    return thumbnailService.getPath(file.getId()).toString();
  }

  public String getPreviewPath(String fileId) {
    var file = getById(fileId);
    var previewService = previewServiceFactory.getByFileType(file.getType());
    previewService.generate(file.getId());
    return previewService.getPath(file.getId()).toString();
  }

  private Path resolvePath(String id) {
    return Path.of(appConfig.getFilesPath()).resolve(id);
  }

  private FileType resolveFileType(String id) {
    Path path = resolvePath(id);
    if (!isValidPath(path)) {
      throw new FileServiceFileNotFoundException(path.toString());
    }

    if (path.toFile().isDirectory()) {
      return FileType.DIRECTORY;
    }

    String fileTypeName = FileType.UNSUPPORTED.name();
    try {
      fileTypeName = Files.probeContentType(path);
      if(null == fileTypeName) {
        fileTypeName = new Tika().detect(path.toFile());
      }
    } catch (IOException e) {
      log.error("Failed to read type of file {}", path, e);
    }

    String extension = path.toString().substring(path.toString().lastIndexOf('.') + 1);
    return FileType.getByNameOrExtension(fileTypeName, extension);
  }

  private boolean isValidPath(Path path) {
    try {
      return path.toFile().getCanonicalPath().startsWith(Path.of(appConfig.getFilesPath()).toFile().getCanonicalPath());
    } catch (IOException io) {
      return false;
    }
  }
}
