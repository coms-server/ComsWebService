package coms.kw.ac.kr.server.service.statics;

import coms.kw.ac.kr.server.vo.statics.*;

import java.util.List;

/**
 * Static information provider such as list of board, status code, major,
 * position, etc.
 */
public interface StaticInformationService {

    List<RootBoardVO> getRootBoardList();

    List<BoardVO> getBoardList();

    Board findBoardByContext(String context);

    BoardVO findBoardByIndex(int boardIndex);

    List<StatusVO> getStatusList();

    StatusVO findStatusByIndex(int statusIndex);

    List<MajorVO> getMajorList();

    MajorVO findMajorByIndex(int majorIndex);

    List<PositionVO> getPositionList();

    PositionVO findPositionByIndex(int positionIndex);
    
    

}