package coms.kw.ac.kr.server.service.statics;

import coms.kw.ac.kr.server.dao.StaticInformationDAO;
import coms.kw.ac.kr.server.service.tools.ScheduledTask;
import coms.kw.ac.kr.server.vo.statics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StaticInformationServiceImpl implements StaticInformationService, ScheduledTask {

    private List<RootBoardVO> rootBoardList;
    private List<BoardVO> boardList;
    private List<StatusVO> statusList;
    private List<MajorVO> majorList;
    private List<PositionVO> positionList;

    private StaticInformationDAO informationDAO;

    @Autowired
    public StaticInformationServiceImpl(StaticInformationDAO informationDAO) {
        this.informationDAO = informationDAO;
        refreshStaticInformation();
    }

    @Override
    public List<RootBoardVO> getRootBoardList() {
        return this.rootBoardList;
    }

    @Override
    public List<BoardVO> getBoardList() {
        return this.boardList;
    }

    @Override
    public Board findBoardByContext(String context) {
        Stream<? extends Board> rootBoard = this.rootBoardList.stream();
        Stream<? extends Board> board = this.boardList.stream();
        Stream<Board> concat = Stream.concat(rootBoard, board);

        return concat.filter(b -> b.getContext().equals(context)).findAny().orElse(null);
    }

    @Override
    public BoardVO findBoardByIndex(int boardIndex) {
        return this.boardList.stream().filter(b -> b.getBoard_idx().equals(boardIndex)).findAny().orElse(null);
    }

    @Override
    public List<StatusVO> getStatusList() {
        return this.statusList;
    }

    @Override
    public StatusVO findStatusByIndex(int statusIndex) {
        return this.statusList.stream().filter(s -> s.getStatus_idx().equals(statusIndex)).findAny().orElse(null);
    }

    @Override
    public List<MajorVO> getMajorList() {
        return this.majorList;
    }

    @Override
    public MajorVO findMajorByIndex(int majorIndex) {
        return this.majorList.stream().filter(m -> m.getMajor_idx().equals(majorIndex)).findAny().orElse(null);
    }

    @Override
    public List<PositionVO> getPositionList() {
        return this.positionList;
    }

    @Override
    public PositionVO findPositionByIndex(int positionIndex) {
        return this.positionList.stream().filter(p -> p.getPosition_idx().equals(positionIndex)).findAny().orElse(null);
    }

    private void refreshStaticInformation() {
        this.rootBoardList = informationDAO.getRootBoardList();
        this.boardList = informationDAO.getBoardList();
        this.statusList = informationDAO.getStatusList();
        this.majorList = informationDAO.getMajorList();
        this.positionList = informationDAO.getPositionList();

        for (RootBoardVO root : this.rootBoardList) {
            List<Integer> child = informationDAO.getChildBoardList(root.getIndex());
            List<BoardVO> list = boardList.stream().filter(b -> child.contains(b.getBoard_idx()))
                    .collect(Collectors.toList());

            list.stream().forEach(b -> b.setRoot(root));
            root.setChild_boards(list);
        }
    }

    @Override
    public UpdateRate getUpdateRate() {
        return UpdateRate.NEVER;
    }

    @Override
    public int getUpdatePriority() {
        return 0;
    }

    @Override
    public void scheduledUpdate() {
        refreshStaticInformation();
    }

}