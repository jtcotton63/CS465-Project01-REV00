public class WordHelper {

    public static int getByteValue(int nibble1, int nibble2) {
        return (nibble1 << 4) + nibble2;
    }

    public static int getLeftNibble(int byteVal) {
        return (byteVal & 0xf0) >> 4;
    }

    public static int getRightNibble(int byteVal) {
        return byteVal & 0x0f;
    }

    public static Word[] reverseWordArray(Word[] in, int ROUNDS) {
        Word[] out = new Word[in.length];

        if(!(ROUNDS == 11 || ROUNDS == 13 || ROUNDS == 15))
            throw new IllegalArgumentException("ROUNDS cannot be " + ROUNDS + "; can only be 11, 13, or 15");

        int inIdx = 0;
        for(int i = (ROUNDS*4) - 1; i >= 0; i-=4) {
            out[i-3] = in[inIdx];
            out[i-2] = in[inIdx+1];
            out[i-1] = in[inIdx+2];
            out[i] = in[inIdx+3];
            inIdx +=4;
        }

        return out;
    }

    public static Word[] toWordArray(String s) {
        int numWords = s.length() / 8;
        if(!((numWords == 4) || (numWords == 6) || (numWords == 8)))
            throw new IllegalArgumentException("Input string length incorrect");

        Word[] wordArray = new Word[numWords];
        char[] sArray = s.toCharArray();

        for(int i = 0; i < sArray.length; i=i+8) {
            Word temp = new Word(
                    HexHelper.getDecimalValueFromHex(sArray[i]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 1]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 2]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 3]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 4]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 5]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 6]),
                    HexHelper.getDecimalValueFromHex(sArray[i + 7])
            );
            wordArray[i/8] = temp;
        }

        return wordArray;
    }

    public static Word xor(Word a, Word b) {
        Word rtn = new Word();
        for(int i = 0; i < 8; i++) {
            rtn.setNibbleAt(i, xorNibble(a.getNibbleAt(i), b.getNibbleAt(i)));
        }
        return rtn;
    }

    private static int xorNibble(int n1, int n2) {
        return n1 ^ n2;
    }
}
