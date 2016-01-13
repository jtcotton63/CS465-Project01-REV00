/**
 * Created by josepher on 1/11/16.
 */
public class Main {

    private static String data1 = "00112233445566778899aabbccddeeff";
    private static String data2 = "3243f6a8885a308d313198a2e0370734";
    private static String KEY_128 = "2b7e151628aed2a6abf7158809cf4f3c";

    public static void main(String[] args) {
        AESEncryptor.cipher(data2, KEY_128);
    }

}
