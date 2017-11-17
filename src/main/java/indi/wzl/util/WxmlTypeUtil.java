package indi.wzl.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.wzl.constant.AnnotationTypeConstant;
import indi.wzl.constant.TypeConstant;

/**
 * 
* @ClassName: WxmlTypeUtil 
* @Description: 类型工具类
* @author wuzonglin
* @date 2015年10月21日 下午4:05:35 
*
 */
public class WxmlTypeUtil {
	
	/**
	 * 获取需要解析的class的类型
	 * @param cla
	 * @return
	 */
	public static int getType(Class cla) {
		// TODO Auto-generated method stub
		if(int.class.equals(cla) || Integer.class.equals(cla)){
			return TypeConstant.INTEGET;
		}else if(Long.class.equals(cla) || long.class.equals(cla)){
			return TypeConstant.LONG;
		}else if(Float.class.equals(cla)|| float.class.equals(cla)){
			return TypeConstant.FLOAT;
		}else if(Double.class.equals(cla)||double.class.equals(cla)){
			return TypeConstant.DOUBLE;
		}else if(Boolean.class.equals(cla)|| boolean.class.equals(cla)){
			return TypeConstant.BOOLEAN;
		}else if(String.class.equals(cla)){
			return TypeConstant.STRING;
		}else if(isListType(cla)){
			return TypeConstant.LIST;
		}else if(isMapType(cla)){
			return  TypeConstant.MAP;
		}
		else{
			//默认bean
			return TypeConstant.BEAN;
		}
	}
	
	/**
	 * 获取注解类型
	 * @param annotation
	 * @return
	 */
	public static String getAnnotationType(Annotation annotation){
			String name = annotation.annotationType().getSimpleName();
			switch (name) {
			case AnnotationTypeConstant.ATTRIBUTE:
				return AnnotationTypeConstant.ATTRIBUTE;
			case AnnotationTypeConstant.ELEMENT:
				return AnnotationTypeConstant.ELEMENT;
			case AnnotationTypeConstant.ROOTELEMENT:
				return AnnotationTypeConstant.ROOTELEMENT;
			case AnnotationTypeConstant.PARENTELEMENT:
				return AnnotationTypeConstant.PARENTELEMENT;
			}
		
		return null;
	}
	
	/**
	 * 获取字段注解集合
	 * @param annotations
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Map<String,Object> getFieldAnnotationMap(Annotation... annotations) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Map<String,Object> map = null;
		if(annotations != null && annotations.length >0){
			map = new HashMap<String, Object>();
			for (Annotation annotation : annotations) {
				String type = getAnnotationType(annotation);
				Object value = ClassUtil.getAnnotationMethodValue(annotation, "value");
				map.put(type, value);
			}
		}
		
		return map;
		
	}
	
	/**
	 * 获取注解值
	 * @param type
	 * @param annotations
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static String getAnnotationValue(String type,Annotation... annotations) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		for (Annotation annotation : annotations) {
			String name = annotation.annotationType().getSimpleName();
			if(name.equals(type)){
				return ClassUtil.getAnnotationMethodValue(
						annotation, "value");
			}
		}
		return null;
	}
	
	
	/**
	 * 是否是List接口或者List的实现类
	 * @param cla
	 * @return
	 */
	public static boolean isListType(Class cla){
		boolean isInterface = cla.isInterface();
		if(isInterface && cla.equals(List.class)){
			//接口判断
			return true;
		}else{
			//实体类判断
			Class[] interfaces = cla.getInterfaces();
			for (Class interf : interfaces) {
				if(interf.equals(List.class)){
					return true;
				}
			} 
		}
		
		return false;
	}
	
	/**
	 * 是否是Map接口或者Map的实现类
	 * @param cla
	 * @return
	 */
	public static boolean isMapType(Class cla){
		boolean isInterface = cla.isInterface();
		if(isInterface && cla.equals(Map.class)){
			//接口判断
			return true;
		}else{
			//实体类判断
			Class[] interfaces = cla.getInterfaces();
			for (Class interf : interfaces) {
				if(interf.equals(Map.class)){
					return true;
				}
			} 
		}
		
		return false;
	}
 }
