package indi.wzl.test;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import indi.wzl.Exception.WxmlException;
import indi.wzl.util.ClassUtil;
import indi.wzl.util.WxmlTypeUtil;
import indi.wzl.util.XmlParseUtil;
import indi.wzl.wxml.WxmlFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class wxmlTest {
	
	@Test
	public void testXmlToList() throws DocumentException, WxmlException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/resource/test.xml");
		List<TestData> list = WxmlFactory.parseList(doc, "data", TestData.class);
		System.out.println(JSON.toJSONString(list));
	}
	
	@Test
	public void testXmlToBean() throws WxmlException, DocumentException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/resource/testList.xml");
		//设置启动annotation，默认是不启动
		WxmlFactory.setAnnotation(true);
		TestBean tb = (TestBean) WxmlFactory.parse(TestBean.class, doc);
		System.out.println(JSON.toJSONString(tb));
	}
	
	@Test
	public void testXmlToListMap() throws DocumentException, WxmlException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/resource/test.xml");
		List<Map> list = WxmlFactory.parseList(doc, "data",Map.class);
		System.out.println(JSON.toJSONString(list));
	}
	
	@Test
	public void testXmlToMap() throws DocumentException, WxmlException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/resource/test.xml");
		Map map = (Map) WxmlFactory.parse(Map.class, doc, "data");
		System.out.println(JSON.toJSONString(map));
	}
	
	@Test
	public void testXmlToString() throws WxmlException, DocumentException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/resource/test.xml");
		String str = (String)WxmlFactory.parse(String.class, doc, "version");
		System.out.println(str);
	}
	
	@Test
	public void testWxmlAnnotation() throws FileNotFoundException, DocumentException{
		Document doc=XmlParseUtil.read("K:/workpace/wxml/src/test/java/indi/wzl/test/resource/testList.xml");
		Object obj = null;
		try {
			WxmlFactory.setAnnotation(true);
			TestBean map = (TestBean) WxmlFactory.parse(TestBean.class, doc);
			System.out.println(JSON.toJSONString(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testBeanToXml() throws WxmlException{
		TestBean2 t = new TestBean2();
		t.setChannel("1");
		t.setTest("3");
		t.setVersion("1.0");
		String str = WxmlFactory.toXml(t,"root");
		System.out.println("s:"+str);
	}
	
	@Test
	public void testAnnotationToXml() throws WxmlException{
		TestBean2 t = new TestBean2();
		t.setChannel("1");
		t.setTest("3");
		t.setVersion("1.0");
		WxmlFactory.setAnnotation(true);
		String str = WxmlFactory.toXml(t,"root");
		System.out.println("s:"+str);
	}
	
	@Test
	public void testToXmlMap() throws WxmlException{
		Map map = new HashMap();
		map.put("k1","v1");
		map.put("k2","v2");
		map.put("k3","v3");
		map.put("k4","v4");
		String str = WxmlFactory.toXml(map,"root");
		System.out.println("s:"+str);
	}
	
	@Test
	public void testBeanToXml2() throws WxmlException{
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
		String str = WxmlFactory.toXml(t,"root");
		System.out.println("s:"+str);
	}
}
