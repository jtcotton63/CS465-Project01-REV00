/**
 * Created by josepher on 1/12/16.
 */
public class WordHelper {

    public static Word[] toWordArray(String s) {
        int numWords = s.length() / 8;
        if(!((numWords == 4) || (numWords == 6) || (numWords == 8)))
            throw new IllegalArgumentException("Input string length incorrect");

        Word[] wordArray = new Word[numWords];
//        String[] sArray = s.split("(?<=\\G.{1})"); // http://stackoverflow.com/questions/3760152/split-string-to-equal-length-substrings-in-java
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

    public static Word xor(Word a, int b) {
        Word intToWord = new Word();
        for(int i = 0; i < 8; i++) {
            int right = b % 10;
            intToWord.setNibbleAt(i, right);
            b /= 10;
        }
        return xor(a,intToWord);
    }
}
