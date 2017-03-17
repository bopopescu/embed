import pyb
pin = pyb.Pin(14, pyb.Pin.OUT)
for i in range(10):
	pin.value(0) 
	pyb.delay(1000)
	pin.value(1) 
	pyb.delay(1000)

