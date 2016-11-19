package net.floodlightcontroller.recountfee;

import java.io.IOException;
import java.util.HashMap;


import org.projectfloodlight.openflow.types.IPv4Address;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(using=SubscribersMapSeri.class)
public class SubscribersMap extends HashMap<IPv4Address, Subscriber> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

class SubscribersMapSeri extends JsonSerializer<SubscribersMap>{

	@Override
	public void serialize(SubscribersMap t, JsonGenerator jGen, SerializerProvider serializer)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		jGen.writeStartArray();
		for (Subscriber ss : t.values()) {
			jGen.writeStartObject();
			jGen.writeStringField("ip", ss.address.toString());
			jGen.writeNumberField("id", ss.id);
			jGen.writeEndObject();
		}
		//jGen.writeString(t.toString());
		jGen.writeEndArray();
	}
	
}
