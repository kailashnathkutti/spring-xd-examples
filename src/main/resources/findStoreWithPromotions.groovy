@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
@Grab('redis.clients:jedis:2.4.2')
@Grapes(@Grab(group='com.rabbitmq', module='amqp-client', version='3.0.1'))


import redis.clients.jedis.*


import java.util.concurrent.ConcurrentHashMap;

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC


import com.rabbitmq.client.*

import com.rabbitmq.client.*


/*
 * This module 
 * 1) connect to Rabbit mq to post user's lat,log which will be later consumed by Node.js web ui
 * 2) Posts all details about user into a http port which can be consumed by other app. Not implemented yet
 * 3) Reads reference data from a Redi hash set
 */
def factory = new com.rabbitmq.client.ConnectionFactory();
factory.setUsername("guest");
factory.setPassword("guest");
factory.setVirtualHost("/");
factory.setHost("localhost")
factory.setPort(5672)

//Using redis to store dimention/reference data
Jedis jedis = new Jedis("localhost")
//println( jedis.hget("storeid_cd","14316"))

//storeIdLocCodeMap=[204:"BDKJTY",541:"BDKRES",2551:"BKTPJG",2554:"BTANIC",2722:"BTPLZA",5425:"CHGIAP",6396:"CHGIPT",6542:"CHGIT2",6958:"CHJMES",10896:"CHOMP2",12220:"CLARKE",12226:"CLIFRD",37869:"CSCLUB",38190:"CWYMAS",43561:"CWYSGP",50397:"ECLAGN",59100:"FABER ",59176:"FAREST",63136:"FERRY ",63139:"HAWPAR",63283:"HEEREN",12157:"HOLNDV",43923:"HORSBG",32617:"HOSALV",9871:"HOSALX",62063:"HOSMTE",20284:"HOSNUH",27575:"HOSSGH",13190:"HOSTPY",6400:"HOSTTS",13831:"ISTANA",31334:"JELITA",26577:"JGBIRD",38860:"KINGAL",21637:"KJIDAM",36234:"KLGKFC",11975:"KUSU",24251:"LAUPAS",29875:"LAZRUS",49875:"LIDO",42745:"LK2HWY",36587:"LK2MAS",43817:"LK2SGP",13826:"MAINLB",4999:"MCRTIE",5916:"MCSEMB",44933:"MOSRNG",4204:"MRNASQ",12894:"MRNSTH",36625:"MRTADM",23614:"MRTALJ",51966:"MRTAMK",9692:"MRTBAY",10643:"MRTBDK",28304:"MRTBDL",21386:"MRTBGS",40173:"MRTBLY",407:"MRTBNV",19325:"MRTBSN",13817:"MRTBTK",23657:"MRTCCK",32274:"MRTCMT",13310:"MRTCMW",39536:"MRTCTH",51476:"MRTDBG",20511:"MRTDP1",25541:"MRTDP2",6842:"MRTDP3",25037:"MRTENS",40037:"MRTGBK",38404:"MRTGDN",22765:"MRTJGE",4769:"MRTKBG",21646:"MRTKJI",1148:"MRTKLG",55385:"MRTKTB",12126:"MRTLKE",1253:"MRTLVD",6753:"MRTMAR",41655:"MRTNOV",22632:"MRTNWT",43561:"MRTORD",10421:"MRTOTR",24091:"MRTPYL",13641:"MRTQTN",28576:"MRTRAF",39466:"MRTRED",26965:"MRTRIS",61259:"MRTSEM",17076:"MRTSMI",19555:"MRTSMT",46498:"MRTTGB",31387:"MRTTGP",46262:"MRTTNM",23354:"MRTTPN",7733:"MRTTPY",6683:"MRTWDL",50544:"MRTYCK",54730:"MRTYSN",26448:"MRTYWT",63444:"NACITY",29856:"NEOTEW",25355:"NESTAD",56125:"NRTHPT",36075:"NTUC",58294:"NTUGPS",43413:"NUSSGP",18985:"NWTCIR",31987:"NYPSEG",28112:"NYPSIT",59839:"OBS-SG",18364:"ORCHAD",63534:"PA-ECP",59794:"PARADZ",4656:"PDNRES",22069:"PGLJTY",41871:"PRATA ",28188:"PRCLWR",48025:"PRCUPR",51232:"PRISPK",55207:"PUAKA ",62378:"QNSTWN",2813:"RAGTME",56452:"RFLITE",57016:"SBWGED",37997:"SELRES",43681:"SERENE",25439:"SGPZOO",36956:"SHWTWR",29191:"SICC",23934:"SIMLMT",15016:"SNTOSA",55076:"SRMEND",10896:"STJOHN",14069:"TCS ",48399:"THMSON",22333:"TIMAH ",12420:"TPECTR",33964:"TPMALL",24686:"TUASJT",20520:"TURFCB",42738:"WARMEM",29825:"WCPIER",38195:"WESTIN",25078:"WTCSGP",1627:"YSNDAM",45158:"YSNRES"]

