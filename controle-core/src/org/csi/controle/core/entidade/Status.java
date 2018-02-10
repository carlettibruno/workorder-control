package org.csi.controle.core.entidade;

public enum Status {
	CONCLUIDO("Concluído"), ABERTO("Aberto"), EM_ANDAMENTO("Em andamento"), PENDENTE("Pendente");
	
	private String label;
	Status(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
}
