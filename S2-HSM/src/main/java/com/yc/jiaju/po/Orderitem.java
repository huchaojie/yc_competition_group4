package com.yc.jiaju.po;

public class Orderitem {
	private Integer id;
	private Integer count;
	private Double total;
	private Integer pid;
	private Integer oid;
	private Integer estatu;
	
	private Product product;//订单明细记录的商品信息对象
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Integer getEstatu() {
		return estatu;
	}
	public void setEstatu(Integer estatu) {
		this.estatu = estatu;
	}
	
	
}
