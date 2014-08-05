/**
 * Simple implementation of a Spring Integration transformer that creates an order object
 *
 */
import com.pivotal.example.xd.CallDataRecord


	split = payload.tokenize(",")
    CDRLine = new CallDataRecord ( (split[0]).replace('"','').toString() ,Long.valueOf(split[1].replace('"','')),  Long.valueOf(split[2].replace('"','')),  Long.valueOf(split[3].replace('"','')) , Long.valueOf(split[4].replace('"','')), Long.valueOf(split[5].replace('"','')),  Long.valueOf(split[6].replace('"','')),  Long.valueOf(split[7].replace('"','')), Double.valueOf(split[8].replace('"','')), split[9].replace('"','') ,(split[10].replace('"','')?split[10].replace('"',''):"") ,split[11].replace('"','') , Long.valueOf(split[12].replace('"','')),Long.valueOf(split[13].replace('"','')),Long.valueOf(split[14].replace('"','')))
	return CDRLine
