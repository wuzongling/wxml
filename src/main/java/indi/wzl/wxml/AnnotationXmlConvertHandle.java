package indi.wzl.wxml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import indi.wzl.Exception.WxmlException;
import indi.wzl.annotation.WxmlRootElement;
import indi.wzl.constant.AnnotationTypeConstant;
import indi.wzl.constant.TypeConstant;
import indi.wzl.interfac.XmlConvert;
import indi.wzl.util.ClassUtil;
import indi.wzl.util.StringUtil;
import indi.wzl.util.WxmlTypeUtil;

public class AnnotationXmlConvertHandle extends XmlConvertHandle {

	@Override
	public <T extends Object> Object addBean(Element parentEle, String name,
			Object value, T t) throws WxmlException {
		Element element = parentEle;
		try {
			Annotation claAnnotation = t.getClass().getAnnotation(
					WxmlRootElement.class);
			if (claAnnotation != null) {
				Class<T> cla = (Class<T>) t.getClass();
				Field[] fields = cla.getDeclaredFields();
				for (Field field : fields) {
					beanFieldConvert(element, field, t);
				}
			} else {
				throw new WxmlException("类没有@WxmlRootElement");
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
			// 字段值
			Object value = ClassUtil.getProperty(t, name);
			Element element = parentEle;
			Annotation[] annotations = field.getAnnotations();
			Map<String, Object> map = WxmlTypeUtil
					.getFieldAnnotationMap(annotations);
			if (value != null) {

				if (map == null || !(map.containsKey(AnnotationTypeConstant.ELEMENT)
						|| map.containsKey(AnnotationTypeConstant.ATTRIBUTE))) {
					return parentEle;
				}
				if (map != null
						&& map.containsKey(AnnotationTypeConstant.PARENTELEMENT)) {
					String parentName = (String) map
							.get(AnnotationTypeConstant.PARENTELEMENT);
					Element listEle = parentEle.addElement(parentName);
					element = listEle;
				}
				switch (type) {
				case TypeConstant.LIST:
					if (map.containsKey(AnnotationTypeConstant.ATTRIBUTE)) {
						// List类型的属性只能用@WxmlElement标识
						throw new WxmlException("List类型的属性只能用@WxmlElement标识");
					}
					Type fieldtype = field.getGenericType();
					Type[] generics = ((ParameterizedType) fieldtype)
							.getActualTypeArguments();
					// 泛型的class对象
					Class mTClass = (Class) (generics[0]);
					String attr = (String) map.get(AnnotationTypeConstant.ELEMENT);
					if(!StringUtil.isNull(attr)){
						name = attr;
					}
					XmlConvertFactory.listConver(element, name, (List) value,
							mTClass, t);
					break;

				default:
					AnnotationXmlConvertFactory.xmlConvert(t, element, name,
							type, value, map);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new WxmlException("beanFieldConvert 失败：", e);
		}
		return parentEle;
	}

}
