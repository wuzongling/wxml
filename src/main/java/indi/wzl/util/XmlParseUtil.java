package indi.wzl.util;

import indi.wzl.Exception.WxmlException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * xml解析工具类
 * @author wuzonglin
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2015年9月23日 下午5:17:00
 */
public class XmlParseUtil {
	/**
	 * 读取文件
	 * @param fileUrl (绝对路径)
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * @throws FileNotFoundException 
	 */
	 public static Document read(String fileUrl) throws DocumentException, FileNotFoundException{
		 SAXReader reader = new SAXReader();
		// Document document = reader.read(XmlParseUtil.class.getResourceAsStream(fileUrl));
		 Document document = reader.read(new FileInputStream(fileUrl));
		 return document;
	       
	  }
	 
	 /**
	  * 读取文件
	  * @param fileUrl (classPath下的相对路径，带有"/")
	  * @return
	  * @throws DocumentException
	  */
	 public static Document readClassPath(String fileUrl) throws DocumentException{
		 SAXReader reader = new SAXReader();
		 //将\\替换成/
		 fileUrl = fileUrl.replace("\\\\", "/");
		 //将\替换成/
		 fileUrl = fileUrl.replace("\\", "/");
		 if(fileUrl.startsWith("/")){
			 fileUrl = fileUrl.substring(1);
		 }
		 Document document = reader.read(XmlParseUtil.class.getClassLoader().getResourceAsStream(fileUrl));
		 return document;
	       
	    }
	 
	 /**
	  * 读取字符串的xml
	  * @param xmlStr
	  * @return
	 * @throws DocumentException 
	  */
	 public static Document ParseXmlStr(String xmlStr) throws DocumentException{
		 Document document = null;
		 document = DocumentHelper.parseText(xmlStr);
		 return document;
	 }
	 
	 /**
	  * 获取直接子元素元素集合
	  * @param doc
	  * @param elment
	  * @param elementName
	  * @return
	  */
	 public static List<Element> getChildElements(Document doc,Element elment,String elementName){
		 if(elment == null){
			 elment = doc.getRootElement();//获取根节点
		 }
		 List<Element> list = null;
		 if(elementName == null || elementName == ""){
			 list = elment.elements();
		 }else {
			 list = elment.elements(elementName);
		}
		 return list;
	 }
	 
	 /**
	  * 获取直接子元素元素集合
	  * @param doc 
	  * @param elment 从elment下开始检索，如果为null则从根元素开始检索
	  * @param elementName 子元素名
	  * @param attributes 需要匹配的元素属性 
	  * @return
	  */
	 public static List<Element> getChildElements(Document doc,Element elment,String elementName,Map<String,String> attributes){
		 if(elment == null){
			 elment = doc.getRootElement();//获取根节点
		 }
		 Iterator<Element> iterator = null;
		 if(elementName == null || elementName == ""){
			  iterator = elment.elementIterator();
		 }else {
			  iterator = elment.elementIterator(elementName);
		}
		 return filterAttrCondition(iterator,attributes);
	 }
	 
	 /**
	  * 获取直接子元素元素集合
	  * @param doc
	  * @param element 如果为null，默认从根节点下开始检索
	  * @param elementName 要搜索的元素名
	  * @param parentName 要搜索元素的父元素名
	  * @param parentAttributes 父元素属性条件
	  * @return
	  * @desc 从元素element的直接子元素开始检索，如果直接子元素中搜索到则返回集合，否则在搜索所有子元素的下一级，以此类推。
	  */
	 public static List<Element> getChildElements(Document doc,Element elment,String parentName,String elementName,Map<String,String> parentAttributes){
		 if(elment == null){
			 elment = doc.getRootElement();//获取根节点
		 }
		 Iterator<Element> iterator = null;
		 if(elementName == null || elementName == ""){
			  iterator = elment.elementIterator();
		 }else {
			  iterator = elment.elementIterator(elementName);
		}
		 return filterAttrCondition(iterator, parentName, parentAttributes);
	 }
	 
	/**
	 * 从元素element下开始搜索，返回子元素名为elementName的list<Element>集合
	 * @param doc
	 * @param element 如果为null，默认从根节点下开始检索
	 * @param elementName
	 * @return
	 * @desc 从元素element的直接子元素开始检索，如果直接子元素中搜索到则返回集合，否则在搜索所有子元素的下一级，以此类推。
	 */
	public static List<Element> getElements(Document doc,Element element,String elementName){
		  if(element==null){
			  element=doc.getRootElement();//获取根节点
		  }
		 return ElementIterator(element,elementName,new ArrayList<Element>());
	}
	
	/**
	 * 从元素element下开始搜索，返回子元素名为elementName的list<Element>集合
	 * @param doc
	 * @param element 如果为null，默认从根节点下开始检索
	 * @param elementName
	 * @param attributes 元素属性条件
	 * @return
	 * @desc 从元素element的直接子元素开始检索，如果直接子元素中搜索到则返回集合，否则在搜索所有子元素的下一级，以此类推。
	 */
	public static List<Element> getElements(Document doc,Element element,String elementName,Map<String,String> attributes){
		  if(element==null){
			  element=doc.getRootElement();//获取根节点
		  }
		  List<Element> list = new ArrayList<Element>();
		  list = ElementIterator(element,elementName,list);
		  if(attributes != null){
			  return filterAttrCondition(list.iterator(),attributes);
		  }else{
			  return list;
		  }
		 
	}
	
