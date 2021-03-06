package cn.medcn.common.utils;

import cn.medcn.common.Constants;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Created by lixuan on 2017/1/10.
 */
public class DESUtils {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    /**
     * 这个值必须是8位
     */
    public static final String IV_PARAMETER_SPEC = "qWeRdFgH";

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    public static String encode(String key,String data) {
        if(data == null)
            return null;
        try{
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER_SPEC.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.encodeBase64String(bytes);
        }catch(Exception e){
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key,String data) {
        if(data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER_SPEC.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

            return new String(cipher.doFinal(Base64.decodeBase64(data.getBytes())));
        } catch (Exception e){
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二行制转字符串
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length/2];
        for (int n = 0; n < b.length; n+=2) {
            String item = new String(b,n,2);
            b2[n/2] = (byte)Integer.parseInt(item,16);
        }
        return b2;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String src = "id=529&abroad=0&_local=zh_CN";
        String pw = encode(Constants.DES_PRIVATE_KEY, src);
        System.out.println("加密之后="+pw);
        String plain = decode(Constants.DES_PRIVATE_KEY, "ev63jtjZYcn8vC6gSpQ/CcuMdawlOVwVdNoRU6gb8tE=");
        System.out.println("解密之后="+plain);

        String text = "u3RQdAQFGWEFXRnf6mjwAWSDYmKT9IXmemhvl/8+j38=";

        String str2 = "oAbX2Mf%2BQHYbxsAJFoxwcA%2BygOJEtb5nxLkLp8PHhSc%3D";
        System.out.println(URLEncoder.encode(text, "utf-8"));

        System.out.println(URLDecoder.decode(str2, "utf-8"));
    }
}
