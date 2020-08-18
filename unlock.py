#!/usr/bin/env python

import cgi
import subprocess
import pigpio
import time
import cgitb
cgitb.enable()
#pigs = ""
#pigpio.exceptions = False  Get error code returned, not exceptions.
PIN1 = 4 # pink wire
PIN2 = 17 # orange wire
PIN3 = 23 # blue wire
PIN4 = 24 # yellow wire
delay = 0.003
steps = 250
pi=pigpio.pi() # initialize pigpio to use GPIO
pi.write(PIN1, 1) # set pin4 to 0
pi.write(PIN2, 1) # set pin17 to 0
pi.write(PIN3, 1) # set pin23 to 0
pi.write(PIN4, 1) # set pin24 to 0
def Step0():
    pi.write(PIN2, 0)
    time.sleep(delay)
    pi.write(PIN2, 1)
def Step1():
    pi.write(PIN2, 0)
    pi.write(PIN4, 0)
    time.sleep(delay)
    pi.write(PIN2, 1)
    pi.write(PIN4, 1)
def Step2():
    pi.write(PIN4, 0)
    time.sleep(delay)
    pi.write(PIN4, 1)
def Step3():
    pi.write(PIN4, 0)
    pi.write(PIN1, 0)
    time.sleep(delay)
    pi.write(PIN4, 1)
    pi.write(PIN1, 1)
def Step4():
    pi.write(PIN1, 0)
    time.sleep(delay)
    pi.write(PIN1, 1)
def Step5():
    pi.write(PIN1, 0)
    pi.write(PIN3, 0)
    time.sleep(delay)
    pi.write(PIN1, 1)
    pi.write(PIN3, 1)
def Step6():
    pi.write(PIN3, 0)
    time.sleep(delay)
    pi.write(PIN3, 1)
def Step7():
    pi.write(PIN2, 0)
    pi.write(PIN3, 0)
    time.sleep(delay)
    pi.write(PIN2, 1)
    pi.write(PIN3, 1)
for i in range(steps):
    Step0()
    Step1()
    Step2()
    Step3()
    Step4()
    Step5()
    Step6()
    Step7()
    time.sleep(delay)
pi.write(PIN1, 0)
pi.write(PIN2, 0)
pi.write(PIN3, 0)
pi.write(PIN4, 0)
