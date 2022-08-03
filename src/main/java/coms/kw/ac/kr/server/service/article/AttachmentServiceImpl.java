package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.config.WebMvcConfig;
import coms.kw.ac.kr.server.dao.AttachmentDAO;
import coms.kw.ac.kr.server.service.tools.FileIOManager;
import coms.kw.ac.kr.server.vo.article.AttachmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentDAO attachmentDAO;
    private final FileIOManager fileIOManager;

    private static final String ATTACHMENT_SUBDIR = "attach/";

    @Autowired
    public AttachmentServiceImpl(AttachmentDAO attachmentDAO, @Value("${local-storage.root}") String LOCAL_STORAGE_ROOT) {
        this.attachmentDAO = attachmentDAO;
        this.fileIOManager = new FileIOManager(LOCAL_STORAGE_ROOT);
    }

    @Override
    public int uploadAttachment(int articleIndex, MultipartFile file) {
        String filename = file.getOriginalFilename().replaceAll(" ", "_").replaceAll("[\\/:*?\"<>|]", "+");
        if (filename.length() > 128)
            filename = filename.substring(0, 128);

        try {
            saveAttachment(articleIndex, file.getBytes(), filename);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        AttachmentVO attachment = new AttachmentVO(articleIndex, filename, (int) file.getSize());
        attachmentDAO.insertAttachmentInfo(attachment);

        return attachment.getAttach_idx();
    }

    @Override
    public AttachmentVO getAttachmentInfo(int attachmentIndex) {
        AttachmentVO attachment = attachmentDAO.getAttachmentInfo(attachmentIndex);
        addFileInformation(attachment, attachment.getArticle_idx());

        return attachment;
    }

    @Override
    public List<AttachmentVO> getAttachments(int articleIndex) {
        List<AttachmentVO> attachments = attachmentDAO.getArticleAttachments(articleIndex);
        for (AttachmentVO vo : attachments)
            addFileInformation(vo, articleIndex);

        return attachments;
    }

    @Override
    public void clearAttachments(int articleIndex) {
        String directory = getFileDirectory(articleIndex);
        fileIOManager.clearDirectory(directory);
        attachmentDAO.deleteArticleAttachments(articleIndex);
    }

    private void saveAttachment(int articleIndex, byte[] data, String filename) {
        String directory = getFileDirectory(articleIndex);
        File file = fileIOManager.createFile(directory, filename);
        fileIOManager.writeDataToFile(file, data);
    }

    private String getFileDirectory(int articleIndex) {
        return "/" + WebMvcConfig.ATTACHMENT_DIR_PREFIX + articleIndex + "/" + ATTACHMENT_SUBDIR;
    }

    private void addFileInformation(AttachmentVO attachment, int articleIndex) {
        String filePath = getFileDirectory(articleIndex) + attachment.getFilename();
        int filesize = attachment.getFilesize();
        String unit = "B";
        if (filesize > (1 << 10)) {
            if (filesize > (1 << 20)) {
                filesize >>= 20;
                unit = "MB";
            } else {
                filesize >>= 10;
                unit = "KB";
            }
        }
        attachment.setDirectory(filePath);
        attachment.setFilesize(filesize);
        attachment.setUnit(unit);
    }

}