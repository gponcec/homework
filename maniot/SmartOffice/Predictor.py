#!/usr/bin/python
"""Get data from the rest service 'communicator'."""

import json
import ConfigParser
import requests

def define_rest_url():
    """Define rest url."""
    config = ConfigParser.ConfigParser()
    config.read("config.ini")
    return 'http://'+ config.get('RestConnection', 'Server') + ':'+ config.get('RestConnection', 'Port') + '/rest/sendConfiguration'

def get_data(url):
    """Get data from the rest service 'communicator'."""
    response = requests.get(url)
    print response.text

def post_data(url, conf_data):
    """Send data to the server via the rest service 'communicator'."""
    data_json = json.dumps(conf_data)
    headers = {'Content-type': 'application/json'}

    response = requests.post(url, data=data_json, headers=headers)
    if response.ok:
        print 'Data send succesfully'
    else:
        response.raise_for_status()

def train():
    """Train the markov chain by using new user feedback."""
    print 'training...'

def disactivate_devices():
    """Disactivate all the device of the room."""
    print 'desactivating devices...'
    airon = '0'
    temperature = '17'
    speed = '0'
    swingon = '0'
    lighton = '0'
    openshade = '3'

    conf_data = {"acuAirOn" : airon, "acuTemperature" : temperature, "acuSpeed" : speed, "acuSwingOn" : swingon, "roomLightOn" : lighton, "roomOpenShade" : openshade}
    post_data(define_rest_url(), conf_data)


def predict(env_temperature, env_pressure, env_humidity, env_noise, env_airQuality, env_luminosity1, env_luminosity2,env_openWindow,env_openDoor, date , time):
    """Predict the next user action."""
    print 'predicting...'

    if env_temperature == -1 and env_pressure == -1 and env_humidity == -1 and env_noise == -1 and env_airQuality == -1 and env_luminosity1 == -1 and env_luminosity2 == -1 and env_openWindow == -1 and env_openDoor == -1 and  date == -1 and time == -1:
        print "No changes"
    else:
        if env_temperature >= 20:
            airon = '1' 
        else: 
            airon = '0'

        #if env_luminosity1 < 920:
        #    lighton = '1'
        airon= '1'    
        temperature = '22'
        speed = '0'
        swingon = '1'
        lighton = '1'
	openshade = '0'
        isUserOrSystem = '0'
        conf_data = {"acuAirOn" : airon, "acuTemperature" : temperature, "acuSpeed" : speed, "acuSwingOn" : swingon, "roomLightOn" : lighton, "roomOpenShade": openshade, "isUserOrSystem": isUserOrSystem}
        post_data(define_rest_url(), conf_data)

'''
def predict(env_temperature, env_pressure, env_humidity, env_noise, env_airQuality, env_luminosity1, env_luminosity2, env_date,env_time):
    """Predict the next user action."""
    print 'predicting...'

    
    if env_temperature == -1 and env_pressure == -1 and env_humidity == -1 and env_noise == -1 and env_airQuality == -1 and env_luminosity1 == -1 and env_luminosity2 == -1 and env_date == -1 and env_time == -1:
        print "No changes"
    else:
        if env_temperature >= 17:
            airon = '1' 
        else: 
            airon = '0'

        #if env_luminosity1 < 920:
         #   lighton = '1'
        
            
        airon = 1;
        temperature = '19'
        speed = '0'
        swingon = '1'
        lighton = '1'
        openshade = '100'
        weather = 6
        luminosity = 2
        noise = 4
        conf_data = {"acuAirOn" : airon, "acuTemperature" : temperature, "acuSpeed" : speed, "acuSwingOn" : swingon, "roomLightOn" : lighton, "roomOpenShade" : openshade, "userWeatherRating" : weather, "userLuminosityRating" : luminosity, "userNoiseRating" : noise }
        post_data(define_rest_url(), conf_data)
'''
