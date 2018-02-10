package org.csi.controle.core.serialize;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.csi.controle.core.entidade.Status;

public class StatusSerialize extends JsonSerializer<Status> {

	@Override
	public void serialize(Status arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		if(arg0 != null) {			
			arg1.writeString(arg0.getLabel());
		}
	}

}