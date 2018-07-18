package br.com.icarros.desafio.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class FiltroCarregaBase {
	
	@ApiModelProperty(value = "Ano da Temporada Inicial da Busca.", required=true)
	@NotBlank(message="Temporada Inicial obrigatoria")
	private String temporadaInicial;
	@ApiModelProperty(value = "Ano da Temporada Final da Busca.", required=false)
	private String temporadaFinal;
	@ApiModelProperty(value = "Número da Rodada Inicial da Busca.", required=true)
	@NotBlank(message="Rodada Inicial obrigatoria")
	private String rodadaInicial;
	@ApiModelProperty(value = "Número da Rodada Final da Busca.", required=false)
	private String rodadaFinal;
	
	public String getTemporadaInicial() {
		return temporadaInicial;
	}
	public void setTemporadaInicial(String temporadaInicial) {
		this.temporadaInicial = temporadaInicial;
	}
	public String getTemporadaFinal() {
		return temporadaFinal;
	}
	public void setTemporadaFinal(String temporadaFinal) {
		this.temporadaFinal = temporadaFinal;
	}
	public String getRodadaInicial() {
		return rodadaInicial;
	}
	public void setRodadaInicial(String rodadaInicial) {
		this.rodadaInicial = rodadaInicial;
	}
	public String getRodadaFinal() {
		return rodadaFinal;
	}
	public void setRodadaFinal(String rodadaFinal) {
		this.rodadaFinal = rodadaFinal;
	}
}
