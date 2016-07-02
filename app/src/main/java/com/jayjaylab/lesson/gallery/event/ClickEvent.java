package com.jayjaylab.lesson.gallery.event;

/**
 * Created by jjkim on 2016. 7. 2..
 */
public class ClickEvent {
    ClickEventId id;
    Object[] params;

    // vararg = varaiable arguments.
    public ClickEvent(ClickEventId id, Object... params) {
        this.id = id;
        this.params = params;
    }

    public ClickEventId getId() {
        return id;
    }

    public Object[] getParams() {
        return params;
    }
}
