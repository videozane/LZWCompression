import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Decoder {
	private int sizeOfByte;
	private HashMap<Integer, String> dictionary;
	private byte[] fileContents;
	private int sizeOfDictionary;
	
	
	public Decoder(String fileName, int sizeOfByte) {
		this.sizeOfByte = sizeOfByte;
		sizeOfDictionary = 0;
		//code below puts in normal chars into the table
		dictionary = new HashMap<Integer, String> ();
		for(int index = 0; index < 256; index++) {
			char newChar = (char)index; //changes the index of ASCII to a character
			String toBeAdded = "" + newChar; //changes the character to a string
			dictionary.put(index, toBeAdded); //puts string into HashMap with corresponding spot in table
			sizeOfDictionary++;
		}
		
		
		
		/**
		 * puts all of the bytes of the file into an array of bytes
		 */
		Path path = Paths.get(fileName);
		System.out.println(path.toAbsolutePath());
		try {
			fileContents =  Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/**
		 * turns array of bytes into one long string of 0s and 1s
		 */
		StringBuilder strBuilder = new StringBuilder ();
		for (int i = 0; i < fileContents.length; i ++) {
			byte b1 = fileContents [i];
			strBuilder.append(String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0'));
		}
		
		
		
		System.out.println(decode (strBuilder.toString()));
		
	}
	
	
	/**
	 * 
	 * 
	 * @param toBeDecoded is the String that was created by the binary file
	 * @return the decoded message
	 */
	public String decode (String toBeDecoded) {
		
		
		StringBuilder toRet = new StringBuilder ();
		
		/**
		 * parses String into proper size of each byte
		 */
		ArrayList<Integer> parsedInts = new ArrayList<Integer>();
		for (int i = 0; i < toBeDecoded.length()-sizeOfByte; i+=sizeOfByte) {
			String toBeChanged = toBeDecoded.substring(i,i+sizeOfByte);
			parsedInts.add(binStringToInteger(toBeChanged));
		}
		

		String current = "";
		String next = "";
		for (int i = 0; i < parsedInts.size()-1; i++) {
			current = dictionary.get(parsedInts.get(i));
			next= dictionary.get(parsedInts.get(i+1));
			String firstOfNext = next.charAt(0) + "";
			if (!dictionary.containsValue(current + firstOfNext)) {
				dictionary.put(sizeOfDictionary, current + firstOfNext);
				sizeOfDictionary++;
			}
			toRet.append(current);
		}
		toRet.append(next);
		
		return toRet.toString();
		
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
	
	public static void main (String [] args) {
		Decoder tester = new Decoder ("output.bin", 9);
	}
}
