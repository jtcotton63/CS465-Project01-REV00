import java.util.ArrayList;
import java.util.List;

public class MixColumnsHelper {

    private static String mixColsTransMtrxString = "02030101" +
            "01020301" +
            "01010203" +
            "03010102";
    public static Word[] mixColsTransMtrx = WordHelper.toWordArray(mixColsTransMtrxString);
    private static String invMixColsTransMtrxString = "0e0b0d09" +
            "090e0b0d" +
            "0d090e0b" +
            "0b0d090e";
    public static Word[] invMixColsTransMtrx = WordHelper.toWordArray(invMixColsTransMtrxString);


    public static State mixColumns(State state, Word[] matrix) {
        State newState = new State();
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 8; row += 2) {
                int byteVal = ffMultiply(state.getColumn(col), matrix[row/2]);
                newState.getColumn(col).setNibbleAt(row, WordHelper.getLeftNibble(byteVal));
                newState.getColumn(col).setNibbleAt(row + 1, WordHelper.getRightNibble(byteVal));
            }
        }
        return newState;
    }

    private static int ffMultiply(Word w1, Word w2) {
        List<Integer> toBeXORed = new ArrayList<>();
        for(int i = 0; i < 8; i+=2) {
            int temp = ffMultiply(w1.getNibbleAt(i), w1.getNibbleAt(i+1), w2.getNibbleAt(i+1));
            toBeXORed.add(temp);
        }
        return xorAll(toBeXORed);
    }

    private static int ffMultiply(int n1, int n2, int multiplier) {
        int byteVal = WordHelper.getByteValue(n1, n2);

        if(byteVal == 0 || multiplier == 0)
            return 0;
        if(multiplier == 1)
            return byteVal;

        List<Integer> toBeXORed = new ArrayList<>();
        if(multiplier % 2 ==1)
            toBeXORed.add(byteVal);
        multiplier = multiplier >> 1;

        int temp = byteVal;
        for(int i = 1; i < 8; i++) {
            temp = xTimes(temp);
            if(multiplier % 2 == 1)
                toBeXORed.add(temp);
            multiplier = multiplier >> 1;
        }

        return xorAll(toBeXORed);
    }

    private static int xTimes(int value) {
        int highbit = value & 0x80;
        value = value << 1;
        if(highbit == 0x80) {
            value = value ^ 0x1b;
            value = value & 0x0ff; // to get rid of leading 1
        }
        return value;
    }

    private static int xorAll(List<Integer> toBeXORed) {
        if(toBeXORed.size() == 0)
            throw new IllegalArgumentException("toBeXORed has size 0");

        int xorSum = toBeXORed.get(0);
        for(int i = 1; i < toBeXORed.size(); i++) {
            xorSum = xorSum ^ toBeXORed.get(i);
        }

        return xorSum;
    }

}