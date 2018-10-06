"""
An example of a simulation.

This example implement a simple event simulation that model the revving
engine and emit data events on gear, rpm, belt and vehicle speed.

For information on how to use SimPy, go to https://simpy.readthedocs.io/en/latest/index.html.
"""

# we need the carapi to push events to AndroidCAR.
import carapi
# we need the property database for the event definitions.
import propdb
# SimPy is used for discrete event simulation and we use the realtime variant.
import simpy.rt
# random is used to generate pseudo-random numbers.
import random
# datetime contains methods for handling dates and times.
from datetime import datetime

# a type for simulating the state of the vehicle.
class vehicle_dynamics: pass

vehicle_data = vehicle_dynamics()

vehicle_data.speed = 0.0
vehicle_data.gear = 1
vehicle_data.rpm = 0.0

def revProcess(env):
    """
    This process simulate the revving of the engine.
    """
    while True:
        
        if vehicle_data.rpm < 3000:
            vehicle_data.rpm += 50
        elif vehicle_data.gear < 5:
            vehicle_data.rpm = vehicle_data.speed * 100.0 / (vehicle_data.gear + 1)
            vehicle_data.gear += 1
            
        vehicle_data.speed = vehicle_data.rpm * vehicle_data.gear / 100.0

        yield env.timeout(0.025)
     # turn ignition state off at end of simulation.
    api.injectInteger(propdb.PROP_IGNITION_STATE, propdb.IGNITION_STATE_OFF)

def gearReporter(env, api):
    """
    This process send the current gear to the AndroidCAR instance.
    """
    while True:
        
        api.injectInteger(propdb.PROP_GEAR_SELECTION, vehicle_data.gear)
        yield env.timeout(0.2)
        
def speedReporter(env, api):
    """
    This process send the current speed to the AndroidCAR instance.
    """
    while True:
        
        api.injectFloat(propdb.PROP_PERF_VEHICLE_SPEED, vehicle_data.speed)
        yield env.timeout(0.1)

def rpmReporter(env, api):
    """
    This process send the current rpm to the AndroidCAR instance.
    """
    while True:
        
        api.injectFloat(propdb.PROP_ENGINE_RPM, vehicle_data.rpm)
        yield env.timeout(0.1)

def beltReporter(env, api):
    """
    This process send the belt status to the AndroidCAR instance.
    """
    api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_LEFT, 0, True)
    while True:
        
        """if random.randrange(0, 8) == 2:
            api.injectInteger(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_LEFT, random.randrange(0, 2))
        if random.randrange(0, 8) == 2:
            api.injectInteger(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_RIGHT, random.randrange(0, 2))
        if random.randrange(0, 8) == 2:
            api.injectInteger(propdb.PROP_SEAT_BELT_BUCKLED_BACK_LEFT, random.randrange(0, 2))
        if random.randrange(0, 8) == 2:
            api.injectInteger(propdb.PROP_SEAT_BELT_BUCKLED_BACK_MIDDLE, random.randrange(0, 2))
        if random.randrange(0, 8) == 2:
            api.injectInteger(propdb.PROP_SEAT_BELT_BUCKLED_BACK_RIGHT, random.randrange(0, 2))
        yield env.timeout(0.2)"""
        #if random.randrange(0, 16) == 0:
         #   api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_LEFT, 0, True if random.randrange(0, 2) == 1 else False)
        #if random.randrange(0, 16) == 0:
            #api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_RIGHT, 0, True if random.randrange(0, 2) == 1 else False)
        if random.randrange(0, 16) == 0:
            api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_RIGHT, 0, True if random.randrange(0, 2) == 1 else False)
        if random.randrange(0, 16) == 0:
            api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_BACK_LEFT, 0, True if random.randrange(0, 2) == 1 else False)
        if random.randrange(0, 16) == 0:
            api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_BACK_RIGHT, 0, True if random.randrange(0, 2) == 1 else False)
        yield env.timeout(0.4)

    # Unbuckle belts at end of simulation.
    api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_LEFT, 0, False)
    api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_FRONT_RIGHT, 0, False)
    api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_BACK_LEFT, 0, False)
    api.injectZonedBoolean(propdb.PROP_SEAT_BELT_BUCKLED_BACK_RIGHT, 0, False)


# run
if __name__ == '__main__':
    random.seed(datetime.now())
    # create an interface to the AndroidCAR running on an emulator.
    api = carapi.AndroidCARInterface()
    # create an event simulation environment.
    env = simpy.rt.RealtimeEnvironment(factor=1)
    # create the processes.
    revp = env.process(revProcess(env))
    gearp = env.process(gearReporter(env, api))
    speedp = env.process(speedReporter(env, api))
    rpmp = env.process(rpmReporter(env, api))
    beltp = env.process(beltReporter(env, api))
    # start simulation and run it for 60 seconds.
    env.run(until=60.0)
