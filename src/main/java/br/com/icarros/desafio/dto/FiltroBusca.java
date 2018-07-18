package br.com.icarros.desafio.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class FiltroBusca {

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
	@ApiModelProperty(value = "Código do tipo de Filtro a ser aplicado - Mais detalhes use o /getInfo.", required=true)
	@NotBlank(message="Tipo de Busca obrigatorio - Consulte /getInfo")
	private String tipoBusca;
	@ApiModelProperty(value = "Descrição do filtro a ser aplicado.", required=false)
	private String parametro;
	
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
	public String getTipoBusca() {
		return tipoBusca;
	}
	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
	}
	public String getParametro() {
		return parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
}
