package coms.kw.ac.kr.server.controller;

public enum LayoutType {
    MAIN    ("main"),
    SIGNUP  ("signup"),
    LOGIN   ("login"),
    ABOUT_COMS          ("about_coms"),
    INFORMATION_POLICY  ("information_policy"),

    PROFILE             ("user/profile/big_box"),
    SMALL_PROFILE       ("user/profile/small_box"),
    MYPAGE              ("user/mypage"),
    PERSONAL_INFORMATION("user/personal_information"),
    USER_LIST           ("user/user_list"),

    BOARD   ("article/board"),
    EDITOR  ("article/editor"),
    READER  ("article/reader"),
    COMMENT ("article/comment_box"),
    
    ADMIN           ("admin/admin_index"),
    USER_SELECTOR   ("admin/user_selector"),
    USER_MANAGER    ("admin/user_manager"),
    EVENT_MANAGER   ("admin/event_manager"),
    NOTIFICATION    ("admin/notification_sender"),

    EXEC_LIST       ("admin/executive_list"),
    NEW_EXEC_FORM   ("admin/new_executive_form"),
    EXEC_EDITOR     ("admin/executive_editor"),

    NEW_EVENT_FORM  ("event/new_event_form"),
    EVENT_EDITOR    ("event/event_editor");

    private final String jsp;

    private LayoutType(String jsp) {
        this.jsp = jsp;
    }

    public String getJsp() {
        return this.jsp;
    }
}