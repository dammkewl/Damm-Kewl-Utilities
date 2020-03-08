package nl.damm.util.string;

public class NumberRepresentationConversions {

    private final static char[] digitsLower = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    public static String toHexBigEndianLowerCase(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder(bytes.length*2);
        for(int i = 0; i < bytes.length; ++i) {
            sb.append(Integer.toHexString(bytes[i]&0x0FF));
        }
        return sb.toString();
    }

    public static String toHexLittleEndianLowerCase(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder(bytes.length*2);
        for(int i = bytes.length-1; i >= 0 ; --i) {
            sb.append(Integer.toHexString(bytes[i]&0x0FF));
        }
        return sb.toString();
    }

}
