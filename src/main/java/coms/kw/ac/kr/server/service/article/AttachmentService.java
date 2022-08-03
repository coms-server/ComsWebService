package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.vo.article.AttachmentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    public int uploadAttachment(int articleIndex, MultipartFile file);

    public AttachmentVO getAttachmentInfo(int attachmentIndex);

    public List<AttachmentVO> getAttachments(int articleIndex);

    public void clearAttachments(int articleIndex);

}