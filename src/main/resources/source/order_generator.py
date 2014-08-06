#!/usr/bin/python

import re
import shutil
import subprocess
import random
import json
import urllib.request

def main():
  for x in range(10000):
    cust_id = str(x)
    order_id = str(random.randrange(2000,10000))
    order_amount = str('{:20.2f}'.format(random.uniform(100,10000))).strip()
    state_id = str(random.randrange(1,52)).zfill(2) 
    city_id = str(random.randrange(1,99)).zfill(2)
    
    if x % 101 == 0:
       cust_id = "BAD_DATA"
    #make california every x records   
    #if x % 3 == 0:
    #   state_id = "05"
    
    store_id = state_id + city_id
    num_items = str(random.randrange(1,50))
       
    data = json.dumps({"customerId":cust_id,"orderId":order_id,"orderAmount":order_amount,"storeId":store_id,"numItems":num_items})
    print ("POST DATA:" + data)

    binary_data = data.encode('utf-8')

    req = urllib.request.Request("http://localhost:8000", binary_data, {'Content-Type': 'application/json'})
    print ("POST URL:" + req.get_full_url())
    #print ("POST DATA:" + req.data)
    f = urllib.request.urlopen(req)
    response = f.read()
    f.close()
  
if __name__ == "__main__":
  main()
