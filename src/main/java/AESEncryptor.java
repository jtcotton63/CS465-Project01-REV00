/**
 * Created by josepher on 1/11/16.
 */
public class AESEncryptor {

    static Word[] keySchedule = null;
    static int keyScheduleIdx = 0;
    static State state = null;
    static int nK = -1;
    static int nR = -1;
    static int ROUNDS = -1;

    public static void cipher(String data, String keyAsString) {

        Word[] key = WordHelper.toWordArray(keyAsString);
        nK = key.length;
        nR = detNR();
        ROUNDS = nR+1;
        prepState(data);
        keySchedule = KeyExpander.expandCypherKey(nK, ROUNDS, key);

        // Input round
        addRoundKey();

        // nR - 1 rounds
        for(int round = 1; round < ROUNDS - 1; round++) {
            subBytes();
            shiftRows();
            state = MixColumnsHelper.mixColumns(state);
            System.out.println("STOP HERE");
            addRoundKey();
        }

        // Last rounds
        subBytes();
        shiftRows();
        addRoundKey();
    }

    private static int detNR() {
        if(nK == 4)
            return 10;
        if(nK == 6)
            return 12;
        if(nK == 8)
            return 14;

        throw new IllegalArgumentException("nK is not 4, 6, or 8");
    }

    // This is a duplicate function; should be combined with WordHelper.toWordArray
    private static void prepState(String data) {
        state = new State();
        int dataIdx = 0;
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 8; y++) {
                String sub = data.substring(dataIdx,dataIdx+1);
                state.getColumn(x).setNibbleAt(y, Integer.parseInt(sub, 16));
                dataIdx++;
            }
        }
    }

    private static void subBytes() {
        for(int i = 0; i < nK; i++) {
            state.setColumn(i, SubWordHelper.subWord(state.getColumn(i)));
        }
    }

    private static void shiftRows() {
        // once
        shiftRows(2);

        // twice
        shiftRows(4);
        shiftRows(4);

        // thrice
        shiftRows(6);
        shiftRows(6);
        shiftRows(6);
    }

    private static void shiftRows(int startRow) {
        int tempNibble1 = state.getColumn(0).getNibbleAt(startRow);
        int tempNibble2 = state.getColumn(0).getNibbleAt(startRow+1);
        state.getColumn(0).setNibbleAt(startRow, state.getColumn(1).getNibbleAt(startRow));
        state.getColumn(0).setNibbleAt(startRow+1, state.getColumn(1).getNibbleAt(startRow+1));
        state.getColumn(1).setNibbleAt(startRow, state.getColumn(2).getNibbleAt(startRow));
        state.getColumn(1).setNibbleAt(startRow+1, state.getColumn(2).getNibbleAt(startRow+1));
        state.getColumn(2).setNibbleAt(startRow, state.getColumn(3).getNibbleAt(startRow));
        state.getColumn(2).setNibbleAt(startRow+1, state.getColumn(3).getNibbleAt(startRow+1));
        state.getColumn(3).setNibbleAt(startRow, tempNibble1);
        state.getColumn(3).setNibbleAt(startRow+1, tempNibble2);
    }

    private static void addRoundKey() {
        for(int i = 0; i < 4; i++) {
            state.setColumn(i, WordHelper.xor(state.getColumn(i), keySchedule[keyScheduleIdx]));
            keyScheduleIdx++;
        }
    }

}
