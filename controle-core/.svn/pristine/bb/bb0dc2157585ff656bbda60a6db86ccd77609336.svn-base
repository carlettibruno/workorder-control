package org.csi.controle.core.serialize;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class MoedaSerialize extends JsonSerializer<Double> {

	@Override
	public void serialize(Double arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		if(arg0 == null) {
			arg1.writeString("");
		}
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		formatter.setMinimumFractionDigits(2);
		String valor3 = formatter.format(arg0);
		valor3 = valor3.replaceAll("R\\$ ", "");
		arg1.writeString(valor3);
	}

}