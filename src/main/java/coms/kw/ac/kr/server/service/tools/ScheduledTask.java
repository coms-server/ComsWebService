package coms.kw.ac.kr.server.service.tools;

public interface ScheduledTask {
    enum UpdateRate {
        ONCE_AN_HOUR, ONCE_A_DAY, NEVER
    }
    
    /**
     * 업데이트 주기. Update Rate에서 선택.
     * @return  {@link UpdateRate}
     */
    UpdateRate getUpdateRate();

    /**
     * 업데이트 중요도. 낮은 숫자의 업데이트가 먼저 실행됨.
     * @return Update Priority {@code int}
     */
    int getUpdatePriority();

    /**
     * 업데이트 함수 본체.
     */
    void scheduledUpdate();
}