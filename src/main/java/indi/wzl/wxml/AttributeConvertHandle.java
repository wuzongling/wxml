package indi.wzl.wxml;

import org.dom4j.Attribute;
import org.dom4j.Element;

public class AttributeConvertHandle {
	
	public String getAttributeValue(Element element,String attributeName){
		Attribute attribute = element.attribute(attributeName);
		if(attribute != null ){
			return attribute.getStringValue();
		}else{
			return null;
		}
		
	}
}
