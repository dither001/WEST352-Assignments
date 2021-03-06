
/**
 * MT Name Client based on Echo Client provided in class.
 * 
 * @author Nick Foster
 */

import java.net.*;
import java.io.*;

public class MultithreadedNameClient {
	public static final int DEFAULT_PORT = 6052;

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: java EchoClient <echo server>");
			System.exit(0);
		}

		BufferedReader networkBin = null; // the reader from the network
		PrintWriter networkPort = null; // the writer to the network
		BufferedReader localBin = null; // the reader from the local keyboard
		Socket sock = null; // the socket

		try {
			sock = new Socket(args[0], DEFAULT_PORT);

			// set up the necessary communication channels
			networkBin = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			localBin = new BufferedReader(new InputStreamReader(System.in));

			/**
			 * a PrintWriter allows us to use println() with ordinary socket I/O. "true"
			 * indicates automatic flushing of the stream. The stream is flushed with an
			 * invocation of println()
			 */
			networkPort = new PrintWriter(sock.getOutputStream(), true);

			/**
			 * Read from the keyboard and send it to the echo server. Quit reading when the
			 * client enters the word "exit"
			 */
			boolean done = false;
			while (!done) {
				String line = localBin.readLine();
				if (line.equals("exit"))
					done = true;
				else {
					networkPort.println(line);
					System.out.println("Server: " + networkBin.readLine());
				}
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (networkBin != null)
				networkBin.close();
			if (localBin != null)
				localBin.close();
			if (networkPort != null)
				networkPort.close();
			if (sock != null)
				sock.close();
		}
	}
}
