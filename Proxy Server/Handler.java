
/**
 * Handler class for ProxyServer. 
 * Based on multithreaded echo server provided in class.
 * 
 * @author Nick Foster - February 2018
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Handler {
	public static final int BUFFER_SIZE = 1024;

	public void process(Socket client) throws java.io.IOException {
		byte[] bufferIn = new byte[BUFFER_SIZE];
		byte[] bufferOut = new byte[BUFFER_SIZE];
		InputStream fromClient = null;
		OutputStream toClient = null;
		InputStream fromServer = null;
		DataOutputStream toServer = null;

		// TODO
		Socket server;

		try {
			fromClient = new BufferedInputStream(client.getInputStream());
			toClient = new BufferedOutputStream(client.getOutputStream());
			int bytesOut;

			while ((bytesOut = fromClient.read(bufferIn)) != -1) {
				String command = new String(bufferIn).trim();
				// TODO - testing console print
//				System.out.println(command);
				String request = command.substring(0, 3);

				if (request.equals("GET")) {
					int originStop = command.indexOf("HTTP") - 1;
					String originHost = command.substring(5, originStop); 
					
					int resourceStart = originHost.indexOf("/");
					String resource = "/";
					if (resourceStart != -1) {
						resource = originHost.substring(resourceStart);
						originHost = originHost.substring(0, resourceStart);
					}

					// TODO - testing console print
//					System.out.println("\nOriginHost: " + originHost);
//					System.out.println(resourceStart);
//					System.out.println("Resource: " + resource + "\n");

					server = new Socket(originHost, 80);
					fromServer = new BufferedInputStream(server.getInputStream());
					toServer = new DataOutputStream(server.getOutputStream());

					request = String.format("GET %s HTTP/1.1\r\nHost: %s\r\nConnection: close\r\n\r\n", resource,
							originHost);
					// TODO - testing console print
//					System.out.println("\n\n" + request + "\n");
					toServer.write(request.getBytes());

					int bytesIn;
					while ((bytesIn = fromServer.read(bufferOut)) != -1) {
						toClient.write(bufferOut);
						toClient.flush();
					}
				} else {
					client.close();
				}
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (fromClient != null)
				fromClient.close();
			if (toClient != null)
				toClient.close();
		}
	}
}
