package indi.wzl.wxml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import indi.wzl.Exception.WxmlException;
import indi.wzl.annotation.ParentElement;
import indi.wzl.annotation.WxmlRootElement;
import indi.wzl.constant.AnnotationTypeConstant;
import indi.wzl.constant.TypeConstant;
import indi.wzl.util.ClassUtil;
import indi.wzl.util.StringUtil;
import indi.wzl.util.WxmlTypeUtil;

/**
 * annotation方式的类型转换解析 
* @ClassName: AnnotationTypeConvertHandle 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author wuzonglin
* @date 2016年3月1日 上午11:30:43 
*
 */
public class AnnotationTypeConvertHandle extends TypeConvertHandle {

	ElementConvertHandle eh = new ElementConvertHandle();
	
	AttributeConvertHandle ah = new AttributeConvertHandle();
	
	public Integer integerConvert(Element ele, String name,
			Map<String,Object> fieldAnnotations) throws WxmlException {
		// TODO Auto-generated method stub
		if (fieldAnnotations != null && fieldAnnotations.size() > 0) {
			String value = getValue(ele, name, fieldAnnotations);
			if (value != null) {
				return new Integer(value);
			}

		}
		return null;
	}

	public Long longConvert(Element ele, String name, Map<String,Object> fieldAnnotations)
			throws WxmlException {
		if (fieldAnnotations != null && fieldAnnotations.size() > 0) {
			String value = getValue(ele, name, fieldAnnotations);
			if (value != null) {
				return new Long(value);
			}

		}
		return null;

	}

	public Float floatConvert(Element ele, String name,
			Map<String,Object> fieldAnnotations) throws WxmlException {
		if (fieldAnnotations != null && fieldAnnotations.size() > 0) {
			String value = getValue(ele, name, fieldAnnotations);
			if (value != null) {
				return new Float(value);
			}
		}
		return null;
	}

	public Double doubleConvert(Element ele, String name,
			Map<String,Object> fieldAnnotations) throws WxmlException {
		if (fieldAnnotations != null && fieldAnnotations.size() > 0) {
			String value = getValue(ele, name, fieldAnnotations);
			if (value != null) {
				return new Double(value);
			}
		}
		return null;
	}

	public String stringConvert(Element ele, String name,
			Map<String,Object> fieldAnnotations) throws WxmlException {
		if (fieldAnnotations != null && fieldAnnotations.size() > 0) {
			String value = getValue(ele, name, fieldAnnotations);
			if (value != null) {
				return new String(value);
			}
		}
		return null;
	}

	public Boolean booleanConvert(Element ele, String name,
			Map<String,Object> fieldAnnotations) throws WxmlException {
		if (fieldAnnotations != null && fieldAnnotations.size() > 0) {
			String value = getValue(ele, name, fieldAnnotations);
			if (value != null) {
				return new Boolean(value);
			}
		}
		return null;
	}

	public <T> List<T> listConvert(Class cla, List<Element> listEle,
			Class<T> generics) throws WxmlException {
		// TODO Auto-generated method stub
		try {
			List<T> list = null;
			if (cla.isInterface()) {
				// 如果是接口，默认用ArrayList类型的实体
				list = new ArrayList<T>();
			} else {
				list = (List<T>) cla.newInstance();
			}

			if (generics == null) {
				return list;
			}
			if(null != listEle && listEle.size() > 0){
				for (Element element : listEle) {
					T t = generics.newInstance();
					T obj = (T) AnnotationTypeConvertFactory.typeConvert(
							t.getClass(), element, null);
					list.add(obj);

				}
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WxmlException("List类型 转换失败", e);
		}
	}

	public Object beanConvert(Class cla, Element ele) throws WxmlException {
		Object obj = null;
		try {
			Field[] fields = cla.getDeclaredFields();
			Annotation claAnnotation = cla.getAnnotation(WxmlRootElement.class);
			if (claAnnotation != null
					&& AnnotationTypeConstant.ROOTELEMENT.equals(claAnnotation
							.annotationType().getSimpleName())) {
				obj = cla.newInstance();
				for (Field field : fields) {
					Object property = beanFieldConvert(field, ele);
					String fieldName = field.getName();
					if (property != null) {
						ClassUtil.setProperty(obj, fieldName, property);
					}

				}
			} else {
				throw new WxmlException("class no @XmlRootElement tag");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WxmlException("java bean 转换失败 ", e);
		}
		return obj;

	}

	public Object beanFieldConvert(Field field, Element ele)
			throws WxmlException {
		try {
			Class typeCla = field.getType();
			int type = WxmlTypeUtil.getType(typeCla);
			String fieldName = field.getName();
			Annotation[] annotations = field.getAnnotations();
			Map<String,Object> map = WxmlTypeUtil.getFieldAnnotationMap(annotations);
			if(map != null){
				//元素名
				String eleName = (String) map.get(AnnotationTypeConstant.ELEMENT);
				if(StringUtil.isNotNull(eleName))
					fieldName = eleName;

				String parentElement = (String) map.get(AnnotationTypeConstant.PARENTELEMENT);
				if (StringUtil.isNotNull(parentElement)) {
					ele= ele.element(parentElement);
				}
			}
			switch (type) {
			case TypeConstant.LIST:
				Type fieldtype = field.getGenericType();
				Type[] generics = ((ParameterizedType) fieldtype)
						.getActualTypeArguments();
				// 泛型的class对象
				Class mTClass = (Class) (generics[0]);
				List<Element> listEle = null;
				if (annotations != null && annotations.length > 0){
					if (map.containsKey(AnnotationTypeConstant.ATTRIBUTE)) {
						// List类型的属性只能用@WxmlElement标识
						throw new WxmlException("List类型的属性只能用@WxmlElement标识");
					}
					if (map.containsKey(AnnotationTypeConstant.ELEMENT)) {
						if(ele != null)
							listEle = ele.elements(fieldName);
						return AnnotationTypeConvertFactory.listConver(typeCla,
								listEle, mTClass);
					}
				}

				return null;
			default:
				return AnnotationTypeConvertFactory.typeConvert(typeCla, ele,
						fieldName, map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("beanFieldConvert 失败", e);
		}
	}

	/**
	 * 获取字段值
	 * 
	 * @param ele
	 * @param name
	 * @return
	 * @throws WxmlException
	 */
	private String getValue(Element ele, String name, Map<String,Object> fieldAnnotations)
			throws WxmlException {
		try {
			String returnValue = "";
			String annotationVal = "";
			if(fieldAnnotations.containsKey(AnnotationTypeConstant.ELEMENT)){
				annotationVal = (String) fieldAnnotations.get(AnnotationTypeConstant.ELEMENT);
				if (!StringUtil.isNull(annotationVal)) {
					name = annotationVal;
				}
				returnValue = eh.getElementValue(ele, name);
			}else if(fieldAnnotations.containsKey(AnnotationTypeConstant.ATTRIBUTE)){
				annotationVal = (String) fieldAnnotations.get(AnnotationTypeConstant.ATTRIBUTE);
				if (!StringUtil.isNull(annotationVal)) {
					name = annotationVal;
				}
				returnValue = ah.getAttributeValue(ele, name);
			}
			return returnValue;
		} catch (Exception e) {
			// TODO: handle exception
			new WxmlException("字段：" + name + " 转换失败", e);
		}
		return null;
	}

}
