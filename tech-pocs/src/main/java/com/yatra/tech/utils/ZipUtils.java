package com.yatra.tech.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

import org.castor.core.util.Base64Decoder;

public class ZipUtils {
    
    public static String getUnZipped(InputStream in)
    {
    	String retStr="";
		try {
			InputStream gzipStream = new GZIPInputStream(in);
			Reader decoder = new InputStreamReader(gzipStream);
			BufferedReader buffered = new BufferedReader(decoder);
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = buffered.readLine()) != null) {
				sb.append(line);
			}
			retStr = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retStr;
    }
    
    public static void main(String[] args) {
		String zippedXml = "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";
		System.out.println(getUnZippedBase64(zippedXml));
    }
    
    public static String getUnZippedBase64(String str){
    	if(str != null)
    		if(str.length()>1)
    			return getUnZipped(new ByteArrayInputStream(Base64Decoder.decode(str)));
    		else return str;
    	else
    		return str;
	}
	public static byte[] fastCompression( String str){
		if (str == null || str.length() == 0) {
			return null;
		}
		byte[] data = str.getBytes();
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_SPEED);
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); // returns the generated code... index
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		}catch (Exception e){

		}
		byte[] output = outputStream.toByteArray();
		return output;
	}

	public static String fastDecompress(byte[] data)  {
		try {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
			byte[] output = outputStream.toByteArray();
			return new String(output);
		} catch (Exception e) {
			return null;
		}
	}

	    
	public static byte[] compressByteArray(byte[] data) throws IOException {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				data.length);

		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); // returns the generated
													// code... index
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();

		deflater.end();
		return output;
	}
    
	public static byte[] decompressByteArray(byte[] data) throws IOException,
			DataFormatException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();

		inflater.end();
		return output;
	}
}
