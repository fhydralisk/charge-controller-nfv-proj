package net.floodlightcontroller.recountfee;

import java.io.IOException;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;


public class RecountFeeSubscribers extends RecountFeeRestletRequestBase {

	@Get("json")
	public FeeResponse<SubscribersMap> getSubscribers() {
		FeeResponse<SubscribersMap> sp = new FeeResponse<SubscribersMap>();
		sp.error = new ErrorCode(200);
		sp.payload = context.subscribers;
		return sp;
	}
	
	@Put("json")
	public FeeResponse<Integer> putSubscribers(String sssJson) {
		
		FeeResponse<Integer> fr = new FeeResponse<Integer>();
		SubscribersMap sssm;
		try {
			sssm = jsonToSubscribersMap(sssJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fr.error = new ErrorCode(400);
			return fr;
		}
		
		fr.error = new ErrorCode(200);
		fr.payload = Integer.valueOf(sssm.size());
		context.addSubscribers(sssm);
		
		return fr;
		
	}
	
	@Delete("json")
	public FeeResponse<Integer> deleteSubscribers(String sssJson) {
		
		FeeResponse<Integer> fr = new FeeResponse<Integer>();
		SubscribersMap sssm;
		try {
			sssm = jsonToSubscribersMap(sssJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fr.error = new ErrorCode(400);
			return fr;
		}
		
		fr.error = new ErrorCode(200);
		fr.payload = Integer.valueOf(context.deleteSubscribers(sssm));
		
		return fr;
		
	}

	
	public static SubscribersMap jsonToSubscribersMap(String sssJson) throws IOException {
		SubscribersMap sssm = new SubscribersMap();
		
		MappingJsonFactory f = new MappingJsonFactory();
		JsonParser jp;
		
		try {
			jp = f.createParser(sssJson);
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
	        String ip = jp.getCurrentName();
	        
	        jp.nextToken();
	        
	        if (jp.getCurrentToken() != JsonToken.VALUE_NUMBER_INT) {
	        	 throw new IOException("Expected NUMBER_INT");
	        }
	        
	        long id = jp.getNumberValue().longValue();
	        IPv4Address addr;
	        try {
	        	 addr = IPv4Address.of(ip);
	        } catch (IllegalArgumentException e) {
	        	throw new IOException(e);
	        }
	        
	        sssm.put(addr, new Subscriber(addr, id));
	        
		}
		
		return sssm;
		
	}
}


