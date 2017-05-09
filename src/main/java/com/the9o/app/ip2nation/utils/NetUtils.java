package com.the9o.app.ip2nation.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetUtils {
	
	public static final Logger LOGGER = Logger.getLogger(NetUtils.class.getName());
	
	/**
	 * Given an IPv4 network address as string return its numeric
	 * representation 
	 * 
	 * @param ipAddress
	 * @return an integer value of ipAddress
	 */
	public static long inetAton(String ipAddress) {
		long value = 0;
		try {
			InetAddress ad = InetAddress.getByName(ipAddress);
			int iad = ByteBuffer.wrap(ad.getAddress()).getInt();
			value = iad & (-1L >>> 32);
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE, "Failed to convert IP", e);
		}
		return value;
	}
	
	/**
	 * Given an IP numeric value return its IPv4 string
	 * representation 
	 * 
	 * @param ipAddress
	 * @return string value of ipAddress
	 */
	public static String inetNtoa(String ipAddress) {
		try {
			InetAddress ad = InetAddress.getByName(ipAddress);
			return ad.getHostAddress();
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE, "Failed to return from numeric value", e);
		}
	    return null;
	}
	
	
	

}
