package br.com.icarros.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {
	
	@JsonProperty("number")
	private String number;
	
	@JsonProperty("position")
	private String position;
	
	@JsonProperty("positionText")
	private String positionText;
	
	@JsonProperty("points")
	private String points;
	
	@JsonProperty("Driver")
	private Driver driver;
	
	@JsonProperty("Constructor")
	private Constructor constructor;
	
	@JsonProperty("grid")
	private String grid;
	
	@JsonProperty("laps")
	private String laps;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("Time")
	private Time time;

	@JsonProperty("FastestLap")
	private FastestLap fastestLap;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPositionText() {
		return positionText;
	}
	public void setPositionText(String positionText) {
		this.positionText = positionText;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public Constructor getConstructor() {
		return constructor;
	}
	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public String getLaps() {
		return laps;
	}
	public void setLaps(String laps) {
		this.laps = laps;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public FastestLap getFastestLap() {
		return fastestLap;
	}
	public void setFastestLap(FastestLap fastestLap) {
		this.fastestLap = fastestLap;
	}
	
}
