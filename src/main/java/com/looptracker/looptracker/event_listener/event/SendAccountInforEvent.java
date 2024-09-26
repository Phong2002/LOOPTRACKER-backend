package com.looptracker.looptracker.event_listener.event;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import java.util.Map;


@Getter
@Setter
public class SendAccountInforEvent extends ApplicationEvent {
    private String email;
    private Map<String,Object> model;
    public SendAccountInforEvent(Object source,String email, Map<String,Object> model) {
        super(source);
        this.email = email;
        this.model = model;
    }
}

