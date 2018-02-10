package org.csi.controle.core.entrega;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.csi.controle.core.entidade.TipoEntrega;

public class EntregaFactory {

	private static Map<TipoEntrega, String> entregas = new HashMap<TipoEntrega, String>();
	
	static {
		entregas.put(TipoEntrega.CORREIOS, Correios.class.getName());
//		entregas.put(TipoEntrega.PARTICULAR, Particular.class.getName());
//		entregas.put(TipoEntrega.JADLOG, Jadlog.class.getName());
	}
	
	public static Entrega getInstancia(TipoEntrega etapaEntrega) {
		try {
			Class<?> clazz = Class.forName(entregas.get(etapaEntrega));
			Constructor<?> ctor = clazz.getConstructor(String.class);
			Object object;
			object = ctor.newInstance(new Object[] {});
			return (Entrega) object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
