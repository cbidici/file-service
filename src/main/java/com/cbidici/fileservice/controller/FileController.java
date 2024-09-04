package com.cbidici.fileservice.controller;

import com.cbidici.fileservice.controller.response.FileResponse;
import com.cbidici.fileservice.controller.response.FileResponseFactory;
import com.cbidici.fileservice.controller.response.FileResponseWithPrevNext;
import com.cbidici.fileservice.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

  private final static String CHILDREN_PATH = "/children";

  private final FileService service;
  private final FileResponseFactory factory;

  @GetMapping({CHILDREN_PATH, CHILDREN_PATH+"/**"})
  public ResponseEntity<List<FileResponse>> children(
      HttpServletRequest request,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "0") int size,
      @RequestParam(defaultValue = "true") boolean ignoreDuplicates
  ) {
    String fileId = resolveFileId(request.getRequestURL().toString().replace(CHILDREN_PATH, ""));
    List<FileResponse> contents = service.getChildren(fileId, offset, size, ignoreDuplicates)
        .stream()
        .map(factory::getFileResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(contents);
  }

  @GetMapping({"/**"})
  public ResponseEntity<FileResponseWithPrevNext> file(
      HttpServletRequest request,
      @RequestParam(defaultValue = "true") boolean ignoreDuplicates
  ) {
    String fileId = resolveFileId(request.getRequestURL().toString());
    return ResponseEntity.ok(factory.getFileResponseWithPrevNext(service.getByIdWithNextPrev(fileId, ignoreDuplicates)));
  }

  private String resolveFileId(String requestUrl) {
    return requestUrl.split("/files/").length == 1 ? "" : URLDecoder.decode(requestUrl.split("/files/")[1], StandardCharsets.UTF_8);
  }
}
