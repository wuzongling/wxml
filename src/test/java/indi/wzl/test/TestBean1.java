package indi.wzl.test;

import indi.wzl.annotation.ParentElement;
import indi.wzl.annotation.WxmlElement;
import indi.wzl.annotation.WxmlRootElement;

import java.util.List;

public class TestBean1 {
	
	private String version;
	private String channel;
	private String test;
	private List<TestData> data;
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

}
