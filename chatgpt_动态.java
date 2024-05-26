使用动态规划来解决投资分配问题的Java代码会更加复杂一些，因为动态规划需要考虑到不同的子问题，并存储中间结果以便重复使用。以下是一个实现该问题的详细Java代码示例：

java



import java.util.*;

public class InvestmentAllocationDP {

    public static void main(String[] args) {
        int[] bills = {100, 200, 300}; // 票据金额
        double[] initialRatios = {0.2, 0.8}; // 初始分配比例
        List<Integer>[] result = dpInvestmentAllocation(bills, initialRatios);

        for (int i = 0; i < result.length; i++) {
            System.out.println("Investor " + (i + 1) + " gets bills: " + result[i]);
        }
    }

    public static List<Integer>[] dpInvestmentAllocation(int[] bills, double[] initialRatios) {
        int n = bills.length;
        int numInvestors = initialRatios.length;

        // 计算总金额和每个投资者的期望金额
        int totalAmount = Arrays.stream(bills).sum();
        int[] targetAmounts = new int[numInvestors];
        for (int i = 0; i < numInvestors; i++) {
            targetAmounts[i] = (int) (initialRatios[i] * totalAmount);
        }

        // dp[i][j] 表示前 i 个票据分配给 j 个投资者时的最小差值
        int[][] dp = new int[n + 1][targetAmounts[0] + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;

        // path 用于记录分配路径
        int[][][] path = new int[n + 1][targetAmounts[0] + 1][2];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= targetAmounts[0]; j++) {
                // 不分配给投资者1
                dp[i][j] = dp[i - 1][j];
                path[i][j][0] = j;
                path[i][j][1] = 1; // 分配给投资者2

                // 分配给投资者1
                if (j >= bills[i - 1] && dp[i - 1][j - bills[i - 1]] + Math.abs(j - bills[i - 1] - targetAmounts[0]) < dp[i][j]) {
                    dp[i][j] = dp[i - 1][j - bills[i - 1]] + Math.abs(j - bills[i - 1] - targetAmounts[0]);
                    path[i][j][0] = j - bills[i - 1];
                    path[i][j][1] = 0; // 分配给投资者1
                }
            }
        }

        // 回溯路径
        List<Integer>[] allocation = new ArrayList[numInvestors];
        for (int i = 0; i < numInvestors; i++) {
            allocation[i] = new ArrayList<>();
        }

        int j = targetAmounts[0];
        for (int i = n; i > 0; i--) {
            if (path[i][j][1] == 0) {
                allocation[0].add(bills[i - 1]);
                j = path[i][j][0];
            } else {
                allocation[1].add(bills[i - 1]);
            }
        }

        return allocation;
    }
}


代码解释
初始化:

totalAmount计算票据总金额，targetAmounts计算每个投资者的期望金额。
初始化dp数组，dp[i][j]表示前i个票据分配给投资者1时，金额为j的最小差值。
初始化path数组，用于记录分配路径。
动态规划:

使用嵌套循环遍历每个票据和每个可能的金额。
更新dp和path数组。
若不分配给投资者1，保持之前的状态。
若分配给投资者1，更新状态并记录路径。
回溯路径:

根据path数组回溯找到最优分配方案。
分配票据到对应的投资者。
输出示例
假设输入票据金额为{100, 200, 300}，初始分配比例为20%和80%，输出将会显示每个投资者分配到的票据列表：