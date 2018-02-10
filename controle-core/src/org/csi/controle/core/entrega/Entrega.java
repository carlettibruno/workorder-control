package org.csi.controle.core.entrega;

import java.util.List;

import org.csi.rastreamento.correios.entidade.Evento;

public abstract class Entrega {

	protected List<Evento> eventos;
	
	public abstract List<Evento> getEventos();

}