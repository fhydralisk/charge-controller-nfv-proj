package net.floodlightcontroller.recountfee;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = FeeSeri.class)
public interface IFeeType {
	public void recount(FeeUnit ufee);
	public String toString();
	public long getFee();
	public String description();
	public Map<String, String>  mapObj();
	public FeeMatchFields getMatchFields();
}


class FeeSeri extends JsonSerializer<IFeeType> {

	@Override
	public void serialize(IFeeType f, JsonGenerator jGen,
			SerializerProvider s) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		
		jGen.writeStartObject();
		jGen.writeStringField("type", f.getClass().getName());
		jGen.writeStringField("description", f.description());
		jGen.writeObjectField("account", f.mapObj());
		jGen.writeEndObject();
	}
}
