package com.dj.server.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.dj.server.HttpMethod;
import com.dj.server.HttpRequest;
import com.dj.server.HttpResponse;
import com.dj.server.StatusCode;

public class HttpResponseTest {

	@Test
	public void HeadRequest() throws IOException {
		File file = new File("temp.txt");
		OutputStream os = new FileOutputStream(file); 
		os.write("HEAD / HTTP/1.1\n\n".getBytes());
		HttpResponse res = new HttpResponse(StatusCode.getStatusMessage(200)).processRequestedFile(file);
		os.close();
		Assert.assertTrue(res.getHeaders().size() > 0);
		Assert.assertEquals(StatusCode.getStatusMessage(200), res.getStatus());
	}

	@Test
	public void GetRequest() throws IOException {
		File file = new File("temp.txt");
		OutputStream os = new FileOutputStream(file); 
		os.write("GET / HTTP/1.1\n\n".getBytes());
		HttpResponse res = new HttpResponse(StatusCode.getStatusMessage(200)).processRequestedFile(file);
		os.close();
		Assert.assertTrue(res.getHeaders().size() > 0);
		Assert.assertEquals(StatusCode.getStatusMessage(200), res.getStatus());
	}
}
