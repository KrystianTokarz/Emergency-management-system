package server.gui.distributor.phone;

import server.message.mediator.DistributorCommandMediator;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class DistributorPhoneService {

    private DistributorCommandMediator commandMediator;

    public DistributorPhoneService(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }

    public void activePhoneRinging() {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

        Mixer mixer = AudioSystem.getMixer(mixerInfo[0]);

        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        Clip clip = null;
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        URL soundUrl = getClass().getClassLoader().getResource("alert/phone-ringing.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.start();
    }
}
