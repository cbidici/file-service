package com.cbidici.fileservice.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FileDomain {
  private final String id;
  private final String name;
  private final FileType type;
}
