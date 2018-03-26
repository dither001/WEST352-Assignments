
/*
 * Multi-threaded Name Server based on EchoServer & DNSLookup provided in class.
 * 
 * @Author Nick Foster
 */

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class MultithreadedNameServer {
	private static final int DEFAULT_PORT = 6052;
	private static final int BUFFER_SIZE = 1024;

	// construct a thread pool for concurrency
	private static final Executor exec = Executors.newCachedThreadPool();

	public static void main(String[] args) throws IOException {
		ServerSocket sock = null;

		try {
			// establish the socket
			sock = new ServerSocket(DEFAULT_PORT);

			while (true) {
				Socket client = sock.accept();
				/**
				 * now listen for connections and service the connection in a separate thread.
				 */


				// anonymous Thread/Connection class
				Thread t = new Thread() {
//					Handler handler = new Handler();
					public void run() {
						try {
							byte[] buffer = new byte[BUFFER_SIZE];
							InputStream fromClient = null;
							OutputStream toClient = null;

							try {
								/**
								 * get the input and output streams associated with the socket.
								 */
								fromClient = new BufferedInputStream(client.getInputStream());
								toClient = new BufferedOutputStream(client.getOutputStream());

								/** continually loop until the client closes the connection */
								while ((fromClient.read(buffer)) != -1) {
									String request = new String(buffer).trim();
									System.out.println(request);

									if (request.equals(null)) {
										System.err.println("Usage: java DNSLookUp <IP name>");
										System.exit(0);
									}

									String hostAddress;
									try {
										hostAddress = new String(InetAddress.getByName(request).getHostAddress() + "\n");
										System.out.println(InetAddress.getByName(request).getHostAddress() + "\n");
										toClient.write(hostAddress.getBytes());
									} catch (UnknownHostException uhe) {
										System.err.println("Unknown host: " + request + "\n");
									}

									toClient.flush();
								}
							} catch (IOException ioe) {
								System.err.println(ioe);
							} finally {
								// close streams and socket
								if (fromClient != null)
									fromClient.close();
								if (toClient != null)
									toClient.close();
							}
						} catch (java.io.IOException e) {
							System.err.println(e);
						}
					}
				};

				exec.execute(t);
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (sock != null)
				sock.close();
		}
	}

	/*
	 * PRIVATE HANDLER CLASS
	 */
//	private static class Handler {
//		static final int BUFFER_SIZE = 1024;
//
//		/**
//		 * this method is invoked by a separate thread
//		 */
//		void process(Socket client) throws java.io.IOException {
//			byte[] buffer = new byte[BUFFER_SIZE];
//			InputStream fromClient = null;
//			OutputStream toClient = null;
//
//			try {
//				/**
//				 * get the input and output streams associated with the socket.
//				 */
//				fromClient = new BufferedInputStream(client.getInputStream());
//				toClient = new BufferedOutputStream(client.getOutputStream());
//				int numBytes;
//
//				/** continually loop until the client closes the connection */
//				while ((numBytes = fromClient.read(buffer)) != -1) {
//					String request = new String(buffer).trim();
//					System.out.println(request);
//
//					if (request.equals(null)) {
//						System.err.println("Usage: java DNSLookUp <IP name>");
//						System.exit(0);
//					}
//
//					InetAddress hostAddress;
//					try {
//						hostAddress = InetAddress.getByName(request);
//						System.out.println(InetAddress.getByName(request).getHostAddress() + "\n");
//						toClient.write(hostAddress.getAddress());
//					} catch (UnknownHostException uhe) {
//						System.err.println("Unknown host: " + request + "\n");
//					}
//
//					toClient.flush();
//				}
//			} catch (IOException ioe) {
//				System.err.println(ioe);
//			} finally {
//				// close streams and socket
//				if (fromClient != null)
//					fromClient.close();
//				if (toClient != null)
//					toClient.close();
//			}
//		}
//	}

}
