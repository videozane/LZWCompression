import java.util.*;
import java.io.FileWriter;
import java.io.IOException;


public class LZWCompression {
	private HashMap<String, Integer> table;
	private String input;
	private ArrayList<Integer> output;
	
	public LZWCompression(String input){
		table=new HashMap<String,Integer>(); //table of values
		this.input=input;
		output=new ArrayList<Integer>();
		
		for(int i=0;i<256;i++){
			table.put(""+(char)i,i);
		}
	}
	
	public void compress() throws IOException{
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
		LZWCompression c = new LZWCompression("TOBEORNOTTOBEORTOBEORNOT");
		c.compress();
	}
}
