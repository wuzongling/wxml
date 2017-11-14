package indi.wzl.test;

import indi.wzl.annotation.WxmlAttribute;
import indi.wzl.annotation.WxmlElement;
import indi.wzl.annotation.WxmlRootElement;

@WxmlRootElement
public class TestData {
	@WxmlAttribute("id")
	private int id;
	@WxmlElement
	private String oappid;
	@WxmlElement
	private String category;
	@WxmlElement
	private String categoryname;
	private String subcate;
	private String bigmaplink;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOappid() {
		return oappid;
	}
	public void setOappid(String oappid) {
		this.oappid = oappid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getSubcate() {
		return subcate;
	}
	public void setSubcate(String subcate) {
		this.subcate = subcate;
	}
	public String getBigmaplink() {
		return bigmaplink;
	}
	public void setBigmaplink(String bigmaplink) {
		this.bigmaplink = bigmaplink;
	}
	
}
