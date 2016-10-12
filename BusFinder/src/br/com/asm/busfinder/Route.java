package br.com.asm.busfinder;

public class Route {

	private String longName;
	private int routeId;
	private String shortName;
	
	public String getLongName() {return longName;}
	public void setLongName(String longName) {this.longName = longName;}
	
	public int getRouteId() {return routeId;}
	public void setRouteId(int routeId) {this.routeId = routeId;}
	
	public String getShortName() {	return shortName;}
	public void setShortName(String shortName) {this.shortName = shortName;}
	
	public String toString()
	{
		return this.shortName + " - " + this.longName;
	}
}