	/**
	 * 从元素element下开始搜索，返回子元素名为elementName的list<Element>集合
	 * @param doc
	 * @param element 如果为null，默认从根节点下开始检索
	 * @param elementName 要搜索的元素名
	 * @param parentName 要搜索元素的父元素名
	 * @param parentAttributes 父元素属性条件
	 * @return
	 * @desc 从元素element的直接子元素开始检索，如果直接子元素中搜索到则返回集合，否则在搜索所有子元素的下一级，以此类推。
	 */
	public static List<Element> getElements(Document doc,Element element,String elementName,String parentName,Map<String,String> parentAttributes){
		  if(element==null){
			  element=doc.getRootElement();//获取根节点
		  }
		  List<Element> list = new ArrayList<Element>();
		  list = ElementIterator(element,elementName,list);
		  if(parentName != null){
			  return filterAttrCondition(list.iterator(),parentName,parentAttributes);
		  }else{
			  return list;
		  }
		 
	}
	
	/**
	  * 获取元素下面某个子元素节点的属性集合 ， 子元素存在多个返回list集合,存在一个返回字符串，不存在返回Null
	  * @param doc
	  * @param elment 如果为Null 则默认为根节点下
	  * @param elementName  
	  * @param attrName
	  * @return
	  */
	 public static Object getElementAttributes(Document doc,Element elment,String elementName,String attrName){
		 if(elment == null){
			 elment = doc.getRootElement();//获取根节点
		 }
		 Object obj= ElementAttributesIterator(elment,elementName,attrName);
		 return obj;
		 
	 }
	
	/**
	 * 根据元素的属性条件过滤元素
	 * @param iterator
	 * @param attributes
	 * @return
	 */
	public static List<Element> filterAttrCondition(Iterator<Element> iterator,Map<String,String> attributes){
		List<Element> listEle = new ArrayList<Element>();
		 while(iterator.hasNext()){
			 Element e = iterator.next();
			 if(attributes != null){
				 String attr1 = "";
				 String attr2 = "";
				 for (Map.Entry<String, String> entry : attributes.entrySet()) {
					attr1 += entry.getValue();
					attr2 += e.attributeValue(entry.getKey());
				}
				 if(attr1.equals(attr2)){
					 listEle.add(e);
				 }
			 }else{
				 listEle.add(e);
			 }
		 }
		 return listEle;
	}
	
	/**
	 * 根据元素的属性条件过滤元素
	 * @param iterator
	 * @param parentName 父级元素名
	 * @param parentAttributes  父级元素属性
	 * @return
	 */
	public static List<Element> filterAttrCondition(Iterator<Element> iterator,String parentName,Map<String,String> parentAttributes){
		List<Element> listEle = new ArrayList<Element>();
		 while(iterator.hasNext()){
			 Element e = iterator.next();
			 Element parent = e.getParent();
			 if(parentAttributes != null){
				 String attr1 = "";
				 String attr2 = "";
				 for (Map.Entry<String, String> entry : parentAttributes.entrySet()) {
					attr1 += entry.getValue();
					attr2 += parent.attributeValue(entry.getKey());
				}
				 if(attr1.equals(attr2)){
					 listEle.add(e);
				 }
			 }else{
				 listEle.add(e);
			 }
		 }
		 return listEle;
	}
	
	/**
	 * 检验一段xml字符串是否合法
	 * @param xmlstr
	 * @return
	 */
	public static Boolean checkoutXml(String xmlstr){
		boolean flag=false;
		try {
			DocumentHelper.parseText(xmlstr);
			flag=true;
			return flag;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			flag=false;
			return flag;
		}
		
	}
	
	/**
	  * 获取元素下面某个子元素属性集合  子元素存在多个返回list集合,存在一个返回字符串，不存在返回Null
	  * @param element
	  * @param chilElementName
	  * @param attrName
	  * @return
	  */
	private static Object ElementAttributesIterator(Element element,
			String chilElementName, String attrName) {
		List<Element> elements = element.elements(chilElementName);
		List<String> attrs = new ArrayList<String>();
		if (elements.size() > 0) {
			for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext();) {
				Element chilele = iterator.next();
				String attr = chilele.attributeValue(attrName);
				attrs.add(attr);

			}
			if (attrs.size() == 1) {
				String attr = attrs.get(0);
				return attr;
			}
			return attrs;
		}
		return null;
	}
	
	/**
	 * 从元素element下开始搜索，返回元素名为elementName的list<Element>集合
	 * @param element
	 * @param elementName
	 * @return
	 * @desc 从元素element的直接子元素开始检索，如果直接子元素中搜索到则返回集合，否则在搜索所有子元素的下一级，以此类推。
	 */
	private static List<Element> ElementIterator(Element element,String elementName,List<Element> arrayList){
		List<Element> elements = element.elements(elementName);
		if(elements.size()>0){
			//如果在直接子元素中检索到
			for(int i=0;i<elements.size();i++){
				arrayList.add(elements.get(i));
			}
			
		}else{
			//List<Element> chillist=new ArrayList<Element>();
			for (Iterator iterator = element.elementIterator(); iterator.hasNext();){
				Element e=(Element)iterator.next();
				ElementIterator(e,elementName,arrayList);
			}
		}
		return arrayList;
		
	}
	
	
	/*public static void main(String[] args) {
		 try {
			Document doc=XmlParseUtil.read("/test/test.xml");
			Map map = new HashMap();
			map.put("id", "3");
			List<Element> list = getElements(doc,null,"mxGeometry","asg",map);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				System.out.println("x:"+element.attributeValue("x"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
