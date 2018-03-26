
/**
 * Connection class for ProxyServer. 
 * Based on multithreaded echo server provided in class.
 * 
 * @author Nick Foster - February 2018
 */

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
	private Socket client;
	private static Handler handler = new Handler();

	public Connection(Socket client) {
		this.client = client;
	}

	/**
	 * This method runs in a separate thread.
	 */
	public void run() {
		try {
			handler.process(client);
		} catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
	}
}
