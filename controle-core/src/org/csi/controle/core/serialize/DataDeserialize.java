//package org.csi.controle.core.serialize;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.DeserializationContext;
//import org.codehaus.jackson.map.JsonDeserializer;
//
//public class DataDeserialize extends JsonDeserializer<Date> {
//
//	@Override
//	public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
//		String jsonValue = arg0.getText();
//		if(jsonValue == null) {
//			return  null;
//		}
//		try {
//			return new SimpleDateFormat("dd/MM/yyyy").parse(jsonValue);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//}
