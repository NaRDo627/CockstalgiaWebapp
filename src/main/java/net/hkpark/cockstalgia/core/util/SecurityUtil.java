package net.hkpark.cockstalgia.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class  SecurityUtil {
    private SecurityUtil() { }

    public static byte[] digest(String alg, byte[] input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(alg);
        return md.digest(input);
    }

    // MD5인코딩
    public static String md5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch(NoSuchAlgorithmException e) {
            log.error("encryption error : ", e);
            return null;
        }
    }

    // base64인코딩
    public static String base64Encoder(String key) {

        String output = null;

        if(null == key) return null;
        try {
            byte[] base64 = DatatypeConverter.parseBase64Binary(key);
            output = new String(base64);
        } catch (Exception e) {
            log.error("Encoding error", e);

        }
        return output;
    }

    // base64디코딩
    public static String base64Decoder(String key) {
        if(null == key) return null;

        String decode = null;
        try {
            decode = DatatypeConverter.printBase64Binary(key.getBytes());
        } catch (IllegalArgumentException e) {
            log.error("Decoding error", e);
        }
        //System.out.println(new String(decode));

        return decode;
    }
}