package cn.medcn.common.geetest;

/**
 * Created by lixuan on 2017/4/7.
 */
public class GeetestConfig {
    // 填入自己的captcha_id和private_key
    private static final String geetest_id = "b46d1900d0a894591916ea94ea91bd2c";
    private static final String geetest_key = "36fc3fe98530eea08dfc6ce76e3d24c4";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }

}
