package org.jxch.capital.event;

import org.jxch.capital.event.dto.ServerJobProgressEventDto;
import org.springframework.context.ApplicationEvent;

public class ServerJobProgressEvent extends ApplicationEvent {

    public ServerJobProgressEvent(Object source) {
        super(source);
    }

    @Override
    public ServerJobProgressEventDto getSource() {
        return (ServerJobProgressEventDto) super.getSource();
    }

}
