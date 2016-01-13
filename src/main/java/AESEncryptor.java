/**
 * Created by josepher on 1/11/16.
 */
public class AESEncryptor {

    static Word[] keySchedule = null;
    static int keyScheduleIdx = 0;
    static State state = null;

    public static void cipher(String data, String keyAsString) {

        prepState(data);
        Word[] key = WordHelper.toWordArray(keyAsString);
        int nK = key.length;
        keySchedule = KeyExpander.expandCypherKey(nK, key);

        // add first round
        addRoundKey();


        // Test
        System.out.println("STOP HERE");

        //for the following rounds
            //SubBytes
                // Where does this come from? Static S-Box?
            //ShiftRows
            //MixColumns
            //AddRoundKey

        //SubBytes
        //ShiftRows
        //AddRoundKey
    }

    private static void prepState(String data) {
//        String[] dataArray = data.split("(?<=\\G.{2})");
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

    private static void addRoundKey() {
        for(int i = 0; i < 4; i++) {
            state.setColumn(i, WordHelper.xor(state.getColumn(i), keySchedule[keyScheduleIdx]));
            keyScheduleIdx++;
        }
    }

}
