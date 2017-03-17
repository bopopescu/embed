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
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.java.core.entity.RemoteStats;
import main.java.core.utils.HttpSender;
import main.java.services.SerialExample;
import org.lirc.LircClient;
import org.lirc.TcpLircClient;
import uk.co.caprica.lircj.LircBridge;

//import org.lirc.LircClient;
//import org.lirc.TcpLircClient;

/**
 * Created by digvijaysharma on 26/01/17.
 */
public class StartupServiceNew {

    public enum LOCK {
        OPEN,
        CLOSE
    }
    static GpioPinDigitalInput my_switch_1,app_input,ir_led_lirc_input;        static boolean my_switchValue = false,app_inputValue = false;;
    static GpioPinDigitalOutput relay;                       static boolean relayValue;
    static List<Boolean> queue = new ArrayList<Boolean>();
    static ExecutorService executorService = Executors.newFixedThreadPool(50);
    static int threadCount = 0;
    static String lock = "null";
    static int key = 1;

    /*  Shows The Statistics Of The Remote Being Used */
    public static RemoteStats remoteStats = new RemoteStats();

    /*  Controls GPIO Pins */
    public static GpioController gpio = GpioFactory.getInstance();

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Starting Raspberry Pi 3 Model B Server !");
//        controlWifi();
//        wireLedAndSwitch();
//        addDeviceListener();
//        controlBulbUsingRelay();
        System.out.println("Listening for events");
        init();
        gpio.shutdown();
    }

    public static void useLircClient() {
        System.out.println("Init LIRC Client........");
        try {
            LircClient lirc = new TcpLircClient("localhost", 8765);
            String version = lirc.getVersion();
            System.out.println(version);
            List<String> remotes = lirc.getRemotes();
            int i = 0;
            for (String remote : remotes)
                System.out.println(i++ + ":\t" + remote);

            System.out.println("Select a remote by entering its number");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, LircClient.encodingName));
//            String line = reader.readLine();
            String line = System.console().readLine();
            if (line == null)
                return;
            int remoteNo = Integer.parseInt(line);
            String remote = remotes.get(remoteNo);
            List<String> commands = lirc.getCommands(remote);
            i = 0;
            for (String command : commands)
                System.out.println(i++ + ":\t" + command);
            System.out.println("Select a command by entering its number");
            line = System.console().readLine();
            if (line == null)
                return;
            int commandNo = Integer.parseInt(line);
            String command = commands.get(commandNo);
            lirc.sendIrCommand(remote, command, 1);
            System.out.println("Command successful");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void addDeviceListener() {
                ir_led_lirc_input = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04);
                ir_led_lirc_input.addListener(new GpioPinListenerDigital() {
                    @Override public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                        executorService.execute(new Runnable() {
                            public int thread;
                            public GpioPinDigitalStateChangeEvent myEvent;
                            public void run() {
                                this.thread =  ++threadCount;
                                this.myEvent = event;
                                System.out.println("Thread " + thread + "Entered listener");
                                handleSensorInput(this.thread, this.myEvent);
                            }
                        });
                    }

                    private synchronized void handleSensorInput(int thread, GpioPinDigitalStateChangeEvent event) {
                        System.out.println(" --> GPIO PIN STATE CHANGE: ");
                        boolean inputState = event.getState().isHigh() ? true : false;
                        while(key != thread);
                        System.out.println("Thread " + thread + " taking LOCK");
//                        lock = LOCK.CLOSE.name();
                        queue.add(inputState);
                        System.out.println("Thread " + thread + " adding IR code bit to queue");
                        System.out.println(" ** Queue Size ------    " + queue.size());
                        if(queue.size()>=50) {
                            ir_led_lirc_input.removeAllListeners();
                            System.out.println("Listeners Removed");
                            dequeue(queue);
                            queue = new ArrayList<Boolean>();
                        }
//                        lock = LOCK.OPEN.name();
                        System.out.println("Thread " + thread + " opened LOCK");
                        key++;
                    }
                });
//        executorService.shutdown();
    }

    private static void dequeue(List<Boolean> code) {
        System.out.println("Dequeueing");
//        List<Boolean> irCode = new ArrayList<Boolean>();
//        for(Boolean inputState : code) {
//            irCode.add(inputState);
//        }
        for(Boolean inputState : code) {
            if (inputState) {
                StringBuffer response = null;
                try {
                    response = new HttpSender().send("GET",
                            "http://192.168.43.55/LED=ON",
                            "", "", "");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            } else {
                StringBuffer response = null;
                try {
                    response = new HttpSender().send("GET",
                            "http://192.168.43.55/LED=OFF",
                            "", "", "");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        }
        GpioPinDigitalOutput rpi_code = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
        rpi_code.low();
        for(Boolean inputState : code) {
            if(inputState) {
                rpi_code.high();
            }
            else {
                rpi_code.low();
            }
        }
    }

    /**
     * Test Wifi Program
     */
    private static void controlWifi() {
        try {
            SerialExample.runSerial();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes all the services and listeners
     * @throws IOException
     * @throws InterruptedException
     */
    public static void init() throws IOException, InterruptedException {
        System.out.println("Initializing");
        StartupConfig config = new StartupConfig();
//        config.startVncServer();
//        config.exit();
//        config.restartLircDaemon();
//        config.setupBridge();
//        /* Sends a test remote key press */
//        config.send();
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
        GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
        GpioPinDigitalInput my_switch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04);
        my_switch.setShutdownOptions(true);
        while(true) {
            System.out.println(System.currentTimeMillis());
            led.high();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            led.low();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//            my_switch.addListener(new GpioPinListenerDigital() {
//            @Override public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                System.out.println("Entered listener");
//                handleSensorInput(led, event);
//            }
//
//            private void handleSensorInput(GpioPinDigitalOutput led,GpioPinDigitalStateChangeEvent event) {
//                System.out.println(" --> GPIO PIN STATE CHANGE: ");
//                if (event.getState().isLow()) {
//                    notifySwitchUnpressed(led);
//                }
//                else {
//                    notifySwitchPressed(led);
//                }
//            }
//
//            private void notifySwitchPressed(GpioPinDigitalOutput led) {
//                System.out.println("Switch is on");
//                led.high();
//            }
//
//            private void notifySwitchUnpressed(GpioPinDigitalOutput led) {
//                System.out.println("Switch is off");
//                led.low();
//            }
//        });
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
        System.out.println("Exiting");
//        bridge.release();
//        new ProcessBuilder("irsend","SEND_STOP","/home/pi/lircd.conf","KEY_0").start().waitFor();
    }
}