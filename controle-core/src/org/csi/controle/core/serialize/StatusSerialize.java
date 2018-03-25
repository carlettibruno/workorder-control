package org.csi.controle.core.serialize;

import java.io.IOException;

import org.csi.controle.core.entidade.Status;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class StatusSerialize extends JsonSerializer<Status> {

	@Override
	public void serialize(Status arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		if(arg0 != null) {			
			arg1.writeString(arg0.getLabel());
		}
	}

}