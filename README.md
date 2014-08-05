spring-xd examples 
============

This is a set of examples on using SpringXD for building big data and fast data application. This is not meant to represent a realistic use case but it is useful for testing and experimenting

This application processes two streams on real time. Integrated Esper for managing business rule

#### Real time CDR (Call Data Records) data stream Data available in this stream are 

  * cdrType - the call connection type e.g. MOC(Mobile Originated Call (attempt)) MTC(Mobile Terminated Call (attempt))
  * imsi - International Mobile Subscriber Identity of the calling party. 
  * imei - International Mobile Equipment Identity
  * caller - The address of the calling party of the ME performing the action. 
  * callee - The address of the "forwarded-to" party.
  * recordingEntity - The E.164 number of the forwarding Mobile Services Switching Centre
  * location - The Location Area Code and Cell Identity from which the request originated.
  * callReference - A local identifier distinguishing between transactions at the same MS.
  * callDuration - The total duration of the usage of the equipment (based on the second).
  * answerTime - The time at which the call was answered or at which charging commences.
  * seizureTime - The time at which the resource in question was seized
  * releaseTime - The time at which the resource was released     
  * causeForTermination - The reason for the release of the connection. normalRelease 0, partialRecord 1, partialRecordCallReestablishment 2, unsuccessfulCallAttempt 3, stableCallAbnormalTermination 4, cAMELInitCallRelease 5
  * basicService - Teleservice 'emergency call'. 
  * mscAddress - This field contains the E.164 number assigned to the MSC that generate the network call reference.
  
####  Real time POS (point of sales) data set. Data available in this stream are 
  * customer id
  * order id
  * amount
  * store id

### This piece of code was tested in following environment 
  - Sprnig XD RC1 (Single node) http://repo.spring.io/simple/libs-milestone-local/org/springframework/xd/spring-xd/1.0.0.RC1/spring-xd-1.0.0.RC1-dist.zip
  - Rabbit MQ 3.3.4
  - PHD 2 (Works on any Apache Hadoop 2 variants) 
  - Pivotal Single node VM 2.0
  - Esper 4.6
  - Node.js latest


### Running the app 
1) Down load and unpack Pivotal single node VM (tested on 2.0.0-52)

2) Install and configure SpringXD (tested on Version 1.0.1)

3) Download and start Rabbit MQ 

4) Update your spring-xd hadoop config (conf/hadoop.properties) to reflect webhdfs as below:

	fs.default.name=webhdfs://localhost:50070
5) Copy the files from /scripts to $XD_HOME/modules/process/scripts/

6) Copy cep.xml files from xml to $XD_HOME/modules/processor/

7) Copy the dependency jar files from jars directory to $XD_HOME/lib 

8) Build the application and copy the jar file to $XD_HOME/lib 

9) Start Hadoop by running "Start-all.sh" on the VM's desktop, spring XD single node and XD shell

10) Create following streams
 * Dummy stream to see all requests processed by CEP:
 
		stream create --definition "http --port=8001 | log" --name dummywebserver --deploy

 * Stream to ingest CDR data to HDFS:
 
	 	stream create --definition "http --port=8008| hdfs --directory=/xd/tmp/rawdatacdrs --rollover=10000" --name raw_cdr_stream --deploy
 	
 * Stream to ingest order data to HDFS:
 
		stream create --definition "http --port=8009| hdfs --directory=/xd/tmp/raworders --rollover=10000" --name raw_order_stream --deploy
 * Tap to process the query - Find the callers who makes calls longer than X (X could be any integer) seconds close to my store locations
 	
 	    - $XD_HOME/modules/process/scripts/empty.epl need to be replaced with this query  (assuming a person makes a single call more than 3500 seconds being rich)
		select areaCode,callerNo,callDuration from com.pivotal.example.xd.CallDataRecord.win:time(5 sec) where callDuration > 3500 output every 5 seconds
		
create stream using following instructions
	
		stream create --definition "tap:stream:raw_cdr_stream > transform --script=txt2cdr.groovy | cep --file=empty.epl --fields='areaCode,callerNo'|script --location=findStoreWithPromotions.groovy| filter --expression=!payload.contains('null')|log " --name cdr_processor_tap --deploy
 * Run the CDR stream generator command line 
	
	cat /home/gpadmin/Desktop/spring-xd-examples/source/cdr100.txt | sed -e "s/^/curl -d \'POST HTTP\/1.1 localhost Content-Type\:text\/plain Content-Length\:10 text=/"|sed "s/$/\' http\:\/\/localhost\:8008/" | sh
	
  *(I have a bigger file that I can share with you if you need. Email me to kkutti@pivotal.io)*
  
 * Next use case "For every dropped call, give a shopping voucher to the closest store". Change is limited to EPL file 
 
   select areaCode,callerNo,causeForTermination from com.pivotal.example.xd.CallDataRecord.win:time(5 seconds) where causeForTermination!=00 output every 5 seconds
   
 * Create tap  		
 
 	stream create --definition "tap:stream:raw_cdr_stream > transform --script=txt2cdr.groovy | cep --file=empty.epl --fields='areaCode,callerNo'|script --location=findStoreWithPromotions.groovy| filter --expression=!payload.contains('null')|log " --name dropped_call_tap --deploy
 
 * Change the EPL file entry with below query
 
 	select storeId,avg(amt) as avgAmount from com.pivotal.example.xd.RetailOrder.win:time(5 sec) group by storeId output every 5 seconds  
 
 * Point of sales use cases. Find average sales across stores in every 5 seconds

	stream create --definition "tap:stream:raw_order_stream > filter --expression=!payload.contains('BAD_DATA') | transform --script=txt2order.groovy  | cep --file=empty.epl --fields='storeId,avgAmount' |log" --name order_view_tap --deploy

### Simulate streams

 *  CDR data generator. The source file can be found at source directory
 
	cat cdr1.txt | sed -e "s/^/curl -d \'POST HTTP\/1.1 localhost Content-Type\:text\/plain Content-Length\:10 text=/"|sed "s/$/\' http\:\/\/localhost\:8008/" | sh
 
 (I have a bigger file that I can share with you if you need. Email me to kkutti@pivotal.io)
 
 * POS (Sales data generator), **I tested it on Python3**. send_py.py can be found at source
 
   python python_2_create_orders.py

