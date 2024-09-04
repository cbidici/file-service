package com.cbidici.fileservice.controller.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FileResponseWithPrevNext extends FileResponse {
  private final String prevId;
  private final String nextId;
}
