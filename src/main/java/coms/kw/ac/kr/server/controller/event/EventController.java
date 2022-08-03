package coms.kw.ac.kr.server.controller.event;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.UnauthorizedException;
import coms.kw.ac.kr.server.service.event.EventService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(EventController.BASE_CONTEXT)
public class EventController {
    public static final String BASE_CONTEXT = "/event";

    private final UserInformationService userInformationService;
    private final EventService eventService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public EventController(UserInformationService userInformationService, EventService eventService,
            AuthenticationFacade authFacade) {
        this.userInformationService = userInformationService;
        this.eventService = eventService;
        this.authFacade = authFacade;
    }

    @RequestMapping("/list")
    public String eventList(Model model) {
        List<EventVO> eventList = eventService.getEventList();
        model.addAttribute("eventList", eventList);
        model.addAttribute("adminMode", false);

        return ViewSelector.byLayoutType(model, LayoutType.EVENT_MANAGER);
    }

    @RequestMapping("/participant/{eventIndex}")
    public String participantViewer(@PathVariable int eventIndex, Model model) {
        List<EventVO> eventList = eventService.getParticipantList(eventIndex);

        List<UserInformationVO> userList = new ArrayList<>();
        eventList.stream().map(EventVO::getUser_idx)
                .forEach(i -> userList.add(userInformationService.getUserInformation(i)));

        model.addAttribute("userList", userList);
        model.addAttribute("title", "참여자 목록");
        model.addAttribute("readOnly", true);
        return ViewSelector.layoutOnly(LayoutType.USER_LIST);
    }

    @RequestMapping("/new-event-form")
    public String newEventForm(Model model) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        return ViewSelector.layoutOnly(LayoutType.NEW_EVENT_FORM);
    }

    @RequestMapping("/editor/{eventIndex}")
    public String eventEditor(@PathVariable int eventIndex, Model model) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        EventVO event = eventService.getEvent(eventIndex);

        List<UserInformationVO> userList = new ArrayList<>();
        List<EventVO> participantList = eventService.getParticipantList(eventIndex);
        for (EventVO participant : participantList) {
            userList.add(userInformationService.getUserInformation(participant.getUser_idx()));
        }
        StringBuilder builder = new StringBuilder();
        participantList.stream().map(EventVO::getUser_idx).forEach(i -> {
            builder.append(i);
            builder.append(",");
        });

        model.addAttribute("event", event);
        model.addAttribute("userList", userList);
        model.addAttribute("selected", builder.toString());
        
        UserAuthenticationVO user = authFacade.getPrincipal();
        model.addAttribute("userIndex", user.getUser_idx());

        return ViewSelector.layoutOnly(LayoutType.EVENT_EDITOR);
    }

}
