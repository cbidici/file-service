package com.cbidici.fileservice.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDomain {
  private final String id;
  private final String name;
  private final FileType type;
}
