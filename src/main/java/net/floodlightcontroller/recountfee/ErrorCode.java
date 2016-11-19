package net.floodlightcontroller.recountfee;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
	
	public Integer errorcode;
	public String errordescription;
	public static Map<Integer, String> errors = new HashMap<Integer, String> (){

		private static final long serialVersionUID = 1L;

		{
			put(new Integer(200), "OK");
			put(new Integer(404), "Not found");
			put(new Integer(400), "Bad request");
			put(new Integer(500), "Internal error");
		}
	}; 
	

	public ErrorCode() {
		// TODO Auto-generated constructor stub
	}
	
	public ErrorCode(Integer errorcode, String errordescription) {
		this.errorcode = errorcode;
		this.errordescription = errordescription;
	}
	
	public ErrorCode(Integer code) {
		errordescription = errors.get(code);
		errorcode = code;
	}
	

}
