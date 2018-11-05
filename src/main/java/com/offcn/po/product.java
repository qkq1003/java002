package com.offcn.po;

public class product {

	
	private String name;
	
	private String pic;
	private double price;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "product [name=" + name + ", pic=" + pic + ", price=" + price + ", url=" + url + "]";
	}
	
	
}
