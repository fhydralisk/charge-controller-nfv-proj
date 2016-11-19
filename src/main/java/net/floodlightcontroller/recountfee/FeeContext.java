package net.floodlightcontroller.recountfee;

import java.util.HashMap;
import java.util.Map;

import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IPv4Address;

public class FeeContext {
	public SubscribersMap subscribers = new SubscribersMap();
	public DevicesMap feeswitches = new DevicesMap();
	public Map<String, String> feetypes = new HashMap<String, String>();
	public final FeeMatchFields defaultMatchFields =
			new FeeMatchFields(FeeMatchFields.MatchMethod.MATCH_ALL,
					FeeMatchFields.MatchMethod.MATCH_ALL,
					FeeMatchFields.MatchMethod.MATCH_ALL);
	
	public FeeContext() {
		// TODO Auto-generated constructor stub
		feetypes.put("net.floodlightcontroller.recountfee.FeeTypeFlow", "Charging by flow");
		feetypes.put("net.floodlightcontroller.recountfee.FeeTypeTime", "Charging by time");
		feetypes.put("net.floodlightcontroller.recountfee.FeeTypeService", "Charging by Service");
		/*
		Subscriber ss = new Subscriber(1);
		ss.address = IPv4Address.of("10.0.0.1");
		subscribers.put(IPv4Address.of("10.0.0.1"), ss);
		*/
	}
	
	public boolean addSwitch(DatapathId switchId, String feeType, FeeDevice.Status status) {
		if (feeType == null || feetypes.containsKey(feeType)) {
			feeswitches.put(switchId, new FeeDevice(FeeDevice.Status.ONLINE, feeType, feetypes.get(feeType)));
		} else
			return false;
		
		return true;
	}
	
	public void addSubscriber(IPv4Address addr, int id) {
		subscribers.put(addr, new Subscriber(addr, id));
	}
	
	public void addSubscribers(SubscribersMap sssm) {
		subscribers.putAll(sssm);
	}
	
	public int deleteSubscribers(SubscribersMap sssm){
		int c = 0;
		for (IPv4Address k : sssm.keySet()) {
			c += subscribers.remove(k) == null ? 0 : 1;
		}
		
		return c;
	}
	
	public int setDevicesType(Map<DatapathId, String> dst) {
		int c = 0;
		if (feetypes.keySet().containsAll(dst.values())) {
			for (Map.Entry<DatapathId, String> e : dst.entrySet()) {
				if (feeswitches.containsKey(e.getKey())) {
					feeswitches.get(e.getKey()).feeType = e.getValue();
					feeswitches.get(e.getKey()).description = feetypes.get(e.getValue());

					c ++;
				}
			}
		}
		return c;
	}
	
	public boolean isIPOfSubscriber(IPv4Address ip) {
		return subscribers.containsKey(ip);
	}
	
	public IPv4Address selectIPOfSubscriber(IPv4Address ip1, IPv4Address ip2) {
		IPv4Address ip = null;
		
		if (isIPOfSubscriber(ip1))
			ip = ip1;
		else if (isIPOfSubscriber(ip2))
			ip = ip2;
		
		return ip;
	}
	
	public Subscriber selectSubscriber(IPv4Address ip1, IPv4Address ip2) {
		IPv4Address ip = selectIPOfSubscriber(ip1, ip2);
		if (ip == null)
			return null;
		
		return subscribers.get(ip);
	}

}

