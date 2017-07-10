#!/usr/bin/python

import os
import sys
import json
from pprint import pprint
import urllib2
from urllib import urlencode
import base64
import time
from time import sleep


HTTP_URL = "localhost"
HTTP_PORT = 8085

class Dictlist(dict):
    def __setitem__(self, key, value):
        try:
            self[key]
        except KeyError:
            super(Dictlist, self).__setitem__(key, [])
        self[key].append(value)

def flatten_json(y):
    out = Dictlist()

    def flatten(x, name=''):
        if type(x) is dict:
            for a in x:
                flatten(x[a], name + a + '_')
        elif type(x) is list:
            i = 0
            for a in x:
                flatten(a, name + '_')
                i += 1
        else:
            out[name[:-1]] = x

    flatten(y)
    return out


def send_data(data, plain):
    things = ['time_stamp', 'client_ip', 'server_id',
        'host_port', 'host_ip', 'id']

    data = data['transaction']

    z = {}
    for i in things:
        z[i] = data[i]

    things = ['protocol', 'http_version', 'uri']
    for i in things:
        z["request_" + i] = data['request'][i]

    z["request_headers"] = []
    for i in data['request']['headers']:
        z["request_headers"].append(i + "=" + data['request']['headers'][i])

    things = ['http_code']
    for i in things:
        z["response_" + i] = data['response'][i]


    url = "http://" + HTTP_URL + ":" + str(HTTP_PORT) \
        + "/sendevent?stream=ModSecurityAuditLogEvent" \
        + "&" + urlencode(z)

    print str(url)
    urllib2.urlopen(url).read()


def read_json_from_file(z):
    try:
        file = open(z, "r") 
        with open(z) as data_file:    
            data = json.load(data_file)
            return data, file.read()
    except:
        pass
    return None, None


def main(p):
    i = 0
    ts = time.time()

    delay = 0
    try:
        delay = int(sys.argv[2])
    except:
        pass

    for root, dirs, files in os.walk(p):
        path = root.split(os.sep)
        for file in files:
            d = os.path.join(*path)
            file = os.path.join(str(d), file)
            d, p = read_json_from_file(file)
            if d != None:
                i = i + 1
                send_data(d, p)
            sleep(delay)

    ts_delta = time.time() - ts

    print "Sent      : " + str(i) + " requests."
    print "Total time: " + str(ts_delta) + " seconds."
    print "reqs/sec  : " + str(i/ts_delta) + " requests per second."
    print "forced del: " + str(delay) + " between the requests."


if __name__ == "__main__":
    path = sys.argv[1]
    main(path)


