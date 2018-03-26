
/**
 * A proxy server listening on port 8080. 
 * Based on multithreaded echo server provided in class.
 * 
 * @author Nick Foster - February 2018
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProxyServer {
	public static final int DEFAULT_PORT = 8080;
	private static final Executor exec = Executors.newCachedThreadPool();

	public static void main(String[] args) throws IOException {
		ServerSocket sock = null;

		try {
			sock = new ServerSocket(DEFAULT_PORT);
			System.out.println("Server bound at port " + sock.getLocalPort());

			while (true) {
				Runnable task = new Connection(sock.accept());
				exec.execute(task);
			}
		} catch (IOException ioe) {
		} finally {
			if (sock != null)
				sock.close();
		}
	}
}
