package org.csi.controle.core.serialize;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

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
