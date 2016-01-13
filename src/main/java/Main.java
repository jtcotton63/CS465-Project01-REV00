/**
 * Created by josepher on 1/11/16.
 */
public class Main {

    public static void main(String[] args) {
        // Set 1
        String data1 = "3243f6a8885a308d313198a2e0370734";
        String KEY_128_1 = "2b7e151628aed2a6abf7158809cf4f3c";
        String expectedResult1 = "3925841d 02dc09fb dc118597 196a0b32";
        run(data1, KEY_128_1, expectedResult1);

        String data2 = "00112233445566778899aabbccddeeff";
        String KEY_128_2 = "000102030405060708090a0b0c0d0e0f";
        String expectedResult2 = "69c4e0d8 6a7b0430 d8cdb780 70b4c55a";
        run(data2, KEY_128_2, expectedResult2);
    }

    private static void run(String data, String key, String expectedResult) {
        String result = AESEncryptor.cipher(data, key);
        System.out.println(result);
        if(!result.equals(expectedResult)) {
            throw new RuntimeException("Result differend from expected result");
        }
    }

}
