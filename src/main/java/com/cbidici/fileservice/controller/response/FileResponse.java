package com.cbidici.fileservice.controller.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FileResponse {
  private final String id;
  private final String name;
  private final String type;
}
