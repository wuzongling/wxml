package indi.wzl.test;

import indi.wzl.annotation.ParentElement;
import indi.wzl.annotation.WxmlAttribute;
import indi.wzl.annotation.WxmlElement;
import indi.wzl.annotation.WxmlRootElement;

import java.util.List;

@WxmlRootElement
public class TestBean {
	
	@WxmlElement
	private String version;
	private String channel;
	@WxmlElement
	private String test;
	@WxmlElement
	private List<TestData> data;
	@WxmlElement("data")
	private List<TestData> aa;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public List<TestData> getData() {
		return data;
	}
	public void setData(List<TestData> data) {
		this.data = data;
	}

	public List<TestData> getAa() {
		return aa;
	}

	public void setAa(List<TestData> aa) {
		this.aa = aa;
	}
}
