#!/usr/bin/python
#Script to generate historical order data
#kkutti@gopivotal.com
import re
import shutil
import subprocess
import random
import time


def strTimeProp(start, end, format, prop):


    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))
    ptime = stime + prop * (etime - stime)
    
    return time.strftime(format, time.localtime(ptime))


def order_date(start, end, prop):
    return strTimeProp(start, end, '%m/%d/%Y %H:%M', prop)
    
def main():
  for x in range(500000):
    cust_id = str(x)
    order_id = str(random.randrange(2000,10000))
    order_amount = str('{:20.2f}'.format(random.uniform(100,10000))).strip()
    state_id = str(random.randrange(1,52)).zfill(2) 
    city_id = str(random.randrange(1,99)).zfill(2)
    
    if x % 101 == 0:
       cust_id = "BAD_DATA"

    store_id = state_id + city_id
    num_items = str(random.randrange(1,50))
       
    data = cust_id + ','+order_id +','+ order_amount +','+store_id+','+ num_items+','+(order_date("1/1/2013 12:00", "07/26/2014 12:00", random.random()))
    print (data)
  
if __name__ == "__main__":
  main()
