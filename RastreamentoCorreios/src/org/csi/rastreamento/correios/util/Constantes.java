package org.csi.rastreamento.correios.util;

public class Constantes {
	
	public static final String XML_DHL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " + 
			"		<req:KnownTrackingRequest xmlns:req=\"http://www.dhl.com\"  " + 
			"								xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  " + 
			"								xsi:schemaLocation=\"http://www.dhl.com " + 
			"								TrackingRequestKnown.xsd\"> " + 
			"			<Request> " + 
			"				<ServiceHeader> " + 
			"					<MessageTime>2002-06-25T11:28:56-08:00</MessageTime> " + 
			"					<MessageReference>1234567890123456789012345678</MessageReference> " + 
			"		            <SiteID>{SITEID}</SiteID> " + 
			"		            <Password>{PASSWORD}</Password> " + 
			"				</ServiceHeader> " + 
			"			</Request> " + 
			"			<LanguageCode>pt</LanguageCode> " + 
			"			<AWBNumber>{AWBNUMBER}</AWBNumber> " + 
			"			<LevelOfDetails>ALL_CHECK_POINTS</LevelOfDetails> " + 
			"			<PiecesEnabled>S</PiecesEnabled>  " + 
			"		</req:KnownTrackingRequest>";

}
