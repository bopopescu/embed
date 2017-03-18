package main.java.services.startup;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.java.core.config.StartupConfigNew;
import main.java.core.entity.RemoteStats;
import main.java.services.SerialExample;
import main.java.utils.http.HttpSender;
import org.lirc.LircClient;
import org.lirc.TcpLircClient;
//import org.lirc.LircClient;
//import org.lirc.TcpLircClient;

/**
 * Created by digvijaysharma on 26/01/17.
 */
public class StartupServiceNew {

    private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(StartupServiceNew.class);

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

    public static void runCoreCode() throws InterruptedException, IOException {
        LOG.info("Starting Raspberry Pi 3 Model B Server !");
//        controlWifi();
//        wireLedAndSwitch();
//        addDeviceListener();
//        controlBulbUsingRelay();
        LOG.info("Listening for events");
        init();
        gpio.shutdown();
    }

    public static void useLircClient() {
        LOG.info("Init LIRC Client........");
        try {
            LircClient lirc = new TcpLircClient("localhost", 8765);
            String version = lirc.getVersion();
            LOG.info(version);
            List<String> remotes = lirc.getRemotes();
            int i = 0;
            for (String remote : remotes)
                LOG.info(i++ + ":\t" + remote);

            LOG.info("Select a remote by entering its number");
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
                LOG.info(i++ + ":\t" + command);
            LOG.info("Select a command by entering its number");
            line = System.console().readLine();
            if (line == null)
                return;
            int commandNo = Integer.parseInt(line);
            String command = commands.get(commandNo);
            lirc.sendIrCommand(remote, command, 1);
            LOG.info("Command successful");
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
                                LOG.info("Thread " + thread + "Entered listener");
                                handleSensorInput(this.thread, this.myEvent);
                            }
                        });
                    }

                    private synchronized void handleSensorInput(int thread, GpioPinDigitalStateChangeEvent event) {
                        LOG.info(" --> GPIO PIN STATE CHANGE: ");
                        boolean inputState = event.getState().isHigh() ? true : false;
                        while(key != thread);
                        LOG.info("Thread " + thread + " taking LOCK");
//                        lock = LOCK.CLOSE.name();
                        queue.add(inputState);
                        LOG.info("Thread " + thread + " adding IR code bit to queue");
                        LOG.info(" ** Queue Size ------    " + queue.size());
                        if(queue.size()>=50) {
                            ir_led_lirc_input.removeAllListeners();
                            LOG.info("Listeners Removed");
                            dequeue(queue);
                            queue = new ArrayList<Boolean>();
                        }
//                        lock = LOCK.OPEN.name();
                        LOG.info("Thread " + thread + " opened LOCK");
                        key++;
                    }
                });
//        executorService.shutdown();
    }

    private static void dequeue(List<Boolean> code) {
        LOG.info("Dequeueing");
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
                LOG.info(String.valueOf(response));
            } else {
                StringBuffer response = null;
                try {
                    response = new HttpSender().send("GET",
                            "http://192.168.43.55/LED=OFF",
                            "", "", "");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                LOG.info(String.valueOf(response));
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
        LOG.info("Initializing");
        StartupConfigNew config = new StartupConfigNew();
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
            LOG.info(String.valueOf(System.currentTimeMillis()));
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
//                LOG.info("Entered listener");
//                handleSensorInput(led, event);
//            }
//
//            private void handleSensorInput(GpioPinDigitalOutput led,GpioPinDigitalStateChangeEvent event) {
//                LOG.info(" --> GPIO PIN STATE CHANGE: ");
//                if (event.getState().isLow()) {
//                    notifySwitchUnpressed(led);
//                }
//                else {
//                    notifySwitchPressed(led);
//                }
//            }
//
//            private void notifySwitchPressed(GpioPinDigitalOutput led) {
//                LOG.info("Switch is on");
//                led.high();
//            }
//
//            private void notifySwitchUnpressed(GpioPinDigitalOutput led) {
//                LOG.info("Switch is off");
//                led.low();
//            }
//        });
    }
}

