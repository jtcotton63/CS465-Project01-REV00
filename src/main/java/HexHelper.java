public class HexHelper {
    public static int getDecimalValueFromHex(char c) {
        if(Character.isDigit(c))
            return Character.getNumericValue(c);

        switch (c) {
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
            default:
                throw new IllegalArgumentException("Cannot get hex value of char " + c);
        }
    }

    public static char getHexValueFromDecimal(int i) {
        if(i < 10)
            return (char) (i + 48); // See ASCII character codes

        switch(i) {
            case 10:
                return 'a';
            case 11:
                return 'b';
            case 12:
                return 'c';
            case 13:
                return 'd';
            case 14:
                return 'e';
            case 15:
                return 'f';
            default:
                throw new IllegalArgumentException("Cannot convert the following int to char: " +i);
        }
    }

}
