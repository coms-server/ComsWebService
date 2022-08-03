package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.article.AttachmentVO;

import java.util.List;

public interface AttachmentDAO {

    public void insertAttachmentInfo(AttachmentVO attachment);

    public AttachmentVO getAttachmentInfo(int attach_idx);

    public List<AttachmentVO> getArticleAttachments(int article_idx);

    public void deleteArticleAttachments(int article_idx);
}