//storeIdLatLongMap=["106":"1.30793,103.9413","204":"1.34199,103.9253","242":"1.37879,103.7652","365":"1.30946,103.8172","395":"1.33865,103.7787","407":"1.3604,103.9894","541":"1.39095,103.9879","657":"1.35574,103.9887","925":"1.29465,103.852","1057":"1.36436,103.8662","1148":"1.29,103.8465","1203":"1.28234,103.8538","1204":"1.39228,103.9857","1205":"1.45784,103.7674","1214":"1.44772,103.7706","1247":"1.30657,103.933","1345":"1.27373,103.8177","1360":"1.30627,103.8339","1472":"1.31454,103.9889","1840":"1.28259,103.7843","2028":"1.30223,103.8376","2043":"1.31087,103.7951","2272":"1.329,104.3988","2276":"1.34322,103.8403","2309":"1.28713,103.801","2374":"1.30567,103.8347","2551":"1.29498,103.7832","2554":"1.28035,103.836","2722":"1.3369,103.8416","2835":"1.32269,103.8463","3061":"1.30545,103.8433","3090":"1.31791,103.7861","3138":"1.3196,103.7065","3174":"1.33693,103.7794","3383":"1.43844,103.7403","3409":"1.30499,103.8814","3515":"1.22336,103.8626","3579":"1.28094,103.8503","3620":"1.2228,103.8546","3712":"1.30557,103.8317","3961":"1.36093,103.6197","4040":"1.37686,103.5993","4141":"1.34726,103.6363","4209":"1.29522,103.8487","4244":"1.345,103.8242","4494":"1.42691,103.8257","4575":"1.3512,103.8066","4636":"1.29191,103.8579","4654":"1.2804,103.8665","4656":"1.44086,103.8004","4728":"1.31637,103.8827","5023":"1.37,103.8496","5186":"1.27629,103.8542","5305":"1.32405,103.9302","5425":"1.34081,103.8467","5488":"1.30006,103.8559","5496":"1.33948,103.7058","5590":"1.30726,103.7903","5639":"1.35059,103.8481","5798":"1.34924,103.7495","5802":"1.38505,103.7443","5901":"1.31493,103.7653","5907":"1.30245,103.7985","5914":"1.29282,103.8525","5935":"1.29833,103.8467","6180":"1.35666,103.8534","6337":"1.33093,103.9555","6370":"1.33083,103.7612","6396":"1.3197,103.9033","6542":"1.35955,103.7518","6683":"1.34209,103.7326","6842":"1.33237,103.7424","6876":"1.32107,103.9129","6958":"1.42555,103.7618","6961":"1.31154,103.8718","7013":"1.41761,103.8333","7075":"1.34455,103.721","7101":"1.30752,103.8632","7174":"1.43243,103.7733","7665":"1.32038,103.8438","7951":"1.3126,103.8378","8015":"1.30442,103.8323","8076":"1.28168,103.839","8095":"1.31809,103.8929","8118":"1.29419,103.8065","8350":"1.28397,103.8521","8888":"1.28971,103.8168","8896":"1.37303,103.9491","8901":"1.44883,103.8206","9152":"1.34371,103.9531","9194":"1.29994,103.8387","9267":"1.28639,103.8268","9604":"1.27696,103.8459","9622":"1.32749,103.947","9657":"1.35372,103.9449","9704":"1.33186,103.8478","9779":"1.43691,103.7862","9871":"1.38166,103.8448","9987":"1.42919,103.8347","10085":"1.39809,103.748","10237":"1.30322,103.8346","10279":"1.42147,103.704","10332":"1.30432,103.8746","10391":"1.43006,103.8361","10410":"1.37636,103.9553","10664":"1.34834,103.6817","10812":"1.29909,103.7711","10842":"1.31407,103.8393","10896":"1.37911,103.8485","10976":"1.37935,103.8502","11089":"1.41953,103.9281","11130":"1.30143,103.8368","11355":"1.3129,103.9569","11527":"1.29998,103.849","11564":"1.31067,103.7435","11721":"1.42089,103.9104","11903":"1.39575,103.8725","12098":"1.37026,103.8226","12143":"1.36833,103.8042","12220":"1.38345,103.9465","12226":"1.40786,103.9587","12286":"1.2987,103.8043","12365":"1.40325,103.8185","12604":"1.15962,103.7404","12692":"1.46275,103.8386","12695":"1.39934,103.8","12705":"1.32231,103.8136","12707":"1.40352,103.7934","12728":"1.29642,103.8568","12736":"1.35948,103.8198","12849":"1.30413,103.8548","12850":"1.25088,103.8341","12850":"1.44547,103.7077","12862":"1.21982,103.8492","13310":"1.33782,103.834","13332":"1.35431,103.8308","13464":"1.35435,103.7769","13498":"1.35537,103.9428","13579":"1.35285,103.9447","13641":"1.29124,103.6186","13653":"1.3396,103.7947","13717":"1.41576,103.7581","13789":"1.29565,103.7628","13817":"1.29374,103.8534","13851":"1.26433,103.8204","14138":"1.42387,103.8599","14316":"1.40621,103.8446"];

