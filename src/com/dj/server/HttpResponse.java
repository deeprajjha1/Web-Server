package com.dj.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;


/**
 * This class is invoked only when we have gone through all the checks on request being valid.
 * Essentially we are processing the uri and building the response
 * @author djha
 *
 */
public class HttpResponse {

	private String status;

	private TreeMap<String, String> headers = new TreeMap<String,String>();

	private byte[] body = null;
	
	public final static String protocol = "HTTP/1.1";

	public HttpResponse(String status) {
		this.status = status;
		setDate(new Date());
	}


	/**
	 * @param f File requested by the client from the server
	 * @return Response built in the format of headers and body as applicable.
	 * @throws IOException
	 */

	public HttpResponse processRequestedFile(File f) throws IOException {
		if(f.isFile()) {
			FileInputStream reader = new FileInputStream(f);
			int length = reader.available();
			body = new byte[length];	
			reader.read(body);
			reader.close();

			setContentLength(length);
			setContentType(f);
			
			return this;
		}
		else {
			return new HttpResponse(StatusCode.getStatusMessage(404)).
					withHtmlBody("<html> <body> File " + f.getName() + " is Not found </body> </html> ");
		}
	}

	public HttpResponse withHtmlBody(String msg) {

		setContentLength(msg.getBytes().length);
		setContentType(ContentType.HTML);
		body = msg.getBytes();
		return this;
	}

	protected HttpResponse removeBody() {
		body = null;
		return this;
	}

	private void setDate(Date date) {
		headers.put("Date", date.toString());
	}

	private void setContentType(String type) {
		headers.put("Content-Type", type);
	}

	private void setContentType(File requestedFile) {
		if(requestedFile.getName().endsWith("htm") || requestedFile.getName().endsWith("html"))
			setContentType(ContentType.HTML);
		else 
			if(requestedFile.getName().endsWith("jpg") || requestedFile.getName().endsWith("jpeg"))
				setContentType(ContentType.JPG);
			else 
				if(requestedFile.getName().endsWith("xml"))
					setContentType(ContentType.XML);
				else 
					if(requestedFile.getName().endsWith("gif"))
						setContentType(ContentType.GIF);
					else
						setContentType(ContentType.TEXT);
	}

	private void setContentLength(long length) {
		headers.put("ContentLength",String.valueOf(length));
	}

	@Override
	public String toString() {
		String result = protocol + " " + status + "\n";

		for(String key : headers.descendingKeySet()) {

			result += key + " " + headers.get(key) + "\n";
		}
		result += "\r\n";		

		if(body!=null)
			result += new String(body);

		return result;
	}


	public TreeMap<String, String> getHeaders() {
		return headers;
	}
	
	public String getStatus() {
		return status;
	}
}