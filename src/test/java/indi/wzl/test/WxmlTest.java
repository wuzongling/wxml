package indi.wzl.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.wzl.Exception.WxmlException;
import indi.wzl.util.XmlParseUtil;
import indi.wzl.wxml.WxmlFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class WxmlTest {
	

	@Test
	@Ignore
	public void testXmlToBean() throws WxmlException, DocumentException{
		//设置启动annotation，默认是不启动
//		WxmlFactory.setAnnotation(true);
		TestBean1 tb = (TestBean1) WxmlFactory.parse(TestBean1.class,"/indi/wzl/test/test.xml");
		System.out.println(JSON.toJSONString(tb));
	}

	@Test
	@Ignore
	public void testXmlToBeanAnnotation() throws WxmlException, DocumentException{
		//设置启动annotation，默认是不启动
		WxmlFactory.setAnnotation(true);
		TestBean tb = (TestBean) WxmlFactory.parse(TestBean.class,"/indi/wzl/test/test.xml");
		System.out.println(JSON.toJSONString(tb));
	}

	@Test
	@Ignore
	public void testXmlToList() throws DocumentException, WxmlException{
		List<TestData1> list = WxmlFactory.parseList("/indi/wzl/test/test.xml","data",TestData1.class);
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	@Ignore
	public void testXmlToListAnnotation() throws DocumentException, WxmlException{
		//设置启动annotation，默认是不启动
		WxmlFactory.setAnnotation(true);
		List<TestData> list = WxmlFactory.parseList("/indi/wzl/test/test.xml","data",TestData.class);
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	@Ignore
	public void testXmlToListMap() throws DocumentException, WxmlException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/test.xml");
		List<Map> list = WxmlFactory.parseList(doc, "data",Map.class);
		System.out.println(JSON.toJSONString(list));
	}
	
	@Test
	@Ignore
	public void testXmlToMap() throws DocumentException, WxmlException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/test.xml");
		Map map = (Map) WxmlFactory.parse(Map.class, doc);
		System.out.println(JSON.toJSONString(map));
	}
	
	@Test
	@Ignore
	public void testXmlToString() throws WxmlException, DocumentException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/test.xml");
		String str = (String)WxmlFactory.parse(String.class, doc, "version");
		System.out.println(str);
	}

	@Test
	@Ignore
	public void testBeanToXml() throws WxmlException{
		TestBean2 t = new TestBean2();
		t.setChannel("1");
		t.setTest("3");
		t.setVersion("1.0");
		String str = WxmlFactory.toXml(t,"root",true);
		System.out.println(str);
	}

	@Test
	@Ignore
	public void testBeanToXmlAnnotaion() throws WxmlException{
		TestBean2 testBean2 = new TestBean2();
		testBean2.setChannel("1");
		testBean2.setTest("3");
		testBean2.setVersion("1.0");
		TestData testData = new TestData();
		testData.setId(11);
		testData.setOappid("222");
		TestData testData2 = new TestData();
		testData2.setId(11);
		testData2.setOappid("222");
		List<TestData> list = new ArrayList<>();
		list.add(testData);
		list.add(testData2);
		testBean2.setData(list);
		WxmlFactory.setAnnotation(true);
		String str = WxmlFactory.toXml(testBean2,"root",true);
		System.out.println(str);
	}

	@Test
	@Ignore
	public void testBeanToXmlAnnotation2() throws WxmlException{
		TestBean2 t = new TestBean2();
		t.setChannel("1");
		t.setTest("3");
		t.setVersion("1.0");

		List<TestData> list = new ArrayList<TestData>();
		TestData data = new TestData();
		data.setBigmaplink("1");
		data.setCategory("2");
		data.setCategoryname("s");
		TestData data2 = new TestData();
		data2.setBigmaplink("1");
		data2.setCategory("2");
		data2.setCategoryname("s");
		list.add(data);
		list.add(data2);
		t.setData(list);

		Map map = new HashMap();
		map.put("k1","v1");
		map.put("k2","我的");
		map.put("k3","v3");
		map.put("k4","v4");
		t.setMap(map);
		//设置启动annotation
		WxmlFactory.setAnnotation(true);
		String str = WxmlFactory.toXml(t,"root",true);
		System.out.println("s:"+str);
	}

	@Test
	@Ignore
	public void testToXmlMap() throws WxmlException{
		Map map = new HashMap();
		map.put("k1","v1");
		map.put("k2","v2");
		map.put("k3","v3");
		map.put("k4","v4");
		String str = WxmlFactory.toXml(map,"root",true);
		System.out.println(str);
	}
	
}
