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
    temperature = '25'
    speed = '0'
    swingon = '0'
    lighton = '0'

    conf_data = {"acuAirOn" : airon, "acuTemperature" : temperature, "acuSpeed" : speed, "acuSwingOn" : swingon, "acuLightOn" : lighton}
    post_data(define_rest_url(), conf_data)

def predict():
    """Predict the next user action."""
    print 'predicting...'

    airon = '1'
    temperature = '19'
    speed = '0'
    swingon = '1'
    lighton = '1'
    conf_data = {"acuAirOn" : airon, "acuTemperature" : temperature, "acuSpeed" : speed, "acuSwingOn" : swingon, "acuLightOn" : lighton}

    post_data(define_rest_url(), conf_data)