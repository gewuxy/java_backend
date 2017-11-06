import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Liuchangling on 2017/11/2.
 */

public class Solution {


    public static void main(String[] nums){
        int[] num = {-1, 1, 3, 3, 3, 2, 1, 0};
        System.out.println(solution1(num));

        int[] num1 = {1,3,-3};
        System.out.println(solution2(num1));
    }

    // 第一题
    public static int solution1(int[] num) {
        Arrays.sort(num);
        int length = num[num.length - 1] - num[0];
        int res = 1;

        int[][] dp = new int[num.length][length + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < length + 1; j++) {
                dp[i][j] = 1;
            }
        }
        for (int i = 1; i < num.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int diff = num[i] - num[j]; // 算出i和j之间的等差
                dp[i][diff] = dp[j][diff] + 1;// 当前
                res = Math.max(res, dp[i][diff]);
            }
        }
        return res;
    }


    // 第二题
    public static int solution2(int[] num) {
        Set<Integer> sets = new TreeSet<Integer>();
        for (Integer s : num) {
            sets.add(s);
        }

        Integer[] b = new Integer[sets.size()];
        sets.toArray(b);
        int res = Math.abs(b[0] - b[b.length - 1]);
        return res;
    }


}
