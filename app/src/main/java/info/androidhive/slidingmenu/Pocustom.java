package info.androidhive.slidingmenu;
public class Pocustom {
	String code = null;
	 String name = null;
	 String continent = null;
	 String region = null;
	 String net = null;
		 
	 
	 public Pocustom(String code, String name, String continent, String region,String net) {
	  super();
	  this.code = code;
	  this.name = name;
	  this.continent = continent;
	  this.region = region;	 
	  this.net = net;	
	  
	 }
	  
	 public String getCode() {
	  return code;
	 }
	 public void setCode(String code) {
	  this.code = code;
	 }
	 public String getName() {
	  return name;
	 }
	 public void setName(String name) {
	  this.name = name;
	 }
	 public String getContinent() {
	  return continent;
	 }
	 public void setContinent(String continent) {
	  this.continent = continent;
	 }
	 public String getRegion() {
	  return region;
	 }
	 public void setRegion(String region) {
	  this.region = region;
	 }
	
		
	 
	 public String getnet() {
		  return net;
		 }
		 public void setnet(String net) {
		  this.net = net;
		 }
	
	 @Override
	 public String toString() {
	  return  code + " " + name + " "
	    + continent + " " + region;
	 }
	  
}
