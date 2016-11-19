package net.floodlightcontroller.recountfee;

import java.util.HashMap;
import java.util.Map;

import org.projectfloodlight.openflow.types.IPv4Address;

public class Subscriber {
	
	public long id;
	public IPv4Address address;
	
	public Map<String, IFeeType> feeObjects = new HashMap<String, IFeeType>();

	public Subscriber(IPv4Address addr, long Id) {
		// TODO Auto-generated constructor stub
		id = Id;
		address = addr;
	}
	
	public Subscriber(long Id) {
		id = Id;
		address = null;
	}
	
	public IFeeType getFeeObject(String feeType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		IFeeType feeObj = null;
		if (!feeObjects.containsKey(feeType)) {
			feeObj = (IFeeType) Class.forName(feeType).newInstance();
			feeObjects.put(feeType, feeObj);
		} else {
			feeObj = feeObjects.get(feeType);
		}
		
		return feeObj;
		
	}

}
