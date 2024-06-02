package com.cbidici.fileservice.controller.response;

import com.cbidici.fileservice.entity.FileType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponse {
  private final String id;
  private final String name;
  private final String type;
}
