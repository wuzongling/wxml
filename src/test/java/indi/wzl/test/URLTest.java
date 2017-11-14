package indi.wzl.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLTest {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = URLEncoder.encode("我是","UTF-8");
		
		System.out.println(URLDecoder.decode(s));
	}
}
