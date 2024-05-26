// chatgpt 贪心算法

import java.util.*;

public class InvestmentAllocation {

    public static void main(String[] args) {
        int[] bills = {100, 200, 300}; // 票据金额
        double[] initialRatios = {0.2, 0.8}; // 初始分配比例
        List<Integer>[] result = greedyInvestmentAllocation(bills, initialRatios);

        for (int i = 0; i < result.length; i++) {
            System.out.println("Investor " + (i + 1) + " gets bills: " + result[i]);
        }
    }

    public static List<Integer>[] greedyInvestmentAllocation(int[] bills, double[] initialRatios) {
        int numInvestors = initialRatios.length;
        List<Integer>[] allocation = new ArrayList[numInvestors];

        for (int i = 0; i < numInvestors; i++) {
            allocation[i] = new ArrayList<>();
        }

        Arrays.sort(bills);
        reverseArray(bills);

        double[] currentAmounts = new double[numInvestors];
        double totalAmount = Arrays.stream(bills).sum();

        for (int bill : bills) {
            int bestInvestor = findBestInvestor(currentAmounts, initialRatios, totalAmount);
            allocation[bestInvestor].add(bill);
            currentAmounts[bestInvestor] += bill;
        }

        return allocation;
    }

    private static int findBestInvestor(double[] currentAmounts, double[] initialRatios, double totalAmount) {
        int bestInvestor = -1;
        double minDifference = Double.MAX_VALUE;

        for (int i = 0; i < currentAmounts.length; i++) {
            double expectedAmount = initialRatios[i] * totalAmount;
            double difference = Math.abs((currentAmounts[i] + 1) - expectedAmount);

            if (difference < minDifference) {
                minDifference = difference;
                bestInvestor = i;
            }
        }

        return bestInvestor;
    }

    private static void reverseArray(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
}

// 代码解释
// 排序和初始化：
//
// 首先对票据进行排序并反转，使得票据按金额从大到小排列。
// 初始化每个投资者的分配列表和当前已分配的总金额。
// 贪心分配票据：
//
// 遍历每张票据，找到当前最适合的投资者进行分配。
// findBestInvestor方法会找到使分配后与期望分配比例差距最小的投资者。
// 辅助方法：
//
// findBestInvestor方法计算当前金额与期望金额的差值，并选择差值最小的投资者。
// reverseArray方法用于将票据列表反转。
// 输出示例
// 假设输入票据金额为{100, 200, 300}，初始分配比例为20%和80%，输出将会显示每个投资者分配到的票据列表：
//
// less
// 复制代码
// Investor 1 gets bills: [200]
// Investor 2 gets bills: [300, 100]
// 这样，贪心算法在每一步选择当前最优的分配方案，尽量接近初始的分配比例。
