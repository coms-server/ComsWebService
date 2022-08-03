package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.config.WebMvcConfig;
import coms.kw.ac.kr.server.service.article.contentwrapper.Delta;
import coms.kw.ac.kr.server.service.article.contentwrapper.ImagePlaceHolder;
import coms.kw.ac.kr.server.service.tools.FileIOManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class ContentDisassembler {

    private final FileIOManager fileIOManager;

    private static final String EMBED_IMAGE_SUBDIR = "embed/";

    public ContentDisassembler(String LOCAL_STORAGE_ROOT) {
        this.fileIOManager = new FileIOManager(LOCAL_STORAGE_ROOT);
    }

    public void disassembleMedia(List<Delta> deltas, int articleIndex) {
        // Clean up embed image folder and check video URL
        List<ImagePlaceHolder> holderList = new ArrayList<>();
        List<String> exclude = new ArrayList<>();

        Iterator<Delta> iterator = deltas.iterator();
        while(iterator.hasNext()) {
            Delta d = iterator.next();
            
            if (d.hasImage()) {
                ImagePlaceHolder holder = ImagePlaceHolder.decode(d);
                if(holder == null) {
                    iterator.remove();
                    continue;
                }

                holderList.add(holder);
                if (!holder.isRaw())
                    exclude.add(holder.getFilename());
            } else if(!d.isVideoURLValid()) {
                iterator.remove();
            }
        }
        clearEmbedImageFolder(articleIndex, exclude);

        // Save embed image
        String embedImageWebPath = getEmbedImageDirectory(articleIndex);
        for (ImagePlaceHolder h : holderList) {
            if (h.isRaw()) {
                saveEmbedImage(articleIndex, h.getData(), h.getFilename());
                h.getParent().replaceImageData(embedImageWebPath + h.getFilename());
            }
        }

    }

    private void clearEmbedImageFolder(int articleIndex, Collection<String> exclude) {
        String directory = getEmbedImageDirectory(articleIndex);
        fileIOManager.clearDirectory(directory, exclude);
    }

    private void saveEmbedImage(int articleIndex, byte[] data, String filename) {
        String directory = getEmbedImageDirectory(articleIndex);
        File file = fileIOManager.createFile(directory, filename);
        fileIOManager.writeDataToFile(file, data);
    }

    private String getEmbedImageDirectory(int articleIndex) {
        return "/" + WebMvcConfig.ATTACHMENT_DIR_PREFIX + articleIndex + "/" + EMBED_IMAGE_SUBDIR;
    }
}
