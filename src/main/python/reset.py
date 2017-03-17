#!/usr/bin/env python3
from gpiozero import OutputDevice
from time import sleep

#chip pull up
ch_pd = OutputDevice(4, True)
ch_pd.on()

# reset on pin 3, active low
rst = OutputDevice(3, False)

rst.on()
sleep(0.5)
rst.off()
rst.close()