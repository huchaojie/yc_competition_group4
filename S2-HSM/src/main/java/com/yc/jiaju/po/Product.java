package com.yc.jiaju.po;

import java.sql.Timestamp;

public class Product {

	private Integer pid;
	
	private String pname;
	
	private Double price;
	
	private Integer sid;
	
	private String pdesc;
	
	private String image;
	
	private String style;
	
	private Timestamp createtime;
	
	private Integer hot;
	
	private Integer stock;
	
	private Integer eva;

	public Integer getEva() {
		return eva;
	}

	public void setEva(Integer eva) {
		this.eva = eva;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", price=" + price + ", sid=" + sid + ", pdesc=" + pdesc
				+ ", image=" + image + ", style=" + style + ", createtime=" + createtime + ", hot=" + hot + ", stock="
				+ stock + ", eva=" + eva + "]";
	}
	
	
}
