import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by josepher on 1/11/16.
 */
public class AESEncryptor {

    static int keyScheduleIdx;

    public static String cipher(String data, String keyAsString) {

        File file = new File("output.txt");
        if(file.exists())
            file.delete();

        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Word[] key = WordHelper.toWordArray(keyAsString);
        int nK = key.length;
        int nR = detNR(nK);
        int ROUNDS = nR+1;
        State state = prepState(data);
        out.println(genRndOutput(0) + "input " + state.toString());
        Word[] keySchedule = KeyExpander.expandCypherKey(nK, ROUNDS, key);
        keyScheduleIdx = 0;
        printKeyScheduleUpdate(out, keySchedule, 0);

        // Input round
        addRoundKey(state, keySchedule);

        // nR - 1 rounds
        for(int round = 1; round < ROUNDS - 1; round++) {
            out.println(genRndOutput(round) + "start " + state.toString());
            subBytes(state);
            out.println(genRndOutput(round) + "s_box " + state.toString());
            shiftRows(state);
            out.println(genRndOutput(round) + "s_row " + state.toString());
            state = MixColumnsHelper.mixColumns(state);
            out.println(genRndOutput(round) + "m_col " + state.toString());
                    printKeyScheduleUpdate(out, keySchedule, round);
            addRoundKey(state, keySchedule);
        }

        // Last round
        out.println("round[" + (ROUNDS - 1) + "].start " + state.toString());
        subBytes(state);
        out.println("round[" + (ROUNDS - 1) + "].s_box " + state.toString());
        shiftRows(state);
        out.println("round[" + (ROUNDS - 1) + "].s_row " + state.toString());
        printKeyScheduleUpdate(out, keySchedule, (ROUNDS - 1));
        addRoundKey(state, keySchedule);

        out.print("round[" + (ROUNDS - 1) + "].output " + state.toString());
        out.flush();
        out.close();
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

    private static void subBytes(State state) {
        for(int i = 0; i < 4; i++) {
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

    private static String genRndOutput(int roundNum) {
        String num = null;
        if(roundNum < 10) {
            num = " " + roundNum;
        } else {
            num = String.valueOf(roundNum);
        }

        return "round[" + num + "].";
    }

    private static void printKeyScheduleUpdate(PrintStream out, Word[] keySchedule, int roundNum) {
        out.println(genRndOutput(roundNum) + "k_sch " +
                    keySchedule[keyScheduleIdx].toString() +
                    keySchedule[keyScheduleIdx+1].toString() +
                    keySchedule[keyScheduleIdx+2].toString() +
                    keySchedule[keyScheduleIdx+3].toString()
        );
    }
}
