package com.cbidici.fileservice.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PrevNextFileDomain extends FileDomain {
  private String prevId;
  private String nextId;
}
