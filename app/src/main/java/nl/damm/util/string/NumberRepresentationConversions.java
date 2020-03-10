package nl.damm.util.string;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.stream.IntStream;

public class NumberRepresentationConversions {

    private final static char[] digitsLower = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    public static String toHexByteOrderBigEndianLowerCase(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder(bytes.length*2);
        for(int i = 0; i < bytes.length; ++i) {
            String add = Integer.toHexString(bytes[i]&0x0FF);
            if(add.length() == 1) {
                sb.append("0");
            }
            sb.append(add);
        }
        return sb.toString();
    }

    public static String toHexByteOrderLittleEndianLowerCase(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder(bytes.length*2);
        for(int i = bytes.length-1; i >= 0 ; --i) {
            String add = Integer.toHexString(bytes[i]&0x0FF);
            if(add.length() == 1) {
                sb.append("0");
            }
            sb.append(add);
        }
        return sb.toString();
    }

    public static String makeItHappen(byte[] bytes) {
        return new BigInteger(bytes).toString(16);
    }

}
