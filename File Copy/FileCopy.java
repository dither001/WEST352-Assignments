import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {
	public static void main(String[] args) throws IOException {
		InputStream input = null;
		OutputStream output = null;

		try {
			input = new FileInputStream(args[0]);
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
