package org.csi.controle.servico;

import javax.ejb.Local;

import org.csi.controle.core.entidade.Sessao;

@Local
public interface SessionService {

	Sessao findSession(String token);

}