package indi.wzl.wxml;

import indi.wzl.Exception.WxmlException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * map类型转换处理器
 *
 * @author zonglin_wu
 * @create 2017-11-17 11:03
 **/
public class MapConvertHandle {

    /**
     * map转换
     * @param element
     * @param map
     * @return
     * @throws WxmlException
     */
    public void mapConvert(Element element, Map map) throws WxmlException {
        try {
            if (element.elements().size() > 0) {
                listMapConvert(element, map);
            } else {
                //文本，没有子元素
                map.put(element.getName(), getTextValue(element, map));
            }
        }catch (Exception e){
            throw new WxmlException("map解析失败");
        }
    }

    /**
     * 多个子元素的map解析
     * @param element
     * @param map
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws WxmlException
     */
    public void listMapConvert(Element element, Map map) throws IllegalAccessException, InstantiationException, WxmlException {
        Map map1 = map.getClass().newInstance();
        for (Element element1 : (List<Element>) element.elements()) {
            mapConvert(element1, map1);
        }
        map.put(element.getName(), getListMapValue(element, map, map1));
    }

    private Object getTextValue(Element element, Map map) {
        String name = element.getName();
        List list = new ArrayList();
        if (map.containsKey(name)) {
            Object obj = map.get(name);
            list.add(obj);
            list.add(element.getTextTrim());
            return list;
        } else {
            return element.getTextTrim();
        }
    }

    private Object getListMapValue(Element element, Map map, Map value) {
        String name = element.getName();
        List list = new ArrayList();
        if (map.containsKey(name)) {
            Object obj = map.get(name);
            list.add(obj);
            list.add(value);
            return list;
        } else {
            return value;
        }
    }

}
