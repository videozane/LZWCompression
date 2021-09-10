import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;


public class LZWCompression {
	private HashMap<String, Integer> table;
	private static String inputFileName;
	private ArrayList<Integer> output;
	private StringBuilder outputString;
	
	public LZWCompression(String inputFileName) throws IOException{
		table=new HashMap<String,Integer>(); //table of values
		this.inputFileName=inputFileName;
		output=new ArrayList<Integer>();
		outputString= new StringBuilder("");
		
		for(int i=0;i<256;i++){
			table.put(""+(char)i,i);
		}
		compress(readFile());
		output();
	}
	
	public static String readFile() {
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
			System.out.println (e);
		}
		return contents;
	}

	public void compress(String input) throws IOException{
		
		String c=""; //current String
		String cn=""; //current + next
		int tableValue=256;
		
		for(char n:input.toCharArray()) { //n = next character
			cn=c+n;
			if(table.containsKey(cn)) { //since exists, don't put into dictionary yet. move on.
				c=cn;
			}else {
				output.add(table.get(c));
				table.put(cn,tableValue++); //adding the to table
				c=""+n;
			}
		}
		if (!c.equals("")) {
			output.add(table.get(c));
		}
	}
	
	public void output() throws IOException {
		TestBin printer = new TestBin();
		for(int joe: output){
			String result = Integer.toBinaryString(joe);
			String s = String.format("%9s", result).replaceAll(" ", "0");
			outputString.append(s);
		}
		printer.toFile(printer.fromAscii(outputString.toString().toCharArray()));
	}
	
	public static void main (String [] args) throws IOException {
		LZWCompression w = new LZWCompression("lzw-file2.txt");
	}
}
