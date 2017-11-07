package cn.medcn.csp.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * ping++支付签名工具,paypal支付工具
 * Created by LiuLP on 2017/9/27/027.
 */
public class SignatureUtil {


    private static final String TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
    private static final String PAYMENT_DETAIL = "https://api.sandbox.paypal.com/v1/payments/payment/";
    private static final String clientId = "ATBGjclV9GcygEwPz_58PUlxOvh0sJvC_Md3ZuTghMGlGIfQzgID_2zh93Ku44nMV6bcuGyoDvN3GHKv";
    private static final String secret = "EKnYYM97NgsKMPbfoB8ULvKbQr3HLMSxg7aPBuSvx2pFjTVgR78SDctLXWE6WddJDFm_sLVtg-VF09Hv";

    /**
     * 获取签名
     *
     * @param request
     * @return
     */
    public static String getSignature(HttpServletRequest request) {
        String signature = null;
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if ("x-pingplusplus-signature".equals(key)) {
                signature = request.getHeader(key);
            }
        }
        return signature;
    }


    // 获得 http body 内容
    public static String getData(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        return buffer.toString();
    }

    /**
     * 读取文件, 部署 web 程序的时候, 签名和验签内容需要从 request 中获得
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }


    /**
     * 获得公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey(String path) throws Exception {
        String pubKeyString = getStringFromFile(path);
        pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

    /**
     * 验证签名
     *
     * @param dataString
     * @param signatureString
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyData(String dataString, String signatureString, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        return signature.verify(signatureBytes);
    }

    /**
     * 获取token
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @return
     */
    private static String getAccessToken(){
        try{
            URL url = new URL(TOKEN_URL);
            String authorization = clientId+":"+secret;
            authorization = Base64.encodeBase64String(authorization.getBytes());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en_US");
            conn.setRequestProperty("Authorization", "Basic "+authorization);
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            String params = "grant_type=client_credentials";
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
            conn.connect();
            OutputStream outputStream =  conn.getOutputStream();
            outputStream.write(params.getBytes());// 输入参数
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            String accessToken = JSONObject.parseObject(result.toString()).getString("access_token");
            return accessToken;
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public static String getPaymentDetails(String paymentId){
        try{
            URL url = new URL(PAYMENT_DETAIL+paymentId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+getAccessToken());
//             conn.setConnectTimeout(10000);//连接超时 单位毫秒
//             conn.setReadTimeout(2000);//读取超时 单位毫秒
            int code = conn.getResponseCode();
            InputStreamReader inStream = null;
            if(code != 200){
                 inStream = new InputStreamReader(conn.getInputStream());

            }else{

                 inStream = new InputStreamReader(conn.getInputStream());
            }
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            return result.toString();
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }

}
