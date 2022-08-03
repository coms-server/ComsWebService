package coms.kw.ac.kr.server.controller;

import org.springframework.ui.Model;

/***
 * {@code @ReqeustMapping}메소드에서 리턴할 view(layout)를 선택해주는 핸들러.
 */
public class ViewSelector {

    public static final String REDIRECT_TO_INDEX = "redirect:/index";

    public static String byLayoutType(Model model, LayoutType type) {
        model.addAttribute("layoutType", type.getJsp());
        return "index";
    }

    public static String layoutOnly(LayoutType type) {
        return "layout/" + type.getJsp();
    }
}