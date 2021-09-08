import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;


public class LZWCompression {
	private HashMap<String, Integer> table;
	private static String inputFileName;
	private ArrayList<Integer> output;
	
	public LZWCompression(String inputFileName) throws IOException{
		table=new HashMap<String,Integer>(); //table of values
		this.inputFileName=inputFileName;
		output=new ArrayList<Integer>();
		
		for(int i=0;i<256;i++){
			table.put(""+(char)i,i);
		}
		compress(readFile());
	}
	
	public static String readFile() {
		String contents=""; //contents of input file
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader((new FileInputStream(inputFileName))))){
			buffer.mark(300);
			while(buffer.readLine()!=null) {
				buffer.reset();
				contents+=buffer.readLine();
				buffer.mark(300);
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
            
		
		//output to file
		FileWriter writer = new FileWriter("output.txt"); 
		for(int val: output) {
		  writer.write(""+val+", ");
		}
		writer.close();
	}
	
	
	public static void main (String [] args) throws IOException {
		LZWCompression c = new LZWCompression("lzw-file1.txt");
	}
}
