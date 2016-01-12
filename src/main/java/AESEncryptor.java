import java.math.BigInteger;

/**
 * Created by josepher on 1/11/16.
 */
public class AESEncryptor {

    public static void cipher(String keyAsString) {

        Word[] key = WordHelper.toWordArray(keyAsString);
        int nK = key.length;
        Word[] keySchedule = KeyExpander.expandCypherKey(nK, key);

        // Test
        System.out.println("STOP HERE");

        // add first round
        // AddRoundKey

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


}
