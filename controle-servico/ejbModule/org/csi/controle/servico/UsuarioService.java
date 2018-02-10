package org.csi.controle.servico;

import javax.ejb.Local;

import org.csi.controle.core.entidade.Usuario;
import org.csi.controle.core.util.RetornoServico;

@Local
public interface UsuarioService {

	RetornoServico<String> manterSenhaUsuario(Usuario usuario, Boolean enviarEmail);
	
}