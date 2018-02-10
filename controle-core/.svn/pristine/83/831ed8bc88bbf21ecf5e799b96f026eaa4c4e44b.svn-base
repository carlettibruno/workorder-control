package org.csi.controle.core.serialize;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class MoedaDeserialize extends JsonDeserializer<Double> {

	@Override
	public Double deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		String jsonValue = arg0.getText();
		if(jsonValue == null) {
			return  null;
		}
		jsonValue = jsonValue.replaceAll("R\\$ ", "");
		NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("pt","BR"));
		formatter.setMinimumFractionDigits(4);
		try {
			Double valor = formatter.parse(jsonValue).doubleValue();
			return valor;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
