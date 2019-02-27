package com.dj.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * This class has the logic on validating and building on the request object which further gets 
 * used to fetch the url and passing as an argument to httpresponse
 * 
 */
public class HttpRequest {

	private String method;
	private String url;
	private String protocol;
	private TreeMap<String, String> headers = new TreeMap<>();
	private List<String> body = new ArrayList<>();
	private static Logger LOGGER = Server.LOGGER;

	private HttpRequest() {};

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public String getProtocol() {
		return protocol;
	}

	public static HttpRequest httpRequest(InputStream in) {

		try {

			HttpRequest request = new HttpRequest();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();

			if(line==null)  {
				throw new IOException("Server only accepts http requests.");
			}

			String[] requestLine = line.split(" ", 3);

			if(requestLine.length!=3) {
				throw new IOException("Cannot parse request line from \"" + line + "\"");
			}

			if(!requestLine[2].startsWith("HTTP")) {
				throw new IOException("Server only accepts HTTP Requests.");
			}

			request.method = requestLine[0];
			request.url = requestLine[1];
			request.protocol = requestLine[2];

			line = reader.readLine();
			while(line!=null && !line.equals("")) {
				String[] header = line.split(": ", 2);
				if(header.length!=2)
					throw new IOException("Cannot parse header from \"" + header + "\"");
				else
					request.headers.put(header[0], header[1]);

				line=reader.readLine();
			}

			while(reader.ready()) {
				line = reader.readLine();
				request.body.add(line);
			}

			return request;

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

		return null;

	}

}

