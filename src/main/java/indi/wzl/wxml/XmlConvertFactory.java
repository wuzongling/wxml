package indi.wzl.wxml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import indi.wzl.Exception.WxmlException;
import indi.wzl.constant.TypeConstant;
import indi.wzl.interfac.XmlConvert;
import indi.wzl.util.StringUtil;
import indi.wzl.util.WxmlTypeUtil;

public class XmlConvertFactory {
	public static XmlConvert xmlConvert;

	public static XmlConvert getSingleXmlConvert() {
		if (xmlConvert == null) {
			xmlConvert = new XmlConvertHandle();
		}
		return xmlConvert;
	}
	
	/**
	 * document解析
	 * @param obj
	 * @param name
	 * @return
	 * @throws WxmlException
	 */
	public static Document documentConvert(Object obj, String name)
			throws WxmlException {
		// 文档对象
		Document document = DocumentHelper.createDocument();
		try {
			Class cla = obj.getClass();
			int type = WxmlTypeUtil.getType(cla);
			Element root;
			if (StringUtil.isNull(name)) {
				root = DocumentHelper.createElement(cla.getSimpleName());
			} else {
				root = DocumentHelper.createElement(name);
			}
			document.setRootElement(root);
			xmlConvertHandle(obj, root, null, obj, type);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("xml转换失败：", e);
		}
		
		return document;
	}
	
	/**
	 * xml解析
	 * @param obj
	 * @param name
	 * @return
	 * @throws WxmlException
	 */
	public static String xmlConvert(Object obj, String name)
			throws WxmlException {
		Document document = documentConvert(obj, name);
		return getDocumentXml(document);
	}
	
	/**
	 * xml解析
	 * @param obj
	 * @param name
	 * @param out
	 * @param formate
	 * @throws WxmlException
	 */
	public static void xmlConvert(Object obj, String name,OutputStream out,boolean formate)
			throws WxmlException {
		try {
			Document document = documentConvert(obj, name);
			XMLWriter writer = null;
			if(formate){
				OutputFormat format = OutputFormat.createPrettyPrint();
				writer = new XMLWriter(out,format);
			}else{
				writer = new XMLWriter(out);
			}
		    writer.write(document);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("xml转换失败：", e);
		}
	}
	
	/**
	 * xml解析
	 * @param obj
	 * @param parentEle
	 * @param name
	 * @param value
	 * @return
	 * @throws WxmlException
	 */
	public static <T> Object xmlConvert(Object obj, Element parentEle,
			String name, int type,Object value) throws WxmlException {
		return xmlConvertHandle(obj, parentEle, name, value, type);
	}

	/**
	 * 类型转换处理
	 * 
	 * @param cla
	 * @param ele
	 * @param type
	 * @return
	 * @throws WxmlException
	 */
	private static Object xmlConvertHandle(Object obj, Element ele, String name,
			Object value, int type) throws WxmlException {
		XmlConvert xc = getSingleXmlConvert();
		switch (type) {
		case TypeConstant.INTEGET:
			return xc.addElement(ele, name, value, obj);
		case TypeConstant.LONG:
			return xc.addElement(ele, name, value, obj);
		case TypeConstant.BOOLEAN:
			return xc.addElement(ele, name, value, obj);
		case TypeConstant.BEAN:
			return xc.addBean(ele, name, value, obj);
		case TypeConstant.DOUBLE:
			return xc.addElement(ele, name, value, obj);
		case TypeConstant.FLOAT:
			return xc.addElement(ele, name, value, obj);
		case TypeConstant.LIST:
			return listConver(ele, name, (List) value, String.class, obj);
		case TypeConstant.MAP:
			return mapConver(ele, name, (Map) value, obj);
		case TypeConstant.STRING:
			return xc.addElement(ele, name, value, obj);
		}
		return null;
	}

	/**
	 * list类型解析
	 * 
	 * @param cla
	 * @param listEle
	 * @param generics
	 *            List<T> T 类型的Class对象
	 * @desc 将元素集合listEle 解析成List<T> T的Class对象为generics
	 * @return
	 * @throws WxmlException
	 */
	public static <T, B> Object listConver(Element ele, String name,
			List<T> list, Class<T> generics, B b) throws WxmlException {
		XmlConvert xc = getSingleXmlConvert();
		return xc.addList(ele, name, list, generics, b);
	}

	/**
	 * map类型解析
	 * 
	 * @param parentEle
	 * @param name
	 * @param map
	 * @param cla
	 * @return
	 * @throws WxmlException 
	 */
	public static <T> Object mapConver(Element parentEle, String name, Map map,
			T t) throws WxmlException {
		XmlConvert xc = getSingleXmlConvert();
		return xc.addMap(parentEle, name, map, t);
	}
	
	/**
	 * 获取document xml字符串
	 * @param document
	 * @return
	 */
	public static String getDocumentXml(Document document){
		 XMLWriter writer = null;
		 ByteArrayInputStream bytein = null;
		 ByteArrayOutputStream byteOut = null;
		 byte[] b = new byte[9980];
		 String xmlStr = null;
		 int index = 0;
	     try { 
	    	   byteOut = new ByteArrayOutputStream();
		       writer = new XMLWriter( byteOut );
		       writer.write(document);
		       bytein = new ByteArrayInputStream(byteOut.toByteArray());
		       while((index =bytein.read(b))!= -1){
		    	   xmlStr = new String(b,0,index,"utf-8");
		       }
		       bytein.close();
		       byteOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlStr;
	}
}
