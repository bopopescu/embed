package main.java.core.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import main.java.services.startup.StartupServiceNew;
import uk.co.caprica.lircj.LircBridge;

/**
 * Created by digvijaysharma on 18/03/17.
 */
public class StartupConfigNew {

    /*    To Stash a bean of the VNC Server Instance for manual Control  */
    private Process vncServer;

    /**
     * IR OUT_PIN = 17th Pin Actual
     * IR IN_PIN = 13th Pin Actual
     * Keep emitting stats of remote every 500ms
     * @throws InterruptedException
     */
    public void stayRunning() throws InterruptedException {
//        GpioPinDigitalInput ir_in = StartupService.gpio.provisionDigitalInputPin(RaspiPin.GPIO_27);
//        ir_in.addListener(new GpioPinListenerDigital() {
//            @Override public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                System.out.println("Entered listener");
//                handleSensorInput(event);
//            }
//
//            private void handleSensorInput(GpioPinDigitalStateChangeEvent event) {
//                System.out.println(" --> GPIO PIN STATE CHANGE: ");
//                if (event.getState().isLow()) {
//                    System.out.println("State is low!");
//                }
//                else {
//                    System.out.println("State is high!");
//                }
//            }
//        });
        while(true) {
//            StartupService.useLircClient();
//            Thread.sleep(500);
////            StartupService.changeRelayAccordingToSwitch();
//            System.out.println("buttonName = [" + StartupService.remoteStats.buttonName + "], remoteControlName = [" + StartupService.remoteStats.remoteControlName
//                    + "], repeatCount = [" + StartupService.remoteStats.repeatCount + "]");
        }
    }

    /**
     * Usage ::
     * Map<String, String> env = pb.environment();
     * env.put("VAR1", "myValue");
     * env.remove("OTHERVAR");
     * env.put("VAR2", env.get("VAR1") + "suffix");
     * pb.directory(new File("/home/pi"));
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void send() throws IOException, InterruptedException {
        System.out.println("Sending sample key press");
//        new ProcessBuilder("irsend","SEND_START","vuplus.conf","KEY_PLAY").start().waitFor();
        new ProcessBuilder("irsend","SEND_START","RM-816","KEY_VOLUMEUP").start().waitFor();
    }

    /*
     *   Running on wrong port right now. To be checked later
     */
    public void startVncServer() throws IOException, InterruptedException {
        System.out.println("Starting VNC Server for remote desktop");
        this.vncServer = new ProcessBuilder("vncserver",":1").start();
        this.vncServer.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(this.vncServer.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Bootstrap Lirc Library
     * @throws IOException
     * @throws InterruptedException
     */
    public void restartLircDaemon() throws IOException, InterruptedException {
        System.out.println("Starting LIRC Daemon");
        ProcessBuilder pb = new ProcessBuilder("/bin/bash","init.sh").directory(new File("/home/pi"));
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        p.waitFor();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * The bridge is not capturing lirc events. To be checked later
     */
    public void setupBridge() {
        System.out.println("Wiring LIRC Event Listener");
        LircBridge bridge = new LircBridge("/var/run/lirc/lircd");
        bridge.addLircListener((buttonName, remoteControlName, repeatCount) -> {
            System.out.println("buttonName = [" + buttonName + "], remoteControlName = [" + remoteControlName
                    + "], repeatCount = [" + repeatCount + "]");
            StartupServiceNew.remoteStats.buttonName = buttonName;
            StartupServiceNew.remoteStats.remoteControlName = remoteControlName;
            StartupServiceNew.remoteStats.repeatCount = repeatCount;
        });
        bridge.start();
    }

    /**
     * Stop the program and all running daemons
     * @throws IOException
     * @throws InterruptedException
     */
    public void exit() throws IOException, InterruptedException {
        System.out.println("Exiting");
//        bridge.release();
//        new ProcessBuilder("irsend","SEND_STOP","/home/pi/lircd.conf","KEY_0").start().waitFor();
    }
}
