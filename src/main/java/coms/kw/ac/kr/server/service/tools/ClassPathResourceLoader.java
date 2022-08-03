package coms.kw.ac.kr.server.service.tools;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Simple classpath resource loader implementation class, wrapping all IOException
 * to unchecked exception because it assumes that the file exists.
 */
public class ClassPathResourceLoader {

    private ClassPathResourceLoader() {
    }

    public static InputStream getInputStream(String path) {
        try {
            return new ClassPathResource(path).getInputStream();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static String getAsString(String path) {
        Resource resource = new ClassPathResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

}
