/**
 * Created by josepher on 1/11/16.
 */
public class AESEncryptor {

    static int keyScheduleIdx = 0;

    public static String cipher(String data, String keyAsString) {

        Word[] key = WordHelper.toWordArray(keyAsString);
        int nK = key.length;
        int nR = detNR(nK);
        int ROUNDS = nR+1;
        State state = prepState(data);
        Word[] keySchedule = KeyExpander.expandCypherKey(nK, ROUNDS, key);
        keyScheduleIdx = 0;

        // Input round
        addRoundKey(state, keySchedule);

        // nR - 1 rounds
        for(int round = 1; round < ROUNDS - 1; round++) {
            subBytes(state, nK);
            shiftRows(state);
            state = MixColumnsHelper.mixColumns(state);
            addRoundKey(state, keySchedule);
        }

        // Last rounds
        subBytes(state, nK);
        shiftRows(state);
        addRoundKey(state, keySchedule);

        return state.toString();

    }

    private static int detNR(int nK) {
        if(nK == 4)
            return 10;
        if(nK == 6)
            return 12;
        if(nK == 8)
            return 14;

        throw new IllegalArgumentException("nK is not 4, 6, or 8");
    }

    // This is a duplicate function; should be combined with WordHelper.toWordArray
    private static State prepState(String data) {
        State state = new State();
        int dataIdx = 0;
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 8; y++) {
                String sub = data.substring(dataIdx,dataIdx+1);
                state.getColumn(x).setNibbleAt(y, Integer.parseInt(sub, 16));
                dataIdx++;
            }
        }
        return state;
    }

    private static void subBytes(State state, int nK) {
        for(int i = 0; i < nK; i++) {
            state.setColumn(i, SubWordHelper.subWord(state.getColumn(i)));
        }
    }

    private static void shiftRows(State state) {
        // once
        shiftRows(state, 2);

        // twice
        shiftRows(state, 4);
        shiftRows(state, 4);

        // thrice
        shiftRows(state, 6);
        shiftRows(state, 6);
        shiftRows(state, 6);
    }

    private static void shiftRows(State state, int startRow) {
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

    private static void addRoundKey(State state, Word[] keySchedule) {
        for(int i = 0; i < 4; i++) {
            state.setColumn(i, WordHelper.xor(state.getColumn(i), keySchedule[keyScheduleIdx]));
            keyScheduleIdx++;
        }
    }

}
