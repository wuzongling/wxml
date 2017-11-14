package indi.wzl.wxml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;
import indi.wzl.Exception.WxmlException;
import indi.wzl.constant.TypeConstant;
import indi.wzl.interfac.XmlConvert;
import indi.wzl.util.ClassUtil;
import indi.wzl.util.StringUtil;
import indi.wzl.util.WxmlTypeUtil;

public class XmlConvertHandle implements XmlConvert {

	@Override
	public <T> Object addElement(Element parentEle, String name, Object value,
			T t) {
		Element ele = parentEle.addElement(name);
		ele.setText(String.valueOf(value));
		return ele;
	}

	@Override
	public <T> Object addAttribute(Element parentEle, String name,
			Object value, T t) {
		Element ele = parentEle.addAttribute(name, String.valueOf(value));
		return ele;
	}

	@Override
	public <T, B> Object addList(Element parentEle, String name, List<T> list,
			Class<T> generics, B b) throws WxmlException {
		try {
			int type;
			for (T t : list) {
				type = WxmlTypeUtil.getType(generics);
				if(!StringUtil.isNull(name)){
					Element ele = parentEle.addElement(name);
					XmlConvertFactory.xmlConvert(t, ele, null, type, t);
				}else{
					XmlConvertFactory.xmlConvert(t, parentEle, null, type, t);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("list解析失败：", e);
		}
		return parentEle;
	}

	@Override
	public <T> Object addMap(Element parentEle, String name, Map<String,Object> map, T t) throws WxmlException {
		// TODO Auto-generated method stub
		try {
			Element ele = parentEle;
			int type;
			if(!StringUtil.isNull(name)){
				ele = parentEle.addElement(name);
			}
			for ( Map.Entry<String,Object> entry : map.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();
				type = WxmlTypeUtil.getType(value.getClass());
				XmlConvertFactory.xmlConvert(value, ele, key, type, value);
			} 
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("map 解析失败：", e);
		}
		return parentEle;
	}

	@Override
	public <T extends Object> Object addBean(Element parentEle, String name,
			Object value, T t) throws WxmlException {
		Element element = parentEle;
		try {
			Class<T> cla = (Class<T>) t.getClass();
			Field[] fields = cla.getDeclaredFields();
			for (Field field : fields) {

				beanFieldConvert(element, field, t);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("bean解析失败：", e);
		}

		return element;
	}

	@Override
	public <T> Object beanFieldConvert(Element parentEle, Field field, T t)
			throws WxmlException {
		try {
			Class typeCla = field.getType();
			// 类型
			int type = WxmlTypeUtil.getType(typeCla);
			// 字段名
			String name = field.getName();
			//字段值
			Object value = ClassUtil.getProperty(t, name);
			Element element = parentEle;
			if (value != null) {
				/*Annotation parentannotation = field
						.getAnnotation(ParentElement.class);
				if (parentannotation != null) {
					String parentName = ClassUtil.getAnnotationMethodValue(
							parentannotation, "value");
					Element listEle = parentEle.addElement(parentName);
					element = listEle;
				}*/
				switch (type) {
				case TypeConstant.LIST:
					Type fieldtype = field.getGenericType();
					Type[] generics = ((ParameterizedType) fieldtype)
							.getActualTypeArguments();
					// 泛型的class对象
					Class mTClass = (Class) (generics[0]);
					XmlConvertFactory.listConver(element, name, (List) value,
							mTClass, t);
					break;

				default:
					XmlConvertFactory.xmlConvert(t, element, name, type,
							value);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("beanFieldConvert 失败：", e);
		}
		return parentEle;
	}

	private Object parentEleHadle(Element e,Class cla) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		return null;
		
	}

}
