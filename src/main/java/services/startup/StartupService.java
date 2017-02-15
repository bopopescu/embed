package main.java.services.startup;

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
import main.java.core.entity.RemoteStats;
import uk.co.caprica.lircj.LircBridge;

/**
 * Created by digvijaysharma on 26/01/17.
 */
public class StartupService {

    static GpioPinDigitalInput my_switch_1,app_input;    static boolean my_switchValue = false;
    static GpioPinDigitalOutput relay;          static boolean relayValue;
    static boolean app_inputValue = false;

    /*  Shows The Statistics Of The Remote Being Used */
    public static RemoteStats remoteStats = new RemoteStats();

    /*  Controls GPIO Pins */
    public static GpioController gpio = GpioFactory.getInstance();

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Starting Raspberry Pi 3 Model B Server !");
//        wireLedAndSwitch();
//        controlBulbUsingRelay();
        System.out.println("Listening for events");
        init();
        gpio.shutdown();
    }

    /**
     * Initializes all the services and listeners
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * Test LED-SWITCH Program ---> Controls LED on and off with switch presses and logs the presses
     */
    private static void controlBulbUsingRelay() {
        relay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
        relay.high();
//        my_switch_1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04);
//        app_input = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05);
//        my_switch_1.setShutdownOptions(true);
//        relay.low();
//        relayValue = true;
//        my_switch_1.addListener(new GpioPinListenerDigital() {
//            @Override public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                relay.setState(event.getState());
//            }
//        });
//        app_input.addListener(new GpioPinListenerDigital() {
//            @Override public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                relay.setState(event.getState());
//            }
//        });
    }

    public static void changeRelayAccordingToSwitch() {
//        /*     Switch is ON and APP says ON     */
//        if(my_switchValue && app_input) {
//            relay.high();
//        }
//        /*     Switch is ON and APP says OFF     */
//        else if(my_switchValue && !app_input) {
//            relay.high();
//        }
//        /*     Switch is OFF and APP says ON     */
//        else if(!my_switchValue && app_input) {
//            relay.high();
//        }
//        /*     Switch is OFF and APP says OFF     */
//        else if(!my_switchValue && !app_input) {
//            relay.low();
//        }
    }

    /**
     * Test LED-SWITCH Program ---> Controls LED on and off with switch presses and logs the presses
     */
    private static void wireLedAndSwitch() {
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
    }
}

class StartupConfig {

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
            Thread.sleep(500);
//            StartupService.changeRelayAccordingToSwitch();
            System.out.println("buttonName = [" + StartupService.remoteStats.buttonName + "], remoteControlName = [" + StartupService.remoteStats.remoteControlName
                    + "], repeatCount = [" + StartupService.remoteStats.repeatCount + "]");
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
        new ProcessBuilder("irsend","SEND_START","/home/pi/lircd.conf","KEY_0").start().waitFor();
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
            StartupService.remoteStats.buttonName = buttonName;
            StartupService.remoteStats.remoteControlName = remoteControlName;
            StartupService.remoteStats.repeatCount = repeatCount;
        });
        bridge.start();
    }

    /**
     * Stop the program and all running daemons
     * @throws IOException
     * @throws InterruptedException
     */
    public void exit() throws IOException, InterruptedException {
//        bridge.release();
        new ProcessBuilder("irsend","SEND_STOP","/home/pi/lircd.conf","KEY_0").start().waitFor();
    }
}