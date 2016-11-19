package net.floodlightcontroller.recountfee;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = FeeResponseSeri.class)
public class FeeResponse<P> {
	
	public ErrorCode error;
	public P payload;
	
	public FeeResponse() {
		// TODO Auto-generated constructor stub
	}

}

class FeeResponseSeri extends JsonSerializer<FeeResponse<?>> {

	@Override
	public void serialize(FeeResponse<?> r, JsonGenerator jGen,
			SerializerProvider sp) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		jGen.writeStartObject();
		jGen.writeObjectField("error", r.error);
		if (r.error.errorcode == 200) {
			jGen.writeObjectField("payload", r.payload);
		}
		jGen.writeEndObject();

	}
	
}
