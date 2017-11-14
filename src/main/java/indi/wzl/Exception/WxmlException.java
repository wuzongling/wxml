package indi.wzl.Exception;

public class WxmlException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4419948595652548357L;

	public WxmlException(String message, Throwable e){
		super(message,e);
	}
	
	public WxmlException(String message){
		super(message);
	}
}
