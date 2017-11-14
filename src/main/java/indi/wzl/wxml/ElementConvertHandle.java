package indi.wzl.wxml;

import org.dom4j.Element;

public class ElementConvertHandle {
	
	public String getElementValue(Element element,String eleName){
		Element ele = element.element(eleName);
		if(ele != null){
			return ele.getTextTrim();
		}else{
			return null;
		}
		
	}
}
