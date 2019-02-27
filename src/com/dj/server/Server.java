package com.dj.server;

import java.io.File;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * <p>Title: Web Server </p>
 * <p>Description:
 *    This class is the Entry point for our web server
 * </p>
 */
public final class Server implements Runnable {

	private ServerSocket server;
	private ExecutorService threadPool;
	private int threadsLimit;
	private String webroot;
	private int port;
	protected static String serverHome;
	protected static Logger LOGGER;

	public Server(int port, String webRoot, int maxThreads) {
		this.port = port;
		this.threadsLimit = maxThreads;
		this.webroot = webRoot;
	}

	public static void main(String[] args) {

		File f = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		serverHome = f.getParentFile().getParent();
		ConfigXMLParser config = new ConfigXMLParser();
		Map<String, String> configMap = config.getConfigData(serverHome);
		setLogger();
		Server server = new Server(Integer.parseInt(configMap.get("port")), configMap.get("webroot"), Integer.parseInt(configMap.get("maxthread")));

		new Thread(server).run();
	}

	private static void setLogger() {
		try {
			Handler logHandler = new FileHandler(serverHome + "/logs/log.txt");
			logHandler.setFormatter(new SimpleFormatter());
			LOGGER = Logger.getLogger("com.dj.server.Server");
			LOGGER.addHandler(logHandler);
		} catch (Exception e) {
			System.out.println("Unable to initiate Logger. Check Directory permission");
		}
	}

	@Override
	public void run() {
		try {

			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(threadsLimit);
		} catch (Exception e) {
			LOGGER.info("Cannot listen on port " + port);
			System.exit(1);
		}
		LOGGER.info("Running server on the port " + port + 
				" with web root folder \"" + webroot + "\" and " + threadsLimit + " threads limit.");

		while(!Thread.interrupted()) {
			try {
				threadPool.execute(new Thread(new Connection(server.accept(),this)));
			} catch (Exception e) {
				LOGGER.info("Cannot accept client");
			}
		}
	}


	public String getWebroot() {
		return webroot;
	}
}
