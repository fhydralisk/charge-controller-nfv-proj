package net.floodlightcontroller.recountfee;

import java.util.HashMap;
import java.util.Map;

public class FeeTypeService implements IFeeType {

	
	protected long bytescount;
	private static final FeeMatchFields mfs = 
			new FeeMatchFields(FeeMatchFields.MatchMethod.MATCH_ALL, 
					FeeMatchFields.MatchMethod.MATCH_ALL, 
					FeeMatchFields.MatchMethod.MATCH_ALL);

	public FeeTypeService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void recount(FeeUnit ufee) {
		// TODO Auto-generated method stub
		bytescount = ufee.bytecount + (bytescount);
		System.out.println(this.toString());
	}

	@Override
	public long getFee() {
		// TODO Auto-generated method stub
		return bytescount;
	}
	
	@Override
	public String toString(){
		return String.valueOf(bytescount/1024).concat(" KB");
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Charging by service";
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
				put("Service Flow Amount", String.valueOf(bytescount/1024).concat(" KB"));
			}
		};
	}

	@Override
	public FeeMatchFields getMatchFields() {
		// TODO Auto-generated method stub
		return mfs;
	}

}