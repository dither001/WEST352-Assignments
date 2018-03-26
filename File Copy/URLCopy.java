import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class URLCopy {
	public static void main(String[] args) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		URL url = null;

		try {
			url = new URL(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			input = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			output = new FileOutputStream(args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (input.available() > 0) {
			output.write(input.read());
		}

		input.close();
		output.close();
	}
}
