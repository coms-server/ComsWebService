package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.config.WebMvcConfig;
import coms.kw.ac.kr.server.service.tools.ClassPathResourceLoader;
import coms.kw.ac.kr.server.service.tools.FileIOManager;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

class ProfileImageHandler {

    private static final String DEFAULT_IMAGE_PATH = "static/css/images/default.jpg";

    private final FileIOManager fileIOManager;


    public ProfileImageHandler(String LOCAL_STORAGE_ROOT) {
        this.fileIOManager = new FileIOManager(LOCAL_STORAGE_ROOT);
    }

    public void createDefault(int userIndex) throws IOException {
        try (InputStream image = ClassPathResourceLoader.getInputStream(DEFAULT_IMAGE_PATH)) {
            updateImage(userIndex, image);
        }
    }

    public void updateImage(int userIndex, InputStream imageStream) throws IOException {
        BufferedImage original = ImageIO.read(imageStream);
        BufferedImage square = cropImage(original);
        BufferedImage size120 = resizeImage(square, 240);
        BufferedImage size60 = resizeImage(square, 120);
        BufferedImage size40 = resizeImage(square, 80);

        saveImage(userIndex, original, "original.jpg");
        saveImage(userIndex, size120, "120.jpg");
        saveImage(userIndex, size60, "60.jpg");
        saveImage(userIndex, size40, "40.jpg");

        original.flush();
        square.flush();
        size120.flush();
        size60.flush();
        size40.flush();
    }

    private BufferedImage cropImage(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int leftover = width - height;

        if (leftover >= 0) {
            return Scalr.crop(source, leftover / 2, 0, height, height, (BufferedImageOp[]) null);
        } else {
            leftover = -leftover;
            return Scalr.crop(source, 0, leftover / 2, width, width, (BufferedImageOp[]) null);
        }
    }

    private BufferedImage resizeImage(BufferedImage source, int desiredSize) {
        return Scalr.resize(source, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, desiredSize,
                (BufferedImageOp[]) null);
    }

    private void saveImage(int userIndex, BufferedImage image, String filename) throws IOException {
        String directory = getProfileImageDirectory(userIndex);
        File file = fileIOManager.createFile(directory, filename);
        ImageIO.write(image, "jpg", file);
    }

    public static String getProfileImageDirectory(int userIndex) {
        return "/" + WebMvcConfig.PROFILE_IMAGE_DIR_PREFIX + userIndex + "/";
    }

}