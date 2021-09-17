import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestingFileWriter {

	
	public String binnify (int toBeBin, int byteSize) {
		String binaryVersion = Integer.toBinaryString(toBeBin);
		StringBuilder bob = new StringBuilder();
		bob.append(binaryVersion);
		while (bob.length()< byteSize) {
			bob.insert(0, "0");
		}
		return bob.toString();
	}
	
	
	public TestingFileWriter (){
		Path path = Paths.get("output.bin");		
		byte[] data = new byte[6];
		data [0] = (byte)48;
		data [1] = (byte)152;
		data [2] = (byte)140;
		data [3] = (byte)112;
		data [4] = (byte)8;
		data [5] = (byte)0;
	    
		try {
            Files.write(path, data);    // Java 7+ only
            System.out.println("Successfully written data to the file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void main (String [] args) {
		TestingFileWriter tester = new TestingFileWriter();
	}

}
