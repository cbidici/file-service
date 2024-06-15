package com.cbidici.fileservice.config;

import com.cbidici.fileservice.service.PreviewPathResolver;
import com.cbidici.fileservice.service.ThumbnailPathResolver;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@RequiredArgsConstructor
public class ResourceConfiguration extends WebMvcConfigurationSupport {

  private final ThumbnailPathResolver thumbnailPathResolver;
  private final PreviewPathResolver previewPathResolver;
  private final AppConfig appConfig;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/" + AppConfig.FILES + "/**")
        .addResourceLocations("file:"+appConfig.getFilesPath()+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(new PathResourceResolver());

    registry.addResourceHandler("/" + AppConfig.THUMBNAILS + "/**")
        .addResourceLocations("file:"+ Path.of(appConfig.getSystemFilesPath())+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(thumbnailPathResolver)
        .addResolver(new PathResourceResolver());

    registry.addResourceHandler("/" + AppConfig.PREVIEWS + "/**")
        .addResourceLocations("file:"+ Path.of(appConfig.getSystemFilesPath())+"/", "file:"+appConfig.getFilesPath()+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(previewPathResolver)
        .addResolver(new PathResourceResolver());
  }
}
