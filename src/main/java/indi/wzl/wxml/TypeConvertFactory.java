package indi.wzl.wxml;

import java.util.List;

import org.dom4j.Element;

import indi.wzl.Exception.WxmlException;
import indi.wzl.constant.TypeConstant;
import indi.wzl.interfac.TypeConvert;
import indi.wzl.util.StringUtil;
import indi.wzl.util.WxmlTypeUtil;

/**
 * 
* @ClassName: TypeConvertFactory 
* @Description: 类型转换工厂类
* @author wuzonglin
* @date 2015年10月20日 下午5:11:15 
*
 */
public class TypeConvertFactory  {
	
	private static TypeConvert typeConvert;
	
	public static TypeConvert getSingleTypeConvert(){
		if(typeConvert == null){
			typeConvert= new TypeConvertHandle();
		}
		return typeConvert;
	}
	
	/**
	 * 类型解析
	 * @param cla
	 * @param ele
	 * @desc 将元素ele解析成cla
	 * @return
	 * @throws WxmlException 
	 */
	public static Object typeConvert(Class cla, Element ele) throws WxmlException {
		// TODO Auto-generated method stub
		int type = WxmlTypeUtil.getType(cla);
		return typeConvertHandle(cla, ele, type);
	}
	
	/**
	 * 类型解析
	 * @param cla
	 * @param ele
	 * @param fieldName
	 * @desc 将ele元素下的元素名为fieldName的子元素解析成cla
	 * @return
	 * @throws WxmlException 
	 */
	public static Object typeConvert(Class cla, Element ele, String fieldName) throws WxmlException {
		// TODO Auto-generated method stub
		if(!StringUtil.isNull(fieldName)){
			ele = ele.element(fieldName);
		}
		int type = WxmlTypeUtil.getType(cla);
		return typeConvertHandle(cla, ele, type);
	}
	
	/**
	 * list类型解析
	 * @param cla
	 * @param listEle
	 * @param generics  List<T> T 类型的Class对象
	 * @desc 将元素集合listEle 解析成List<T> T的Class对象为generics
	 * @return
	 * @throws WxmlException 
	 */
	public static <T> List<T> listConver(Class cla,List<Element> listEle,Class<T> generics) throws WxmlException{
		TypeConvert tc = getSingleTypeConvert();
		List<T> list = tc.listConvert(cla, listEle, generics);
		return list;
	}
	
	/**
	 * 类型转换处理
	 * @param cla
	 * @param ele
	 * @param type
	 * @return
	 * @throws WxmlException 
	 */
	private static Object typeConvertHandle(Class cla, Element ele,int type) throws WxmlException{
		TypeConvert tc = getSingleTypeConvert();
		switch (type) {
		case TypeConstant.INTEGET:
			return tc.integerConvert(cla,ele);
		case TypeConstant.LONG:
			return tc.longConvert(cla,ele);
		case TypeConstant.BOOLEAN:
			return tc.booleanConvert(cla, ele);
		case TypeConstant.BEAN:
			return tc.beanConvert(cla, ele);
		case TypeConstant.DOUBLE:
			return tc.doubleConvert(cla, ele);
		case TypeConstant.FLOAT:
			return tc.floatConvert(cla, ele);
		case TypeConstant.LIST:
			return ele.elements();
		case TypeConstant.MAP:
			return tc.mapConvert(cla, ele);
		case TypeConstant.STRING:
			return tc.stringConvert(cla, ele);
		}
		return null;
	}
	
	

}
