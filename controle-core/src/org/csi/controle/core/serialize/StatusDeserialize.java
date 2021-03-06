package org.csi.controle.core.serialize;

import java.io.IOException;

import org.csi.controle.core.entidade.Status;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

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