//payload="204"+","+"12334343";

split = payload.tokenize(",")
def areaCode =split[0]
def phoneNo = split[1]
//def storeIdRet = storeIdLocCodeMap[areaCode]	
def storeIdRet = jedis.hget("storeid_cd",areaCode.toString())
//println(storeIdRet)
def returnDataset = null
if (storeIdRet!= null)
	{
		
	def http = new HTTPBuilder( 'http://localhost:8001' )
	
	returnDataset = "[areaCd:"+areaCode +",storeCode:"+storeIdRet+ ",callerPhoneNo:" +phoneNo+"]"
	def postBody = [areaCd: areaCode, storeCode:storeIdRet, callerPhoneNo:phoneNo] // will be url-encoded
	http.post( path: '/', body: postBody,
	requestContentType: URLENC ) { resp ->
		//println "POST Success: ${resp.statusLine}"
		//assert resp.statusLine.statusCode == 201
		//			}
		

	}
		//def storeLatLong = storeIdLatLongMap[areaCode.toString()];
		 storeLatLong = jedis.hget("storeid_latlong",areaCode);
		if (storeLatLong!= null)
		{
			//println(storeLatLong)
			com.rabbitmq.client.Connection conn = factory.newConnection();
			def channel = conn.createChannel();
			
			String exchangeName = "cdr_exchange"
			String routingKey = "cdr_routing_key"
			String queueName = "cdr_ui_queue"
			
			channel.queueBind(queueName, exchangeName, "#");
			def splittedLatLong=storeLatLong.tokenize(",")
			def regUIDataset ='[['+splittedLatLong[0]+','+ splittedLatLong[1]+']]'
			channel.basicPublish(exchangeName, routingKey, MessageProperties.TEXT_PLAIN,
				regUIDataset.getBytes() );
			channel.close()
			conn.close()
		}	
		
}
	if  (returnDataset != null)
		{
			
			//println(returnDataset)
		return returnDataset
		}
