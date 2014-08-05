import com.espertech.esper.client.EventBean

split = fields.split(",")
def output = []  //Keep track of the list of events passed in from Esper.  Split and outout as list of delimited strings
split.eachWithIndex() { 
	obj, i -> 
		if(i == 0) {
			payload.getAt(obj).each() {
				obj1 -> output.add(new StringBuffer() << obj1)
			};
		} else {
			payload.getAt(obj).eachWithIndex() {				
				obj1, j -> output.get(j) << (delimiter + obj1)
			};
		}
	};
return output