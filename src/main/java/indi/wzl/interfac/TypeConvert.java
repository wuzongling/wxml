package indi.wzl.interfac;

import indi.wzl.Exception.WxmlException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

/**
 * 类型转换接口
 * @author wuzongling
 *
 */
public interface TypeConvert {
	
	/**
	 * 整形转换
	 * @param cla
	 * @param ele
	 * @return
	 */
	public Integer integerConvert(Class cla,Element ele);
	
	/**
	 * 长整形转换
	 * @param cla
	 * @param ele
	 * @return
	 */
	public Long longConvert(Class cla,Element ele);
	
	/**
	 * 浮点型转换
	 * @param cla
	 * @param ele
	 * @return
	 */
	public Float floatConvert(Class cla,Element ele);
	
	/**
	 * 双精度转换
	 * @param cla
	 * @param ele
	 * @return
	 */
	public Double doubleConvert(Class cla,Element ele);
	
	/**
	 * 字符串转换
	 * @param cla
	 * @param ele
	 * @return
	 */
	public String stringConvert(Class cla,Element ele);
	
	/**
	 * 布尔类型转换
	 * @param cla
	 * @param ele
	 * @return
	 */
	public Boolean booleanConvert(Class cla,Element ele);
	
	/**
	 * list集合转换
	 * @param <T>
	 * @param cla
	 * @param ele
	 * @param Generics List<T> T的类型字符串
	 * @return
	 * @throws WxmlException
	 */
	public <T> List<T> listConvert(Class cla, List<Element> listEle,Class<T> generics) throws WxmlException;
	
	/**
	 * map转换
	 * @param cla
	 * @param ele
	 * @return
	 * @throws WxmlException 
	 */
	public Map mapConvert(Class cla,Element ele) throws WxmlException;
	
	/**
	 * java bean转换
	 * @param cla
	 * @param ele
	 * @return
	 * @throws WxmlException 
	 */
	public Object beanConvert(Class cla,Element ele) throws WxmlException;
}
