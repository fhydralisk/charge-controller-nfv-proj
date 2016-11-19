package net.floodlightcontroller.recountfee;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.projectfloodlight.openflow.types.DatapathId;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class RecountFeeDevices extends RecountFeeRestletRequestBase {

	@Get("json")
	public FeeResponse<DevicesMap> GetDevices() {
		FeeResponse<DevicesMap> dp = new FeeResponse<DevicesMap>();
		dp.error = new ErrorCode(200);
		dp.payload = context.feeswitches;
		return dp;
	}
	
	@Post("json")
	public FeeResponse<Integer> putDeviceType (String dsts) {
		FeeResponse<Integer> fr = new FeeResponse<Integer>();
		Map<DatapathId, String> dst;
		try {
			dst = jsonToDevicesType(dsts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fr.error = new ErrorCode(400);
			return fr;
		}
		
		int c = context.setDevicesType(dst);
		fr.error = new ErrorCode(c == 0 ? 404 : 200);
		fr.payload = Integer.valueOf(c);
		
		return fr;
	}
	
	public static Map<DatapathId, String> jsonToDevicesType(String dstJson) throws IOException {
		Map<DatapathId, String> dst = new HashMap<DatapathId, String>();
		
		MappingJsonFactory f = new MappingJsonFactory();
		JsonParser jp;
		
		try {
			jp = f.createParser(dstJson);
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		jp.nextToken();
		
		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
	        throw new IOException("Expected START_OBJECT");
	    }
		
		while (jp.nextToken() != JsonToken.END_OBJECT) {
	        if (jp.getCurrentToken() != JsonToken.FIELD_NAME) {
	            throw new IOException("Expected FIELD_NAME");
	        }
	        String dpids = jp.getCurrentName();
	        
	        jp.nextToken();
	        
	        if (jp.getCurrentToken() != JsonToken.VALUE_STRING) {
	        	 throw new IOException("Expected VALUE_STRING");
	        }
	        
	        String type = jp.getText();
	        DatapathId dpid;
	        try {
	        	 dpid = DatapathId.of(dpids);
	        } catch (NumberFormatException e) {
	        	throw new IOException(e);
	        }
	        
	        dst.put(dpid, type);
	        
		}
		
		return dst;
		
	}
}
