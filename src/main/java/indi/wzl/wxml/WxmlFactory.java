package indi.wzl.wxml;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

import indi.wzl.Exception.WxmlException;
import indi.wzl.util.StringUtil;
import indi.wzl.util.XmlParseUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * 
* @ClassName: WxmlFactory 
* @Description: Wxml解析工厂类,可以自动将xml和java类相互转换,默认的规则是：java bean中的成员属性对应着xml中的子元素标签。如果需要开启annotation注解
* 则需要WxmlFactory.setAnnotation(true)这样的代码，开启注解后类名上要加上@WxmlRootElement注解，否则会报错。对于没有加上属性或者元素注解的成员属性不会被解析。
* @author wuzonglin
* @date 2015年10月20日 下午5:22:19 
* 
 */
public class WxmlFactory {
	/**
	 * annotation标识，目前所有的annotation都要放在0的位置（也就是最上面）
	 */
	private static Boolean isAnnotation = false;

	public static void setAnnotation(Boolean isAnnotation) {
		WxmlFactory.isAnnotation = isAnnotation;
	}

	/**
	 * 将xml的字符串解析成Class的对象
	 * @param cla
	 * @param xmlStr
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 */
	public static Object parseXmlStr(Class cla,String xmlStr) throws WxmlException{
		try {
			Document doc = null;
			doc = XmlParseUtil.ParseXmlStr(xmlStr);
			return parse(cla,doc);
		} catch (Exception e) {
			throw new WxmlException("解析xml字符串失败",e);
		}
	}
	
	/**
	 * 将xml的字符串解析成Class的对象
	 * @param cla
	 * @param xmlStr
	 * @param eleName
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 * @desc 将根元素下名为eleName的子元素解析成Class 的实例
	 */
	public static Object parseXmlStr(Class cla,String xmlStr,String eleName) throws WxmlException{
		try {
			Document doc = null;
			doc = XmlParseUtil.ParseXmlStr(xmlStr);
			return parse(cla,doc,eleName);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("解析xml字符串失败",e);
		}
	}
	
	/**
	 * 生成xml字符串
	 * @param obj
	 * @return
	 * @throws WxmlException
	 */
	public static String toXml(Object obj) throws WxmlException{
		if(isAnnotation){
			return AnnotationXmlConvertFactory.xmlConvert(obj, null);
		}
		return XmlConvertFactory.xmlConvert(obj, null);
	}
	
	/**
	 * 将xml写入out
	 * @param obj
	 * @param out
	 * @param formate
	 * @throws WxmlException
	 */
	public static void toXml(Object obj,OutputStream out,boolean formate) throws WxmlException{
		if(isAnnotation){
			AnnotationXmlConvertFactory.xmlConvert(obj, null,out,formate);
		}else{
			XmlConvertFactory.xmlConvert(obj, null, out, formate);
		}
		 
	}
	
	/**
	 * 生成xml字符串
	 * @param obj
	 * @param name
	 * @return
	 * @throws WxmlException
	 */
	public static String toXml(Object obj,String name) throws WxmlException{
		if(isAnnotation){
			return AnnotationXmlConvertFactory.xmlConvert(obj, name);
		}
		return XmlConvertFactory.xmlConvert(obj, name);
	}
	
	/**
	 * 将xml写入out
	 * @param obj
	 * @param name
	 * @param out
	 * @param formate
	 * @throws WxmlException
	 */
	public static void toXml(Object obj,String name,OutputStream out,boolean formate) throws WxmlException{
		if(isAnnotation){
			AnnotationXmlConvertFactory.xmlConvert(obj, name, out, formate);
		}else{
			XmlConvertFactory.xmlConvert(obj, name, out, formate);
		}
	}
	
