package server.model.message;

import lombok.Getter;
import lombok.Setter;
import server.model.notification.AccidentType;

import java.io.Serializable;

@Getter
@Setter
public class SecondMessageWithNotification implements Serializable{

    private Long idFirstMessage;

    private String notations;

    private AccidentType accidentType;

    private int numberOfVictims;
}