package org.csi.controle.core.serialize;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.csi.controle.core.entidade.Status;

public class StatusDeserialize extends JsonDeserializer<Status> {

	@Override
	public Status deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		String jsonValue = arg0.getText();
		if(jsonValue == null) {
			return null;
		}
		for (Status enumValue : Status.values()) {
			if (enumValue.getLabel().equals(jsonValue)) {
				return enumValue;
			}
		}
		return null;
	}

}
