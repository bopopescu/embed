package main.java;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import uk.co.caprica.lircj.LircBridge;

/**
 * Created by digvijaysharma on 26/01/17.
 */
public class StartupService {

    static String buttonName;

    static String remoteControlName;

    static int repeatCount;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Starting Raspberry Pi 3 Model B Server !");
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
        GpioPinDigitalInput my_switch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04);
        my_switch.setShutdownOptions(true);
        led.high();
        my_switch.addListener(new GpioPinListenerDigital() {
            @Override public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                System.out.println("Entered listener");
                handleSensorInput(led, event);
            }

            private void handleSensorInput(GpioPinDigitalOutput led,GpioPinDigitalStateChangeEvent event) {
                System.out.println(" --> GPIO PIN STATE CHANGE: ");
                if (event.getState().isLow()) {
                    notifySwitchUnpressed(led);
                }
                else {
                    notifySwitchPressed(led);
                }
            }

            private void notifySwitchPressed(GpioPinDigitalOutput led) {
                System.out.println("Switch is on");
                led.high();
            }

            private void notifySwitchUnpressed(GpioPinDigitalOutput led) {
                System.out.println("Switch is off");
                led.low();
            }
        });
        System.out.println("Listening for events");
        init();
        gpio.shutdown();
    }

    public static void init() throws IOException, InterruptedException {
        System.out.println("Initializing");
        StartupConfig config = new StartupConfig();
        config.startVncServer();
        config.exit();
        config.restartLircDaemon();
        config.setupBridge();
        /* Sends a test remote key press */
        config.send();
        config.stayRunning();
        config.exit();
    }
}

class StartupConfig {

    private Process vncServer;

    public void stayRunning() throws InterruptedException {
        while(true) {
            Thread.sleep(500);
            System.out.println("buttonName = [" + StartupService.buttonName + "], remoteControlName = [" + StartupService.remoteControlName
                    + "], repeatCount = [" + StartupService.repeatCount + "]");
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
        new ProcessBuilder("irsend","SEND_START","vuplus.conf","KEY_PLAY").start().waitFor();
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
            StartupService.buttonName = buttonName;
            StartupService.remoteControlName = remoteControlName;
            StartupService.repeatCount = repeatCount;
        });
        bridge.start();
    }

    public void exit() throws IOException, InterruptedException {
//        bridge.release();
        new ProcessBuilder("irsend","SEND_STOP","vuplus.conf","KEY_PLAY").start().waitFor();
    }
};