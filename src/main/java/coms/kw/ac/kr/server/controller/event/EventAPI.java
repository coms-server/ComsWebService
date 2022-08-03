package coms.kw.ac.kr.server.controller.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.event.EventService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(EventAPI.API_CONTEXT)
public class EventAPI {
    public static final String API_CONTEXT = "/event";
    public static final String PARTICIPANT_URI = "/participant";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(EventAPI.class);

    private final EventService eventService;
    private final UserInformationService userInformationService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public EventAPI(EventService eventService, UserInformationService userInformationService, AuthenticationFacade authFacade) {
        this.eventService = eventService;
        this.userInformationService = userInformationService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = "/monthly/{year}-{month}", method = RequestMethod.GET)
    public ResponseEntity<String> monthlyEvent(@PathVariable int year, @PathVariable int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        // End of year
        if (month == 12) {
            year += 1;
            month = 0;
        }
        LocalDateTime end = LocalDateTime.of(year, month + 1, 1, 0, 0).minusSeconds(1);
        
        JsonNode node;
        try {
            List<EventVO> eventList = eventService.getEventListBetween(start, end);
            node = mapper.valueToTree(eventList);
        } catch (Exception exception) {
            logger.error("Failed to load monthly event.", exception);
            return CommonHttpResponse.BAD_REQUEST(String.class);
        }
        return CommonHttpResponse.OK(node.toPrettyString());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> insertEvent(@RequestBody EventVO event) {
        // Authoriazation check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Insert event
        try {
            eventService.insertEvent(event);
        } catch (Exception exception) {
            logger.error("Failed to add event.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEvent(@RequestBody EventVO event) {
        // Authoriazation check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Update event
        try {
            eventService.updateEvent(event);
        } catch (Exception exception) {
            logger.error("Failed to add event.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }
        return CommonHttpResponse.OK;
    }
    
    @RequestMapping(value = "/{eventIndex}", method = RequestMethod.GET)
    public ResponseEntity<String> getEvent(@PathVariable int eventIndex) {
        
        JsonNode node;
        try {
            EventVO eventInfo = eventService.getEvent(eventIndex);
            node = mapper.valueToTree(eventInfo);
            
        } catch (Exception exception) {
            logger.error("Failed to load event.", exception);
            return CommonHttpResponse.BAD_REQUEST(String.class);
        }
        return CommonHttpResponse.OK(node.toPrettyString());
    }

    @RequestMapping(value = "/{eventIndex}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEvent(@PathVariable int eventIndex) {
        // Authoriazation check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Check value
        if(eventService.getEvent(eventIndex) == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Delete event
        try{
            List<EventVO> participants = eventService.getParticipantList(eventIndex);
            for (EventVO p : participants)
                eventService.deleteParticipant(p);

            eventService.deleteEvent(eventIndex);
        }catch(Exception exception) {
            logger.error("Failed to delete event.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }
        
    @RequestMapping(value = PARTICIPANT_URI + "/{eventIndex}", method = RequestMethod.GET)
    public ResponseEntity<String> getParticipant(@PathVariable int eventIndex) {
        
        JsonNode node;
        try {            
            List<UserInformationVO> userList = new ArrayList<>();
            List<EventVO> participantList = eventService.getParticipantList(eventIndex);
            for (EventVO participant : participantList) {
                userList.add(userInformationService.getUserInformation(participant.getUser_idx()));
            }
            
            node = mapper.valueToTree(userList);
        } catch (Exception exception) {
            logger.error("Failed to load participant list.", exception);
            return CommonHttpResponse.BAD_REQUEST(String.class);
        }
        return CommonHttpResponse.OK(node.toPrettyString());
    }
    
    @RequestMapping(value = PARTICIPANT_URI + "/join/{eventIndex}", method = RequestMethod.PUT)
    public ResponseEntity<Object> joinParticipant(@PathVariable int eventIndex, @RequestBody EventVO participant) {
        
        UserAuthenticationVO user = authFacade.getPrincipal();
        if(user.getUser_idx() != participant.getUser_idx()) {
            return CommonHttpResponse.UNAUTHORIZED;
        }
        
        List<EventVO> participantList = eventService.getParticipantList(eventIndex);
        int user_idx = participant.getUser_idx();
        for(EventVO p : participantList) {
            if(p.getUser_idx() == user_idx) {
                return CommonHttpResponse.OK;
                // return CommonHttpResponse.ALREADY_REPORTED; ajax.js 뜯어서 상태코드 처리가능하도록 할 것
            }
        }
        
        try {
            participant.setEvent_idx(eventIndex);
            participant.setIs_participated(true);
            
            eventService.addParticipant(participant);
        } catch (Exception exception) {
            logger.error("Failed to update event participants.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = PARTICIPANT_URI + "/{eventIndex}", method = RequestMethod.PUT)
    public ResponseEntity<Object> setParticipantList(@PathVariable int eventIndex,
            @RequestBody List<String> participantList) {
        // Authoriazation check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Set participants
        try {
            List<EventVO> participants = eventService.getParticipantList(eventIndex);
            for (EventVO p : participants)
                eventService.deleteParticipant(p);

            List<Integer> userIndex = new ArrayList<>();
            participantList.stream().forEach(i -> userIndex.add(Integer.parseInt(i)));

            for (int i : userIndex) {
                EventVO participant = new EventVO();
                participant.setEvent_idx(eventIndex);
                participant.setUser_idx(i);
                participant.setIs_participated(true);
                eventService.addParticipant(participant);
            }
        } catch (Exception exception) {
            logger.error("Failed to update event participants.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

}