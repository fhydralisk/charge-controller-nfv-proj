package net.floodlightcontroller.recountfee;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.projectfloodlight.openflow.types.DatapathId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = DevicesMapSeri.class)
public class DevicesMap extends HashMap<DatapathId, FeeDevice> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}

class DevicesMapSeri extends JsonSerializer<DevicesMap>{

	@Override
	public void serialize(DevicesMap t, JsonGenerator jGen, SerializerProvider serializer)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		jGen.writeStartArray();
		for (Map.Entry<DatapathId, FeeDevice> e: t.entrySet()) {
			jGen.writeStartObject();
			jGen.writeStringField("dpid",e.getKey().toString());
			jGen.writeObjectField("properties", e.getValue());
			jGen.writeEndObject();
		}
		//jGen.writeString(t.toString());
		jGen.writeEndArray();
	}
	
}
