package coms.kw.ac.kr.server.vo.statics;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("RootBoardVO")
public class RootBoardVO implements Board {
    private Integer root_idx;
    private String context_path;
    private String root_name;
    private String root_desc;
    private Boolean is_qna;
    private Boolean is_important;

    private List<BoardVO> child_boards;

    public Integer getRoot_idx() {
        return this.root_idx;
    }

    public void setRoot_idx(int root_idx) {
        this.root_idx = root_idx;
    }

    public String getContext_path() {
        return this.context_path;
    }

    public void setContext_path(String context_path) {
        this.context_path = context_path;
    }

    public String getRoot_name() {
        return this.root_name;
    }

    public void setRoot_name(String root_name) {
        this.root_name = root_name;
    }

    public String getRoot_desc() {
        return this.root_desc;
    }

    public void setRoot_desc(String root_desc) {
        this.root_desc = root_desc;
    }

    public Boolean getIs_qna() {
        return this.is_qna;
    }

    public void setIs_qna(Boolean is_qna) {
        this.is_qna = is_qna;
    }

    public Boolean getIs_important() {
        return is_important;
    }

    public void setIs_important(Boolean is_important) {
        this.is_important = is_important;
    }

    public void setChild_boards(List<BoardVO> child_boards) {
        this.child_boards = child_boards;
    }

    public List<BoardVO> getChild_boards() {
        return this.child_boards;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public int getIndex() {
        return this.root_idx;
    }

    @Override
    public String getContext() {
        return this.context_path;
    }

    @Override
    public String getName() {
        return this.root_name;
    }

    @Override
    public boolean isQna() {
        return this.is_qna;
    }

    @Override
    public boolean isImportant() {
        return this.is_important;
    }

}