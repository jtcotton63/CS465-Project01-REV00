/**
 * Created by josepher on 1/11/16.
 */
public class KeyExpander {

    private static int[] rcon = { 0x00, // Rcon[] is 1-based, so the first entry is just a place holder
            0x01, 0x02, 0x04, 0x08,
            0x10, 0x20, 0x40, 0x80,
            0x1B, 0x36, 0x6C, 0xD8,
            0xAB, 0x4D, 0x9A, 0x2F,
            0x5E, 0xBC, 0x63, 0xC6,
            0x97, 0x35, 0x6A, 0xD4,
            0xB3, 0x7D, 0xFA, 0xEF,
            0xC5, 0x91, 0x39, 0x72,
            0xE4, 0xD3, 0xBD, 0x61,
            0xC2, 0x9F, 0x25, 0x4A,
            0x94, 0x33, 0x66, 0xCC,
            0x83, 0x1D, 0x3A, 0x74,
            0xE8, 0xCB, 0x8D };

    /**
     *
     * @param nK: only acceptable values are 4, 6, or 8 (128, 192, or 256-bit encryption)
     * @return The Key Schedule as an array of Words.
     */
    public static Word[] expandCypherKey(int nK, Word[] key) {

        int nR = detNR(nK);
        int ROUNDS = nR+1;
        int nB = 4;

        // Add the cypher key first
        Word[] keySchedule = new Word[ROUNDS*nK];
        for(int i=0; i<nK; i++) {
            keySchedule[i] = key[i];
        }

        Word temp;

        for(int i=nK; i<(nB*ROUNDS); i++) {
            temp = keySchedule[i-1];
            if(i % nK == 0) {
                temp = rotWord(temp);
                temp = SubWordHelper.subWord(temp);
                temp = getRCONValue(temp, i, nK);
            } else if((nK > 6) && (i % nK == 4)) {
                SubWordHelper.subWord(temp);
            }
            keySchedule[i] = WordHelper.xor(keySchedule[i-nK], temp);
        }

        return keySchedule;
    }

    private static Word rotWord(Word in) {
        // Moves [a0,a1,a2,a3] to be [a1,a2,a3,a0]
        return new Word(in.getNibbleAt(2),in.getNibbleAt(3),
                in.getNibbleAt(4),in.getNibbleAt(5),
                in.getNibbleAt(6),in.getNibbleAt(7),
                in.getNibbleAt(0),in.getNibbleAt(1));
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

    private static Word getRCONValue(Word a, int i, int nK) {
        int r = rcon[i/nK];
        Word intToWord = new Word();
        int b1 = (r & 0xf0) >> 4;
        int b2 = r & 0x0f;
        intToWord.setNibbleAt(0, b1);
        intToWord.setNibbleAt(1, b2);
        return WordHelper.xor(a, intToWord);
    }

}
