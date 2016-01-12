/**
 * Created by josepher on 1/11/16.
 */
public class KeyExpander {

    private static int[] rcon = { 0x00000000, // Rcon[] is 1-based, so the first entry is just a place holder
            0x01000000, 0x02000000, 0x04000000, 0x08000000,
            0x10000000, 0x20000000, 0x40000000, 0x80000000,
            0x1B000000, 0x36000000, 0x6C000000, 0xD8000000,
            0xAB000000, 0x4D000000, 0x9A000000, 0x2F000000,
            0x5E000000, 0xBC000000, 0x63000000, 0xC6000000,
            0x97000000, 0x35000000, 0x6A000000, 0xD4000000,
            0xB3000000, 0x7D000000, 0xFA000000, 0xEF000000,
            0xC5000000, 0x91000000, 0x39000000, 0x72000000,
            0xE4000000, 0xD3000000, 0xBD000000, 0x61000000,
            0xC2000000, 0x9F000000, 0x25000000, 0x4A000000,
            0x94000000, 0x33000000, 0x66000000, 0xCC000000,
            0x83000000, 0x1D000000, 0x3A000000, 0x74000000,
            0xE8000000, 0xCB000000, 0x8D000000};

    /**
     *
     * @param nK: only acceptable values are 4, 6, or 8 (128, 192, or 256-bit encryption)
     * @param nR: the number of rounds for the encryption algorithm
     * @return The Key Schedule as an array of Words.
     */
    public static Word[] expandCypherKey(int nK, int nR, Word[] key) {

        int ROUNDS = nR+1;
        short nB = 4;

        // Add the cypher key first
        Word[] keySchedule = new Word[ROUNDS*nK];
        for(short i=0; i<nK;i++) {
            keySchedule[i] = key[i];
        }

        Word temp;

        for(int i=nK; i<(nB*ROUNDS); i++) {
            temp = keySchedule[i-1];
            if(i % nK == 0) {
//                temp =
            }
        }



        return null;
    }

    private static Word rotWord(Word in) {
        // Moves [a0,a1,a2,a3] to be [a1,a2,a3,a0]
        return new Word(in.getNibbleAt(2),in.getNibbleAt(3),
                in.getNibbleAt(4),in.getNibbleAt(5),
                in.getNibbleAt(6),in.getNibbleAt(7),
                in.getNibbleAt(0),in.getNibbleAt(1));
    }

    private static void getRCon(short idx) {
        // returns the value of rcon
    }
}
