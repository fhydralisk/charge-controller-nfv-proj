package net.floodlightcontroller.recountfee;

import java.util.Collection;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.restlet.resource.Get;

public class RecountFeeSubscriber extends RecountFeeRestletRequestBase {

	@Get("json")
	public FeeResponse<SubscriberPackage> getDevice() {
		FeeResponse<SubscriberPackage> sp = new FeeResponse<SubscriberPackage>();
		String subscriberIp = (String) getRequestAttributes().get("id");
		IPv4Address subscriberAddr;
		try {
			subscriberAddr = IPv4Address.of(subscriberIp);
		} catch (IllegalArgumentException e){
			sp.error = new ErrorCode(404);
			return sp;
		}
		/*try {
			Integer subscriberId = Integer.parseInt(subscriber);
		} catch (NumberFormatException e) {
			sp.error = new ErrorCode(404);
		}*/
		System.out.println("query for subscriber " + subscriberIp);
		if (context.subscribers.containsKey(subscriberAddr)){
			Subscriber ss  = context.subscribers.get(subscriberAddr);
			sp.error = new ErrorCode(200);
			sp.payload = new SubscriberPackage();
			sp.payload.id = ss.id;
			sp.payload.ip = ss.address.toString();
			sp.payload.fee = ss.feeObjects.values();
		} else {
			sp.error = new ErrorCode(404);
		}
		
		return sp;
	}
}

class SubscriberPackage {
	public long id;
	public String ip;
	public Collection<IFeeType> fee;
	
}