	/**
	 * 解析xml反射成Class的对象
	 * @param cla
	 * @param filePath (绝对路径)
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 */
	public static Object parse(Class cla,String filePath) throws WxmlException{
		try {
			Document doc = null;
			doc = XmlParseUtil.read(filePath);
			return parse(cla,doc);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("解析文档失败："+filePath,e);
		}
		
	}
	/**
	 * 解析xml反射成Class的对象
	 * @param cla
	 * @param filePath (绝对路径)
	 * @param
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 * @desc 将根元素下名为eleName的子元素解析成Class 的实例
	 * 
	 */
	public static Object parse(Class cla,String filePath,String eleName) throws WxmlException{
		try {
			Document doc = null;
			doc = XmlParseUtil.read(filePath);
			return parse(cla,doc,eleName);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("解析xml失败："+filePath,e);
		}
	}
	
	/**
	 * 解析xml反射成Class的对象
	 * @param cla
	 * @param doc
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 */
	public static Object parse(Class cla,Document doc) throws WxmlException{
		try {
			Element ele = doc.getRootElement();
			if(WxmlFactory.isAnnotation){
				//启动annotation配置
				return AnnotationTypeConvertFactory.typeConvert(cla, ele, null);
			}else{
				return TypeConvertFactory.typeConvert(cla, ele);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("解析xml失败",e);
		}
		
	}
	
	/**
	 * 解析xml反射成Class的对象
	 * @param cla
	 * @param doc
	 * @param eleName
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 * @desc 将根元素下名为eleName的子元素解释成Class 的实例
	 */
	public static Object parse(Class cla,Document doc,String eleName) throws WxmlException{
		try {
			Element ele = null;
			if(StringUtil.isNull(eleName)){
				ele = doc.getRootElement();
			}else{
				ele = doc.getRootElement(); 
				ele = ele.element(eleName);
			}
			if(WxmlFactory.isAnnotation){
				//启动annotation配置
				return AnnotationTypeConvertFactory.typeConvert(cla, ele, null);
			}else{
				return TypeConvertFactory.typeConvert(cla, ele);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("解析xml失败",e);
		}
	}
	
	/**
	 * 解析xml反射成Class的对象
	 * @param cla
	 * @param ele
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 */
	public static Object parse(Class cla,Element ele) throws WxmlException{
		try {
			if(WxmlFactory.isAnnotation){
				//启动annotation配置
				return AnnotationTypeConvertFactory.typeConvert(cla, ele, null);
			}else{
				return TypeConvertFactory.typeConvert(cla, ele);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("解析xml失败",e);
		}
	}
	
	/**
	 * 解析xml反射成List的对象
	 * @param doc
	 * @param eleName 如果为空，则将根元素下的元素集合反射成List
	 * @param generics  List<T>  T所代表的的类型
	 * @desc 将根元素下的名为eleName的元素集合解析成List<T> T的Class为generics
	 * @return
	 * @throws WxmlException 
	 */
	public static <T> List<T> parseList(Document doc,String eleName,Class<T> generics) throws WxmlException{
		Element ele = null;
		List<Element> listEle = null;
		if(StringUtil.isNull(eleName)){
			ele = doc.getRootElement();
			listEle = ele.elements();
		}else{
			ele = doc.getRootElement(); 
			listEle = ele.elements(eleName);
		}
		if(WxmlFactory.isAnnotation){
			//启动annotation配置
			return AnnotationTypeConvertFactory.listConver(List.class, listEle, generics);
		}else{
			return TypeConvertFactory.listConver(List.class,listEle, generics);
		}
		
	}
	
	/**
	 * 解析xml反射成List的对象
	 * @param listEle
	 * @param generics List<T>  T所代表的的类型
	 * @return
	 * @throws WxmlException 
	 * @desc 将元素集合解析成List<T> T的Class为generics
	 */
	public static <T> List<T> parseList(List<Element> listEle,Class<T> generics) throws WxmlException{
		if(WxmlFactory.isAnnotation){
			//启动annotation配置
			return AnnotationTypeConvertFactory.listConver(List.class, listEle, generics);
		}else{
			return TypeConvertFactory.listConver(List.class, listEle, generics);
		}
		
	}
}
