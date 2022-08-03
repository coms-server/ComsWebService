package coms.kw.ac.kr.server.service.event;

import coms.kw.ac.kr.server.vo.event.EventVO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    public void insertEvent(EventVO event);

    public EventVO getEvent(int eventIndex);

    public void updateEvent(EventVO event);

    public void deleteEvent(int eventIndex);

    public List<EventVO> getEventList();

    public List<EventVO> getEventListBetween(LocalDateTime beginDate, LocalDateTime endDate);

    public void addParticipant(EventVO participant);

    public List<EventVO> getParticipantList(int eventIndex);

    public void updateParticipant(EventVO participant);

    public void deleteParticipant(EventVO participant);


}