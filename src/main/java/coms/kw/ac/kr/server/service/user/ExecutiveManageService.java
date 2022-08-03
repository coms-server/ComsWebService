package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.vo.user.ExecutiveVO;

import java.util.List;

/**
 * TODO: javadoc 완성
 * <p>
 * A service that modifies executive and administrative({@code ADMIN} authority) privileges.
 * <p>
 * All method calls in this service must be logged.
 */
public interface ExecutiveManageService {

    public void createNewExecutive(ExecutiveVO execInfo);

    public ExecutiveVO getExecutiveInformation(int execIndex);

    public void updateExecutiveInformation(ExecutiveVO execInfo);

    public void deleteExecutiveInformation(int execIndex);

    public Integer getCount(Boolean active);

    public List<ExecutiveVO> getActiveExecutiveList();

    public List<ExecutiveVO> getNonActiveExecutiveList();
}