#!/usr/bin/env python3
from gpiozero import OutputDevice
from time import sleep

#chip pull up
ch_pd = OutputDevice(4, True)
# reset on pin 3, active low
rst = OutputDevice(3, False)
# flash mode select on pin 0, active low
flash = OutputDevice(0, False)

ch_pd.on()
flash.on()
sleep(0.5)
rst.on()
sleep(0.5)
rst.off()
sleep(0.5)
flash.off()
rst.close()
flash.close()
