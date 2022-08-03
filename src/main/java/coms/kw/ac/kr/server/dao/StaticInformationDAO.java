package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.statics.*;

import java.util.List;

public interface StaticInformationDAO {

    public List<RootBoardVO> getRootBoardList();

    public List<Integer> getChildBoardList(int root_idx);

    public List<BoardVO> getBoardList();

    public List<PositionVO> getPositionList();

    public List<MajorVO> getMajorList();

    public List<StatusVO> getStatusList();

}