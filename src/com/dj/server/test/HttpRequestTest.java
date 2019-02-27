package com.dj.server.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.dj.server.HttpMethod;
import com.dj.server.HttpRequest;

public class HttpRequestTest {

	@Test
	public void doHeadRequest() throws IOException {
		HttpRequest req = HttpRequest.httpRequest(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(HttpMethod.HEAD.name(), req.getMethod());
	}

	@Test
	public void doGetRequest() throws IOException {
		HttpRequest req = HttpRequest.httpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(HttpMethod.GET.name(), req.getMethod());
	}
}
