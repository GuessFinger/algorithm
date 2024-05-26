//  chatgpt 回溯算法
//  回溯算法通过尝试所有可能的分配组合来找到最优解。这种方法虽然可能会比较耗时，但它能确保找到最接近初始分配比例的方案。

import java.util.*;

public class InvestmentAllocationBacktracking {

    private static int minDifference = Integer.MAX_VALUE;
    private static List<Integer>[] bestAllocation;

    public static void main(String[] args) {
        int[] bills = {100, 200, 300}; // 票据金额
        double[] initialRatios = {0.2, 0.8}; // 初始分配比例
        List<Integer>[] result = backtrackingInvestmentAllocation(bills, initialRatios);

        for (int i = 0; i < result.length; i++) {
            System.out.println("Investor " + (i + 1) + " gets bills: " + result[i]);
        }
    }

    public static List<Integer>[] backtrackingInvestmentAllocation(int[] bills, double[] initialRatios) {
        int numInvestors = initialRatios.length;
        bestAllocation = new ArrayList[numInvestors];
        for (int i = 0; i < numInvestors; i++) {
            bestAllocation[i] = new ArrayList<>();
        }

        List<Integer>[] currentAllocation = new ArrayList[numInvestors];
        for (int i = 0; i < numInvestors; i++) {
            currentAllocation[i] = new ArrayList<>();
        }

        int totalAmount = Arrays.stream(bills).sum();
        int[] targetAmounts = new int[numInvestors];
        for (int i = 0; i < numInvestors; i++) {
            targetAmounts[i] = (int) (initialRatios[i] * totalAmount);
        }

        backtrack(bills, 0, currentAllocation, new int[numInvestors], targetAmounts);
        return bestAllocation;
    }

    private static void backtrack(int[] bills, int index, List<Integer>[] currentAllocation, int[] currentAmounts, int[] targetAmounts) {
        if (index == bills.length) {
            int difference = calculateDifference(currentAmounts, targetAmounts);
            if (difference < minDifference) {
                minDifference = difference;
                for (int i = 0; i < currentAllocation.length; i++) {
                    bestAllocation[i] = new ArrayList<>(currentAllocation[i]);
                }
            }
            return;
        }

        for (int i = 0; i < currentAllocation.length; i++) {
            currentAllocation[i].add(bills[index]);
            currentAmounts[i] += bills[index];
            backtrack(bills, index + 1, currentAllocation, currentAmounts, targetAmounts);
            currentAmounts[i] -= bills[index];
            currentAllocation[i].remove(currentAllocation[i].size() - 1);
        }
    }

    private static int calculateDifference(int[] currentAmounts, int[] targetAmounts) {
        int difference = 0;
        for (int i = 0; i < currentAmounts.length; i++) {
            difference += Math.abs(currentAmounts[i] - targetAmounts[i]);
        }
        return difference;
    }
}

// 代码解释
// 初始化:
//
// 计算总金额并计算每个投资者的目标金额targetAmounts。
// 初始化bestAllocation用于保存最佳分配方案。
// 初始化currentAllocation用于保存当前分配状态。
// 回溯算法:
//
// 使用backtrack方法尝试所有可能的分配方案。
// 对每个票据，尝试将其分配给每个投资者，并递归调用backtrack方法。
// 如果到达叶节点（所有票据都被分配），计算当前分配方案与目标分配比例的差异。
// 更新minDifference和bestAllocation以保存当前最优方案。
// 差异计算:
//
// 使用calculateDifference方法计算当前分配方案与目标分配比例的差异。
