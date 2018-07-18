package br.com.icarros.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FastestLap {

	@JsonProperty("rank")
	private String rank;
	
	@JsonProperty("lap")
	private String lap;
	
	@JsonProperty("Time")
	private Time time;
	
	@JsonProperty("AverageSpeed")
	private AverageSpeed averageSpeed;
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getLap() {
		return lap;
	}
	public void setLap(String lap) {
		this.lap = lap;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public AverageSpeed getAverageSpeed() {
		return averageSpeed;
	}
	public void setAverageSpeed(AverageSpeed averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
	
}
