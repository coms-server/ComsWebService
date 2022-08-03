package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.config.WebSecurityConfig.Authority;
import coms.kw.ac.kr.server.dao.ExecutiveDAO;
import coms.kw.ac.kr.server.vo.user.ExecutiveVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutiveManageServiceImpl implements ExecutiveManageService {

    private final ExecutiveDAO executiveDAO;

    private static final Logger logger = LoggerFactory.getLogger(ExecutiveManageServiceImpl.class);

    @Autowired
    public ExecutiveManageServiceImpl(ExecutiveDAO executiveDAO) {
        this.executiveDAO = executiveDAO;
    }

    @Override
    public void createNewExecutive(ExecutiveVO execInfo) {
        executiveDAO.insertExecutive(execInfo);
        if (execInfo.getActive())
            updateAuthority(execInfo.getUser_idx(), Authority.ADMIN);
    }

    @Override
    public ExecutiveVO getExecutiveInformation(int execIndex) {
        return executiveDAO.getExecutive(execIndex);
    }

    @Override
    public void updateExecutiveInformation(ExecutiveVO execInfo) {
        executiveDAO.updateExecutive(execInfo);
        if (execInfo.getActive())
            updateAuthority(execInfo.getUser_idx(), Authority.ADMIN);
        else
            updateAuthority(execInfo.getUser_idx(), Authority.MEMBER);
    }

    @Override
    public void deleteExecutiveInformation(int execIndex) {
        ExecutiveVO execInfo = getExecutiveInformation(execIndex);
        executiveDAO.deleteExecutive(execIndex);
        updateAuthority(execInfo.getUser_idx(), Authority.MEMBER);
    }

    @Override
    public Integer getCount(Boolean active) {
        return executiveDAO.getCount(active);
    }

    @Override
    public List<ExecutiveVO> getActiveExecutiveList() {
        return executiveDAO.getActiveExecutiveList();
    }

    @Override
    public List<ExecutiveVO> getNonActiveExecutiveList() {
        return executiveDAO.getNonActiveExecutiveList();
    }

    private void updateAuthority(int userIndex, Authority authority) {
        executiveDAO.updateAuthority(userIndex, authority.getValue());
        logger.info("User's authority updated; user_idx={}, authority={}", userIndex, authority.getValue());
    }

}