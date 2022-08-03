package coms.kw.ac.kr.server.service.article.contentwrapper;

import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * Image place holding object for {@link Delta}'s {@code image} attribute.
 */
public class ImagePlaceHolder {
    private final String filename;
    private final byte[] data;
    private final boolean isRaw;
    private final Delta parent;

    public ImagePlaceHolder(byte[] data, String extension, Delta parent) {
        this.filename = String.valueOf(data.hashCode()) + "." + extension;
        this.data = data;
        this.isRaw = true;
        this.parent = parent;
    }

    public ImagePlaceHolder(String filepath, Delta parent) {
        this.filename = filepath.substring(filepath.lastIndexOf("/") + 1);
        this.data = null;
        this.isRaw = false;
        this.parent = parent;
    }

    public String getFilename() {
        return this.filename;
    }

    public byte[] getData() {
        return this.data;
    }

    public boolean isRaw() {
        return this.isRaw;
    }

    public Delta getParent() {
        return this.parent;
    }

    /**
     * Decode image data and create {@code ImagePlaceHolder} object.
     * 
     * @param delta target delta object holding {@code image} attribute
     * @return {@code ImagePlaceHolder} containing image data and type
     *         <p>
     *         {@code null} if type is unknown
     */
    public static ImagePlaceHolder decode(Delta delta) {
        String data = delta.getImageData();

        // Raw image data
        if (data.startsWith("data:image/")) {
            String extension = data.substring(data.indexOf('/') + 1, data.indexOf(';'));
            data = data.substring(data.indexOf(',') + 1);
            ImagePlaceHolder holder = new ImagePlaceHolder(Base64.getDecoder().decode(data), extension, delta);
            return holder;
        }

        // Local image url
        if (data.startsWith("/"))
            return new ImagePlaceHolder(data, delta);

        // Web image url
        if (data.startsWith("http")) {
            URL url;
            try {
                url = new URL(data);
            } catch (MalformedURLException exception) {
                return null;
            }

            try (InputStream is = url.openStream()) {
                byte[] bytes = StreamUtils.copyToByteArray(is);
                String extension = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
                if(extension == null)
                    extension = "unknown";
                else if(extension.startsWith("image/"))
                    extension = extension.substring(extension.indexOf('/') + 1);

                return new ImagePlaceHolder(bytes, extension, delta);
            } catch (IOException exception) {
                return null;
            }
        }

        return null;
    }

}