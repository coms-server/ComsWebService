package coms.kw.ac.kr.server.service.event;

import coms.kw.ac.kr.server.dao.EventDAO;
import coms.kw.ac.kr.server.vo.event.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public void insertEvent(EventVO event) {
        eventDAO.insertEvent(event);
    }

    @Override
    public EventVO getEvent(int eventIndex) {
        return eventDAO.getEvent(eventIndex);
    }

    @Override
    public void updateEvent(EventVO event) {
        eventDAO.updateEvent(event);
    }

    @Override
    public void deleteEvent(int eventIndex) {
        eventDAO.deleteEvent(eventIndex);
    }

    @Override
    public List<EventVO> getEventList() {
        List<EventVO> list = eventDAO.getEventList();
        return list;
    }

    @Override
    public List<EventVO> getEventListBetween(LocalDateTime beginDate, LocalDateTime endDate) {
        return eventDAO.getEventListBetween(beginDate.toString(), endDate.toString());
    }

    @Override
    public void addParticipant(EventVO participant) {
        eventDAO.addParticipant(participant);
    }

    @Override
    public List<EventVO> getParticipantList(int eventIndex) {
        return eventDAO.getParticipantList(eventIndex);
    }

    @Override
    public void updateParticipant(EventVO participant) {
        eventDAO.updateEvent(participant);
    }

    @Override
    public void deleteParticipant(EventVO participant) {
        eventDAO.deleteParticipant(participant);
    }
    
}