package coms.kw.ac.kr.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.MultipartConfigElement;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    // MVC View Resolver Settings
    public static final String MVC_VIEW_PREFIX = "/WEB-INF/jsp/";
    public static final String MVC_VIEW_SUFFIX = ".jsp";
    public static final boolean EXPOSE_CONTEXT_BEAN = false;

    // Static Resource Location Mapping
    public static final String STATIC_RESOURCE_PATH_PATTERN = "/resources/**";
    public static final String STATIC_RESOURCE_LOCAL_DIR = "classpath:/static/";

    // Attachment Location Mapping
    public static final String ATTACHMENT_PATH_PATTERN = "/attachment/**";
    public static final String ATTACHMENT_DIR_PREFIX = "attachment/";
    public final String ATTACHMENT_LOCAL_DIR;

    // Profile IMAGE Location Mapping
    public static final String PROFILE_IMAGE_PATH_PATTERN = "/profile/**";
    public static final String PROFILE_IMAGE_DIR_PREFIX = "profile/";
    public final String PROFILE_IMAGE_LOCAL_DIR;

    // Multipart Resolver Settings
    public static final DataSize MULTIPART_MAX_FILE_SIZE = DataSize.ofMegabytes(20);
    public static final DataSize MULTIPART_MAX_REQUEST_SIZE = DataSize.ofMegabytes(20);
    public static final String MULTIPART_CHAR_ENCODING = "UTF-8";

    public WebMvcConfig(@Value("${local-storage.root}") String LOCAL_STORAGE_ROOT_DIR) {
        this.ATTACHMENT_LOCAL_DIR = "file:" + LOCAL_STORAGE_ROOT_DIR + ATTACHMENT_DIR_PREFIX;
        this.PROFILE_IMAGE_LOCAL_DIR = "file:" + LOCAL_STORAGE_ROOT_DIR + PROFILE_IMAGE_DIR_PREFIX;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(MVC_VIEW_PREFIX);
        resolver.setSuffix(MVC_VIEW_SUFFIX);
        resolver.setExposeContextBeansAsAttributes(EXPOSE_CONTEXT_BEAN);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(STATIC_RESOURCE_PATH_PATTERN).addResourceLocations(STATIC_RESOURCE_LOCAL_DIR);
        registry.addResourceHandler(ATTACHMENT_PATH_PATTERN).addResourceLocations(ATTACHMENT_LOCAL_DIR);
        registry.addResourceHandler(PROFILE_IMAGE_PATH_PATTERN).addResourceLocations(PROFILE_IMAGE_LOCAL_DIR);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(MULTIPART_MAX_FILE_SIZE);
        factory.setMaxRequestSize(MULTIPART_MAX_REQUEST_SIZE);
        return factory.createMultipartConfig();
    }

}