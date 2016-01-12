/**
 * Created by josepher on 1/11/16.
 * Represents a data type of 4 bytes (32 bits)
 * @formatter:off
 */
public class Word {

    private int[] contents;

    public Word() {
        contents = new int[8];
    }

    /**
     * This function represents 4 bits (1 nibble) in a byte data type.
     * Byte is used bc it has the smallest data range, and the nibble
     * will never get bigger than 0xf.
     */
    public Word(int n1, int n2, int n3, int n4,
                int n5, int n6, int n7, int n8) {
        this();
        contents[0] = n1;
        contents[1] = n2;
        contents[2] = n3;
        contents[3] = n4;
        contents[4] = n5;
        contents[5] = n6;
        contents[6] = n7;
        contents[7] = n8;
    }

    /**
     * Returns the byte at the index of the word
     * Ex word: abcdef89
     *      Calling getByteAt(1) returns the byte value "b"
     * @param idx The index of the byte to be returned
     * @return The byte located at the specified index
     */
    public int getNibbleAt(int idx) {
        return contents[idx];
    }

    public void setNibbleAt(int idx, int nibble) {
        contents[idx] = nibble;
    }

    public String toString() {
        String temp = "";
        for(int i = 0; i < contents.length; i++) {
            temp += HexHelper.getHexValueFromDecimal(contents[i]);
        }
        return temp;
    }
}