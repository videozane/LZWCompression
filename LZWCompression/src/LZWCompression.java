import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;


public class LZWCompression {
	private HashMap<String, Integer> table; //integers associated with characters
	private static String inputFileName;
	private ArrayList<Integer> outputIntegers; //arraylist of dictionary values associated with characters
	private StringBuilder outputString; //in order to store all binary in one object
	
	public LZWCompression(String inputFileName) throws IOException{
		table=new HashMap<String,Integer>(); //table of values
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
	
	public void output() throws IOException {
		TestBin printer = new TestBin();
		for(int val: outputIntegers){ //for loop in order to construct one long string of binary representing all dictionary values used
			String result = Integer.toBinaryString(val);
			String s = String.format("%9s", result).replaceAll(" ", "0");
			outputString.append(s);
		}
		printer.toFile(printer.fromAscii(outputString.toString().toCharArray())); //output to file by calling on testbin class.
	}
	
	public static void main (String [] args) throws IOException { //main to test that it works.
		LZWCompression w = new LZWCompression("lzw-file3.txt");
	}
}
