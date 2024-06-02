package com.cbidici.fileservice.service;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

@Component
@RequiredArgsConstructor
public class PreviewPathResolver extends PathResourceResolver {
  private final FileService fileService;

  @Override
  @Nullable
  public Resource resolveResource(@Nullable HttpServletRequest request, @NonNull String requestPath,
      @NonNull List<? extends Resource> locations, @NonNull ResourceResolverChain chain) {
    var fileId = URLDecoder.decode(requestPath, StandardCharsets.UTF_8);
    var previewPath = fileService.getPreviewPath(fileId);
    return chain.resolveResource(request, previewPath.toString(), locations);
  }
}
