package net.floodlightcontroller.recountfee;

public class FeeMatchFields {

	public enum MatchMethod {
		MATCH_NONE,
		MATCH_SRC,
		MATCH_DST,
		MATCH_SUBSCRIBER,
		MATCH_SERVER,
		MATCH_ALL
	}
	
	public MatchMethod matchEther;
	public MatchMethod matchIP;
	public MatchMethod matchTransportPort;
	
	
	public FeeMatchFields() {
		// TODO Auto-generated constructor stub
	}
	
	public FeeMatchFields(MatchMethod ether, MatchMethod ip, MatchMethod port) {
		matchEther = ether;
		matchIP = ip;
		matchTransportPort = port;
	}

}
