/**
 * Created by josepher on 1/11/16.
 */
public class Main {

    public static void main(String[] args) {

        String data1 = "3243f6a8885a308d313198a2e0370734";
        String KEY_128_1 = "2b7e151628aed2a6abf7158809cf4f3c";
        String expectedResult1 = "3925841d02dc09fbdc118597196a0b32";
        run(data1, KEY_128_1, expectedResult1);

        String data2 = "00112233445566778899aabbccddeeff";
        String KEY_128_2 = "000102030405060708090a0b0c0d0e0f";
        String expectedResult2 = "69c4e0d86a7b0430d8cdb78070b4c55a";
        run(data2, KEY_128_2, expectedResult2);

        String data3 = "00112233445566778899aabbccddeeff";
        String KEY_192_3 = "000102030405060708090a0b0c0d0e0f1011121314151617";
        String expectedResult3 = "dda97ca4864cdfe06eaf70a0ec0d7191";
        run(data3, KEY_192_3, expectedResult3);

        String data4 = "00112233445566778899aabbccddeeff";
        String KEY_256_4 = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f";
        String expectedResult4 = "8ea2b7ca516745bfeafc49904b496089";
        run(data4, KEY_256_4, expectedResult4);

    }

    private static void run(String data, String key, String expectedResult) {
        String result = AESCryptor.cipher(data, key);
        System.out.println(result);
        if(!result.equals(expectedResult))
            throw new RuntimeException("Result differend from expected result");
        String result2 = AESCryptor.decipher(result, key);
        System.out.println(result2);
        if(!result2.equals(data))
            throw new RuntimeException("Decryption did not produce the same data");
    }

}
