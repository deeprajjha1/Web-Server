package com.dj.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * The complete logic for building on request and response is built in this Class.
 * Hence to make it thread safe we will use Reentrant lock with fairness enabled and will
 * wrap it around the thread run() method logic which is channeling the request and response flow.
 */
public class Connection implements Runnable {

	private Server server;
	private Socket client;
	private InputStream in;
	private OutputStream out;
	private static Logger LOGGER = Server.LOGGER;
	private ReentrantLock lock = new ReentrantLock(true);

	public Connection(Socket cl, Server s) {
		client = cl;
		server = s;
	}



	@Override
	public void run() {
		try {
			lock.lock();
			File f = null;
			in = client.getInputStream();
			out = client.getOutputStream();

			HttpResponse response;
			HttpRequest request = HttpRequest.httpRequest(in);
																																						
			if(request!=null) {
				LOGGER.info("Request for " + request.getUrl() + " is being processed " +
						"by socket at " + client.getInetAddress() +":"+ client.getPort());


				String method = request.getMethod();
				if(isMethodGet(method)) {
					//Just display the default page
					if(request.getUrl().equalsIgnoreCase("/")) {
						f = new File("temp.txt");
						OutputStream os = new FileOutputStream(f); 
						os.write("Welcome to HTTP Web Server.".getBytes());
						os.close();
					}
					else
						f = new File(server.serverHome + File.separator + server.getWebroot() + File.separator + request.getUrl());

					response = new HttpResponse(StatusCode.getStatusMessage(200)).processRequestedFile(f);


				}
				else {
					if(isMethodHead(method)) {
						response = new HttpResponse(StatusCode.getStatusMessage(204)).removeBody();
					}

					else {
						response = new HttpResponse(StatusCode.getStatusMessage(501));
					}
				}
				respond(response);
			}
			in.close();
			out.close();

		} catch (Exception e) {
			LOGGER.info("Error in clients IO.");
		}
		finally {
			try {
				lock.unlock();
				client.close();
			} catch (IOException io) {
				LOGGER.info("Error while closing connection");
			}
		}
	}

	private boolean isMethodGet(String method) {

		return method.equals(HttpMethod.GET.name()) ? true : false;
	}

	private boolean isMethodHead(String method) {

		return method.equals(HttpMethod.HEAD.name()) ? true : false;
	}

	public void respond(HttpResponse response) {
		String toSend = response.toString();
		PrintWriter writer = new PrintWriter(out);
		writer.write(toSend);
		writer.flush();
	}
}
