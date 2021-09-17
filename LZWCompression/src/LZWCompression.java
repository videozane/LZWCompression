import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LZWCompression {
	private HashMap<String, Integer> table; //integers associated with characters
	private static String inputFileName;
	private ArrayList<Integer> outputIntegers; //arraylist of dictionary values associated with characters
	private StringBuilder outputString; //in order to store all binary in one object
	private int byteSize;
	
	public LZWCompression(String inputFileName, int byteSize) throws IOException{
		table=new HashMap<String,Integer>(); //table of values
		this.byteSize = byteSize;
		this.inputFileName=inputFileName;
		outputIntegers=new ArrayList<Integer>();
		outputString= new StringBuilder("");
		
		for(int i=0;i<256;i++){
			table.put(""+(char)i,i);
		}
		compress(readFile());
		output();
	}
	
	public static String readFile() { //reads in file
		String contents=""; //contents of input file
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader((new FileInputStream(inputFileName))))){
			buffer.mark(1000);
			while(buffer.readLine()!=null) {
				buffer.reset();
				contents+=buffer.readLine();
				buffer.mark(1000);
			}
		}
		catch (IOException e){
		}
		return contents;
	}

	public void compress(String input) throws IOException{ //creates arraylist of dictionary values encoded.
		
		String c=""; //current String
		String cn=""; //current + next
		int tableValue=256;
		
		for(char n:input.toCharArray()) { //n = next character
			cn=c+n;
			if(table.containsKey(cn)) { //since exists, don't put into dictionary yet. move on.
				c=cn;
			}else {
				outputIntegers.add(table.get(c));
				table.put(cn,tableValue++); //adding to table/dictionary
				c=""+n;
			}
		}
		if (!c.equals("")) {
			outputIntegers.add(table.get(c));
		}
	}
	
	public String formatBinary (int num, int byteSize) {
		String binaryVersion = Integer.toBinaryString(num);
		StringBuilder bob = new StringBuilder();
		bob.append(binaryVersion);
		while (bob.length()< byteSize) {
			bob.insert(0, "0");
		}
		return bob.toString();
	}
	
	public static int binStringToInteger (String input){

    	String binaryString = input;
    	double convertedDouble = 0;
        for (int i = 0; i < binaryString.length(); i++) {

            if (binaryString.charAt(i) == '1') {
                int len = binaryString.length() - 1 - i;
                convertedDouble += Math.pow(2, len);
            }
        }

        int convertedInt = (int) convertedDouble;
    
	return convertedInt;
}
	
	public void output() throws IOException {
		Path path = Paths.get("encodeTest.bin");
		for(int val: outputIntegers){ //for loop in order to construct one long string of binary representing all dictionary values used
			String result = formatBinary(val, byteSize);
			outputString.append(result);
		}
		
		int numOfBytesToBeWritten = outputString.length() / byteSize;
		byte[] binaryToOutput = new byte[numOfBytesToBeWritten+1];
		int counter = 0;
		for (int i = 0; i < outputString.length()-8; i+=8) {
			binaryToOutput [counter] = (byte)Integer.parseInt(outputString.substring(i, i+8),2);
			counter++;
		}
		StringBuilder endOfString = new StringBuilder (outputString.substring(numOfBytesToBeWritten*9));
		while (endOfString.length()< byteSize) {
			endOfString.append("0");
		}
		binaryToOutput [numOfBytesToBeWritten] = (byte)binStringToInteger(endOfString.toString());
		
		try {
            Files.write(path, binaryToOutput);    // Java 7+ only
            System.out.println("Successfully written data to the file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main (String [] args) throws IOException { //main to test that it works.
		LZWCompression w = new LZWCompression("Untitled 1", 9);
	}
}
