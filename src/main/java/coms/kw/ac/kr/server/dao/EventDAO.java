package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.event.EventVO;

import java.util.List;

public interface EventDAO {

    public void insertEvent(EventVO event);

    public EventVO getEvent(int event_idx);

    public void updateEvent(EventVO event);

    public void deleteEvent(int event_idx);

    public List<EventVO> getEventList();

    public List<EventVO> getEventListBetween(String begin_date, String end_date);

    public void addParticipant(EventVO participant);

    public List<EventVO> getParticipantList(int event_idx);

    public void updateParticipant(EventVO participant);

    public void deleteParticipant(EventVO participant);

}