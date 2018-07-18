package br.com.icarros.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MrData {
	
	@JsonProperty("xmlns")
	private String xmlns;
	
	@JsonProperty("series")
	private String series;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("limit")
	private String limit;
	
	@JsonProperty("offset")
	private String offset;
	
	@JsonProperty("total")
	private String total;
	
	@JsonProperty("RaceTable")
	private RaceTable raceTable;
	
	public String getXmlns() {
		return xmlns;
	}
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public RaceTable getRaceTable() {
		return raceTable;
	}
	public void setRaceTable(RaceTable raceTable) {
		this.raceTable = raceTable;
	}
	
	
}
