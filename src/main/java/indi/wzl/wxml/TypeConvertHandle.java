package indi.wzl.wxml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import indi.wzl.Exception.WxmlException;
import indi.wzl.annotation.ParentElement;
import indi.wzl.constant.TypeConstant;
import indi.wzl.interfac.TypeConvert;
import indi.wzl.util.ClassUtil;
import indi.wzl.util.WxmlTypeUtil;

/**
 * 
 * @ClassName: TypeConvertHandle
 * @Description: 类型处理类
 * @author wuzonglin
 * @date 2015年10月20日 下午5:12:17
 *
 */
public class TypeConvertHandle implements TypeConvert {
	Logger logger = Logger.getLogger(TypeConvertHandle.class);

	public Integer integerConvert(Class cla, Element ele) {
		// TODO Auto-generated method stub
		if (ele != null) {
			return new Integer(ele.getTextTrim());
		} else {
			return null;
		}

	}

	public Long longConvert(Class cla, Element ele) {
		// TODO Auto-generated method stub
		if (ele != null) {
			return new Long(ele.getTextTrim());
		} else {
			return null;
		}
	}

	public Float floatConvert(Class cla, Element ele) {
		// TODO Auto-generated method stub
		if (ele != null) {
			return new Float(ele.getTextTrim());
		} else {
			return null;
		}
	}

	public Double doubleConvert(Class cla, Element ele) {
		// TODO Auto-generated method stub
		if (ele != null) {
			return new Double(ele.getTextTrim());
		} else {
			return null;
		}
	}

	public String stringConvert(Class cla, Element ele) {
		// TODO Auto-generated method stub
		if (ele != null) {
			//return new String(ele.getStringValue());
			return new String(ele.getTextTrim());
		} else {
			return null;
		}
	}

	public Boolean booleanConvert(Class cla, Element ele) {
		// TODO Auto-generated method stub
		if (ele != null) {
			return new Boolean(ele.getTextTrim());
		} else {
			return null;
		}

	}

	public <T> List<T> listConvert(Class cla, List<Element> listEle,
			Class<T> generics) throws WxmlException {
		// TODO Auto-generated method stub
		try {
			List<T> list = null;
			if (cla.isInterface()) {
				list = new ArrayList<T>();
			} else {
				// 如果是接口，默认用ArrayList类型的实体
				list = (List<T>) cla.newInstance();
			}

			if (generics == null) {
				return list;
			}
			for (Element element : listEle) {
				//T t = generics.newInstance();
				T obj = (T) TypeConvertFactory.typeConvert(generics,
						element);
				list.add(obj);

			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WxmlException("list 转换失败", e);
		}
	}

	public Map mapConvert(Class cla, Element ele) throws WxmlException {
		// TODO Auto-generated method stub
		List<Element> listEle = ele.elements();
		Map<String, Object> map = null;
		if (cla.isInterface()) {
			// 如果是接口，默认用HashMap类型的实体
			map = new HashMap<String, Object>();
		} else {
			try {
				map = (Map<String, Object>) cla.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new WxmlException("Map解析错误", e);
			}
		}

		for (Element element : listEle) {
			if (element.elements().size() > 0) {
				map.put(element.getName(), element);
			} else {
				map.put(element.getName(), element.getTextTrim());
			}
		}
		return map;
	}

	public Object beanConvert(Class cla, Element ele) throws WxmlException {
		// TODO Auto-generated method stub
		try {
			Field[] fields = cla.getDeclaredFields();
			Object obj = null;
			obj = cla.newInstance();
			for (Field field : fields) {

				Object property = beanFieldConvert(field, ele);
				String fieldName = field.getName();
				if (property != null) {
					ClassUtil.setProperty(obj, fieldName, property);
				}

			}
			return obj;
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("java bean转换失败", e);
		}

	}

	public Object beanFieldConvert(Field field, Element ele)
			throws WxmlException {
		try {
			Class typeCla = field.getType();
			int type = WxmlTypeUtil.getType(typeCla);
			String fieldName = field.getName();
			List<Element> listEle = null;
			switch (type) {
			case TypeConstant.LIST:
				Type fieldtype = field.getGenericType();
				Type[] generics = ((ParameterizedType) fieldtype)
						.getActualTypeArguments();
				// 泛型的class对象
				Class mTClass = (Class) (generics[0]);
				/*Annotation annotation = field.getAnnotation(ParentElement.class);
				if (annotation != null) {
					String annotationVal = ClassUtil.getAnnotationMethodValue(
							annotation, "value");
					Element el = ele.element(annotationVal);
					listEle = el.elements(fieldName);
					if (el != null) {
						listEle = el.elements(fieldName);
					}
				} else {
					listEle = ele.elements(fieldName);
				}*/
				listEle = ele.elements(fieldName);
				return TypeConvertFactory.listConver(typeCla, listEle, mTClass);
			default:
				return TypeConvertFactory.typeConvert(typeCla, ele, fieldName);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("", e);
		}
	}

}
