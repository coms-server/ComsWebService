package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.user.ExecutiveVO;

import java.util.List;

public interface ExecutiveDAO {

    public void insertExecutive(ExecutiveVO execInfo);

    public ExecutiveVO getExecutive(int exec_idx);

    public void updateExecutive(ExecutiveVO execInfo);

    public void deleteExecutive(int exec_idx);

    public Integer getCount(Boolean active);

    public List<ExecutiveVO> getActiveExecutiveList();

    public List<ExecutiveVO> getNonActiveExecutiveList();

    public void updateAuthority(int user_idx, String authority);
}