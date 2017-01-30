package server.gui.distributor.receivingPanel;

import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import server.communication.EchoThread;
import server.gui.distributor.observerReceivingPanel.ObservableConcrete;
import server.gui.distributor.observerReceivingPanel.Observer;
import server.message.Message;
import server.message.mediator.DistributorCommandMediator;
import server.model.message.MessageType;

/**
 * Special service for distributor (which is used for receive notification) which is observer (in Observable pattern)
 */
public class DistributorReceivingService implements Observer{

    private DistributorCommandMediator commandMediator;
    private boolean distributorBreak;
    private ObservableConcrete observableConcrete;
    private Stage distributorReceivingStage;
    private Timeline timeline =  new Timeline();
    private ObservableList<CallerForTable> observableCallerForTableList;
    private boolean state;


    public DistributorReceivingService(DistributorCommandMediator commandMediator, ObservableConcrete observableConcrete,Stage additionalStage){
        this.commandMediator = commandMediator;
        this.observableConcrete = observableConcrete;
        observableConcrete.attach(this);
        this.distributorBreak = false;
        this.distributorReceivingStage = additionalStage;
        this.state = true;
    }

    @Override
    public void update() {
        if(observableConcrete.getState() == true) {
                this.distributorReceivingStage.hide();
                this.timeline.stop();
        }
        else if(observableConcrete.getState() == false && state == true) {
            this.distributorReceivingStage.show();
            this.timeline.play();
        }
    }

    public Timeline giveTimelineForNotificationCaller() {
        return this.timeline;
    }


    public void sendMessageForLocalizationForNotification() {
        Message message = new Message.MessageBuilder(MessageType.SEND_FOR_LOCALIZATION_FOR_NOTIFICATION)
                .object(null)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }
}
