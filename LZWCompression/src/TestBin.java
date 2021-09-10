import java.io.FileOutputStream;
import java.io.IOException;

public class TestBin {
	
	private static final char[] EMPTY_CHAR_ARRAY = new char[0];

	  /** Empty byte array. */
	  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

	  /** Mask for bit 0 of a byte. */
	  private static final int BIT_0 = 1;

	  /** Mask for bit 1 of a byte. */
	  private static final int BIT_1 = 0x02;

	  /** Mask for bit 2 of a byte. */
	  private static final int BIT_2 = 0x04;

	  /** Mask for bit 3 of a byte. */
	  private static final int BIT_3 = 0x08;

	  /** Mask for bit 4 of a byte. */
	  private static final int BIT_4 = 0x10;

	  /** Mask for bit 5 of a byte. */
	  private static final int BIT_5 = 0x20;

	  /** Mask for bit 6 of a byte. */
	  private static final int BIT_6 = 0x40;

	  /** Mask for bit 7 of a byte. */
	  private static final int BIT_7 = 0x80;

	  private static final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };
	  
	
	public  byte[] fromAscii(char[] ascii) {
	    byte[] l_raw = new byte[ascii.length >> 3];
	    for (int ii = 0, jj = ascii.length - 1; ii < l_raw.length; ii++, jj -= 8) {
	      for (int bits = 0; bits < BITS.length; ++bits) {
	        if (ascii[jj - bits] == '1') {
	          l_raw[ii] |= BITS[bits];
	        }
	      }
	    }
	    return l_raw;
	}
	
	public void toFile(byte[] data) throws IOException {
		FileOutputStream fos = new FileOutputStream("output.bin");
		fos.write(data);
	}
}