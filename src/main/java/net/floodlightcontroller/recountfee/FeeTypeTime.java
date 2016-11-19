package net.floodlightcontroller.recountfee;

import java.util.Map;
import java.util.HashMap;

public class FeeTypeTime implements IFeeType {
	
	protected long timecount;
	private static final FeeMatchFields mfs = 
			new FeeMatchFields(FeeMatchFields.MatchMethod.MATCH_NONE, 
					FeeMatchFields.MatchMethod.MATCH_SUBSCRIBER, 
					FeeMatchFields.MatchMethod.MATCH_NONE);

	public FeeTypeTime() {
		// TODO Auto-generated constructor stub
	}
	
	public String getReadable() {
		String ret;
		if (timecount < 300) {
			ret = String.valueOf(timecount).concat(" s");
		} else if (timecount >= 300 && timecount < 5400) {
			ret = String.valueOf((int)timecount/60).concat(" min");
		} else {
			ret = String.valueOf((int)timecount/3600).concat(" h");
		}
		
		return ret;
	}

	@Override
	public void recount(FeeUnit ufee) {
		// TODO Auto-generated method stub
		timecount += ufee.secondcount;
		System.out.println(this.toString());
	}

	@Override
	public long getFee() {
		// TODO Auto-generated method stub
		return timecount;
	}
	
	@Override
	public String toString(){
		return getReadable();
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Charging by time";
	}

	@Override
	public Map<String, String> mapObj() {
		// TODO Auto-generated method stub
		return new HashMap<String, String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("Time", getReadable());
			}
		};
	}

	@Override
	public FeeMatchFields getMatchFields() {
		// TODO Auto-generated method stub
		return mfs;
	}

}
