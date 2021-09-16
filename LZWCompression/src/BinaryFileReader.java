import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
public class BinaryFileReader {
	
	public BinaryFileReader (String filename) {
		try {
			InputStream inputStream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
