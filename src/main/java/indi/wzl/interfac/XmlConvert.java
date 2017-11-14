package indi.wzl.interfac;

import indi.wzl.Exception.WxmlException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public interface XmlConvert {
	/**
	 * 添加元素
	 * @param parentEle
	 * @param name
	 * @param value
	 * @param cla
	 * @return 
	 */
	public <T> Object addElement(Element parentEle,String name,Object value,T t);
	
	/**
	 * 添加属性
	 * @param parentEle
	 * @param name
	 * @param value
	 * @param cla
	 */
	public <T> Object addAttribute(Element parentEle,String name,Object value,T t);
	
	/**
	 * 添加list元素
	 * @param parentEle
	 * @param name
	 * @param list
	 * @param generics 泛型
	 */
	public <T,B> Object addList(Element parentEle,String name,List<T> list,Class<T> generics,B b) throws WxmlException;
	
	/**
	 * 添加map元素
	 * @param parentEle
	 * @param name
	 * @param map
	 */
	public <T> Object addMap(Element parentEle,String name,Map<String,Object> map,T t) throws WxmlException;
	
	/**
	 * 添加bean元素
	 * @param parentEle
	 * @param name
	 * @param value
	 * @param cla
	 */
	public <T> Object addBean(Element parentEle,String name,Object value,T t) throws WxmlException;
	
	/**
	 * bean字段解析
	 * @param parentEle
	 * @param field
	 * @param cla
	 * @return
	 */
	public <T> Object beanFieldConvert(Element parentEle,Field field,T t) throws WxmlException;
}
