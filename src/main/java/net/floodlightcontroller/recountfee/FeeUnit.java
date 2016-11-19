package net.floodlightcontroller.recountfee;

import org.projectfloodlight.openflow.protocol.OFFlowRemoved;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.TransportPort;

public class FeeUnit {
	
	public IPv4Address serverAddress;
	public TransportPort serverPort;
	public Subscriber subscriber;
	public long bytecount;
	public long secondcount;
	
	public static FeeUnit fromFlowRemoved(Subscriber ss, OFFlowRemoved msg) {
		IPv4Address ipv4src = msg.getMatch().get(MatchField.IPV4_SRC);
		IPv4Address ipv4dst = msg.getMatch().get(MatchField.IPV4_DST);
		TransportPort portsrc = msg.getMatch().get(MatchField.TCP_SRC);
		TransportPort portdst = msg.getMatch().get(MatchField.TCP_DST);
		long bytes = msg.getByteCount().getValue();
		long seconds = msg.getDurationSec();
		IPv4Address serverAddr = null;
		TransportPort serverport = null;
		
		if (ipv4src.equals(ss.address)) {
			serverAddr = ipv4dst;
			serverport = portdst;
		} else if (ipv4dst.equals(ss.address)) {
			serverAddr = ipv4src;
			serverport = portsrc;
		} else {
			System.out.println("No subscriber");
			return null;
		}
		
		return new FeeUnit(ss, serverAddr, serverport, bytes, seconds);
	}
	
	public FeeUnit(Subscriber ss, IPv4Address serverAddr, TransportPort serverport, long bytes, long seconds) {
		bytecount = bytes;
		secondcount = seconds;
		subscriber = ss;
		serverAddress = serverAddr;
		serverPort = serverport;
	}
	

}
