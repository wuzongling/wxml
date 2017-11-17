package indi.wzl.test;

import indi.wzl.annotation.ParentElement;
import indi.wzl.annotation.WxmlAttribute;
import indi.wzl.annotation.WxmlElement;
import indi.wzl.annotation.WxmlRootElement;

import java.util.List;
import java.util.Map;
@WxmlRootElement
public class TestBean2 {
	@ParentElement("varsion")
	@WxmlAttribute("id")
	private String version;
	private String channel;
	private String test;
	@ParentElement("list")
	@WxmlElement("data")
	private List<TestData> data;
	@WxmlAttribute
	private Map map;
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
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	
}
