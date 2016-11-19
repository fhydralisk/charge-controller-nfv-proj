package net.floodlightcontroller.recountfee;

public class FeeDevice {
	public enum Status {
		OFFLINE("Offline"), ONLINE("Online");
		private String name;
		private Status(String str) {
			this.name = str;
		}
		@Override
		public String toString(){
			return this.name;
		}
	};
	public String feeType  = null;
	public Status status = Status.ONLINE;
	public String description = null;
	
	public FeeDevice() {
		
	}
	
	public FeeDevice(Status stat, String type, String des) {
		feeType = type;
		status = stat;
		description = des;
	}

}
