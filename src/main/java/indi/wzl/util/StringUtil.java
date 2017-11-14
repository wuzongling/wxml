package indi.wzl.util;

/**
 * 字符串处理工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	/**
	 * 转换首字母大写
	 * @param str
	 * @return
	 */
	public static String converFirstCapital(String str){
		char[] b = str.toCharArray();
		   if (b[0] >= 97 && b[0] <= 122) {
		    b[0] = (char) (b[0] - 32);
		   }
		   return new String(b);
	}
	
	/**
	 * 是否为空
	 * @param str
	 * @return
	 */
	public static Boolean isNull(String str){
		boolean b = false;
		if(("".equals(str)||""==str)||str == null){
			b = true;
		}
		return b;
	}
}
