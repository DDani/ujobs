package org.ujobs.service.exceptions;

public class DataUpdateException extends Exception {


	private static final long serialVersionUID = -7456783681971797794L;
	
	public DataUpdateException(String string, String reason) {
		super(string + " reason:" + reason);
	}

}
