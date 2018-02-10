//package org.csi.controle.core.serialize;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.JsonSerializer;
//import org.codehaus.jackson.map.SerializerProvider;
//
//public class DELETARDataHoraSerialize extends JsonSerializer<Date> {
//
//	@Override
//	public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
//		if(arg0 != null) {			
//			arg1.writeString(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(arg0));		
//		}
//	}
//
//}
