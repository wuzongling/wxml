package indi.wzl.wxml;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import indi.wzl.Exception.WxmlException;
import indi.wzl.constant.TypeConstant;
import indi.wzl.util.WxmlTypeUtil;

public class AnnotationTypeConvertFactory {
	private static AnnotationTypeConvertHandle typeConvert;
	
	public static AnnotationTypeConvertHandle getSingleTypeConvert(){
		if(typeConvert == null){
			typeConvert= new AnnotationTypeConvertHandle();
		}
		return typeConvert;
	}
	
	/**
	 * 类型解析
	 * @param cla
	 * @param ele
	 * @param annotations
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 */
	public static Object typeConvert(Class cla, Element ele,Map<String,Object> fieldAnnotations) throws WxmlException{
		// TODO Auto-generated method stub
		int type = WxmlTypeUtil.getType(cla);
		return typeConvertHandle(cla, ele,ele.getName(),type,fieldAnnotations);
	}
	
	/**
	 * 类型解析
	 * @param cla
	 * @param ele
	 * @param name 需要转换的元素下的子元素名或者属性名
	 * @param annotations
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 */
	public static Object typeConvert(Class cla, Element ele,String name,Map<String,Object> fieldAnnotations) throws WxmlException{
		// TODO Auto-generated method stub
		int type = WxmlTypeUtil.getType(cla);
		return typeConvertHandle(cla, ele,name,type,fieldAnnotations);
	}
	
	
	public static <T> List<T> listConver(Class cla,List<Element> listEle,Class<T> generics) throws WxmlException{
		AnnotationTypeConvertHandle tc = getSingleTypeConvert();
		List<T> list = tc.listConvert(cla, listEle, generics);
		return list;
	}
	
	/**
	 * 类型转换处理
	 * @param cla
	 * @param ele 
	 * @param name 需要转换的元素下的子元素名或者属性名
	 * @param annotation
	 * @param type
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws WxmlException 
	 */
	private static Object typeConvertHandle(Class cla, Element ele,String name,int type,Map<String,Object> fieldAnnotations) throws WxmlException{
		AnnotationTypeConvertHandle tc = getSingleTypeConvert();
		switch (type) {
		case TypeConstant.INTEGET:
			return tc.integerConvert(ele, name, fieldAnnotations);
		case TypeConstant.LONG:
			return tc.longConvert(ele, name, fieldAnnotations);
		case TypeConstant.BOOLEAN:
			return tc.booleanConvert(ele, name, fieldAnnotations);
		case TypeConstant.BEAN:
			return tc.beanConvert(cla, ele);
		case TypeConstant.DOUBLE:
			return tc.doubleConvert(ele, name, fieldAnnotations);
		case TypeConstant.FLOAT:
			return tc.floatConvert(ele, name, fieldAnnotations);
		case TypeConstant.LIST:
			return ele.elements();
		case TypeConstant.MAP:
			return tc.mapConvert(cla, ele);
		case TypeConstant.STRING:
			return tc.stringConvert(ele, name, fieldAnnotations);
		}
		return null;
	}
}
