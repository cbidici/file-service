package com.cbidici.fileservice.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.file-service")
@Data
public class AppConfig {

    public static final String FILES = "files";
    public static final String THUMBNAILS = "thumbnails";
    public static final String PREVIEWS = "previews";

    private String filesPath;
    private String systemFilesPath;
    private int thumbnailsWidth;
    private int previewsWidth;
    private List<String> allowedCrossOrigins;
}
