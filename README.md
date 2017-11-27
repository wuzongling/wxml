wxml工具
========
# 一、简介

## 1.1 概述
wxml工具是一款用java制作的xml解析工具。它可以很方便的将xml格式的数据通过1-2行代码转换成java bean,也可以将java bean很方便的转换成xml。

## 1.2 特性
* 1.使用方便快捷，整个过程只需1-2行代码，就像fastjson那种使用一样方便。
* 2.支持格式多样，可以支持list、map、java bean以及他们之间相互嵌套。
* 3.使用注解配置映射关系，无配置文件。
* 4.映射规则多样，可以指定xml某个具体元素或者属性进行映射。

## 1.3 发展

## 1.4 下载
        源码地址：https://github.com/wuzongling/wxml
        中央仓库地址：暂时未上传到中央仓库

# 二、快速入门

## 2.1 非注解使用
    非注解方式映射规则：将java类中的属性映射成xml的元素（如果要映射成属性需要开启注解）
[test.xml](https://github.com/wuzongling/wxml/blob/master/src/test/java/indi/wzl/test/test.xml)<br/>
[TestBean1.java](https://github.com/wuzongling/wxml/blob/master/src/test/java/indi/wzl/test/TestBean1.java)<br/>

### 2.1.1 xml转换java bean 
``` java
	public void testXmlToBean() throws WxmlException, DocumentException{
		//设置启动annotation，默认是不启动
//		WxmlFactory.setAnnotation(true);
		TestBean1 tb = (TestBean1) WxmlFactory.parse(TestBean1.class,"/indi/wzl/test/test.xml");
		System.out.println(JSON.toJSONString(tb));
	}
```
### 2.1.2 xml转换list
``` java
public void testXmlToList() throws DocumentException, WxmlException{
		List<TestData1> list = WxmlFactory.parseList("/indi/wzl/test/test.xml","data",TestData1.class);
		System.out.println(JSON.toJSONString(list));
	}
```
### 2.1.3 xml 转换map
``` java
public void testXmlToListMap() throws DocumentException, WxmlException{
		Document doc=XmlParseUtil.readClassPath("/indi/wzl/test/test.xml");
		List<Map> list = WxmlFactory.parseList(doc, "data",Map.class);
		System.out.println(JSON.toJSONString(list));
	}
```
## 2.2 注解使用
    注解方式：默认是不开启注解的，需要执行WxmlFactory.setAnnotation(true);手动设置开启。 
[test.xml](https://github.com/wuzongling/wxml/blob/master/src/test/java/indi/wzl/test/test.xml)<br/>
[TestBean.java](https://github.com/wuzongling/wxml/blob/master/src/test/java/indi/wzl/test/TestBean.java)

### 2.2.1注解
* 1.@WxmlRootElement（使用在类名上，必须的，如果开启注解后类没这个注解会报错） 
* 2.@WxmlElement（使用在类属性上，表示映射成xml元素。可以设置所需要映射的元素名，如果没有此标签或@WxmlAttribute，则该属性不会被映射） 
* 3.@WxmlAttribute（使用在类属性上，表示映射成xml元素的属性。可以设置所需要映射的元素属性名，如果没有此标签或@WxmlElement，则该属性不会被映射） 
* 4.@ParentElement(使用在类属性上，表示元素的父元素。) 

### 2.2.2 xml转换java bean
``` java
public void testXmlToBeanAnnotation() throws WxmlException, DocumentException{
		//设置启动annotation，默认是不启动
		WxmlFactory.setAnnotation(true);
		TestBean tb = (TestBean) WxmlFactory.parse(TestBean.class,"/indi/wzl/test/test.xml");
		System.out.println(JSON.toJSONString(tb));
	}
```
### 2.2.3 xml转换list
``` java
public void testXmlToListAnnotation() throws DocumentException, WxmlException{
		//设置启动annotation，默认是不启动
		WxmlFactory.setAnnotation(true);
		List<TestData> list = WxmlFactory.parseList("/indi/wzl/test/test.xml","data",TestData.class);
		System.out.println(JSON.toJSONString(list));
	}
```
## 2.3 其它
    更多测试用例请查看indi.wzl.test.WxmlTest测试类
