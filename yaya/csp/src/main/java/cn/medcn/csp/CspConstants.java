package cn.medcn.csp;

import java.util.Arrays;

/**
 * Created by lixuan on 2017/9/27.
 */
public class CspConstants {

    public static final String TOKEN_KEY = "csp_token";

    public static final String COURSE_ID_KEY = "courseId";
    //响应即构回调成功的code
    public static final String ZEGO_SUCCESS_CODE = "1";

    public static final int MIN_FLUX_LIMIT = 2;

    public static final String LIVE_TYPE_KEY = "liveType";


    /**
     * 计算等差数列数
     * @param array
     * @return
     */
    public static int countSeries(int[] array){
        int counter = 0;
        for (int i = 0; i < array.length - 2; i ++) {
            int differ = array[i + 1] - array[i];
            for (int j = i + 2; j < array.length; j ++) {
                if (differ == array[j] - array[j - 1]) {
                    counter ++;
                } else {
                    break;
                }
            }

        }
        return counter;
    }


    public static void main(String[] args) {
        int[] srcArray = new int[]{-1, 1, 3, 3, 3, 2, 1, 0};
        System.out.println(countSeries(srcArray));

        System.out.println(maxDiffer(srcArray));
    }

    /**
     * 计算最大差
     * @param array
     * @return
     */
    public static int maxDiffer(int[] array){

        int differ = Integer.MIN_VALUE;

        for (int k = 0; k < array.length - 1; k ++) {
            int[] array1 = Arrays.copyOfRange(array, 0, k + 1);
            int[] array2 = Arrays.copyOfRange(array, k + 1, array.length);
            int max1 = max(array1);
            int max2 = max(array2);
            int maxDiffer = Math.abs(max1 - max2);
            if (maxDiffer > differ) {
                differ = maxDiffer;
            }
        }
        return differ;
    }


    public static int max(int[] array){
        int max = Integer.MIN_VALUE;
        for (int i = 0 ; i < array.length ; i ++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

}
