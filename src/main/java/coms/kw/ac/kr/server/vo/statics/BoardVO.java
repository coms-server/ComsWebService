package coms.kw.ac.kr.server.vo.statics;

import org.apache.ibatis.type.Alias;

@Alias("BoardVO")
public class BoardVO implements Board {
    private Integer board_idx;
    private Integer root_idx;
    private String context_path;
    private String board_name;
    private String board_desc;

    private RootBoardVO root;

    public Integer getBoard_idx() {
        return this.board_idx;
    }

    public void setBoard_idx(Integer board_idx) {
        this.board_idx = board_idx;
    }

    public Integer getRoot_idx() {
        return this.root_idx;
    }

    public void setRoot_idx(Integer parent_idx) {
        this.root_idx = parent_idx;
    }

    public String getContext_path() {
        return this.context_path;
    }

    public void setContext_path(String context_path) {
        this.context_path = context_path;
    }

    public String getBoard_name() {
        return this.board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public String getBoard_desc() {
        return this.board_desc;
    }

    public void setBoard_desc(String board_desc) {
        this.board_desc = board_desc;
    }

    public RootBoardVO getRoot() {
        return this.root;
    }

    public void setRoot(RootBoardVO root) {
        this.root = root;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public int getIndex() {
        return this.board_idx;
    }

    @Override
    public String getContext() {
        return this.context_path;
    }

    @Override
    public String getName() {
        return this.board_name;
    }
    
    @Override
    public boolean isQna() {
        return this.root.isQna();
    }

    @Override
    public boolean isImportant() {
        return this.root.isImportant();
    }

}