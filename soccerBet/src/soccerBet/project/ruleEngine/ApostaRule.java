package soccerBet.project.ruleEngine;

public class ApostaRule {

	private String tipoAposta;
	private int estado;
	
	public ApostaRule(){
		
	}

	public void setTipoAposta(String tipoAposta) {
		this.tipoAposta = tipoAposta;
	}

	public String getTipoAposta() {
		return tipoAposta;
	}

	public void setEstado(int estado) {
		// TODO Auto-generated method stub
		this.estado=estado;
		
	}

	public int getEstado() {
		return estado;
	}
}
