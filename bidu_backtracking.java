// 百度 回溯算法
// 要尽可能地全面考虑并找到最优的分配方案，我们可以使用回溯法（backtracking）或动态规划（dynamic programming）。但由于动态规划对于此类问题可能并不直观，并且回溯法更易于理解和实现，我们将使用回溯法来寻找最优解。
// 在回溯法中，我们将尝试所有可能的分配组合，并在每一步中检查是否满足条件（即分配比例尽可能接近初始分配比例）。以下是一个使用回溯法的Java代码示例：

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvestmentAllocation {

    private static final double EPSILON = 1e-6; // 用于浮点数比较的精度

    public static void main(String[] args) {
        int[] tickets = {300, 200, 100}; // 票据金额
        double[] initialRatios = {0.2, 0.8}; // 初始分配比例

        List<Integer> allocation = findOptimalAllocation(tickets, initialRatios);

        // 输出分配结果
        System.out.println("Optimal allocation:");
        for (int i = 0; i < allocation.size(); i++) {
            System.out.println("Investor " + (i + 1) + ": " + allocation.get(i));
        }
    }

    public static List<Integer> findOptimalAllocation(int[] tickets, double[] initialRatios) {
        Arrays.sort(tickets); // 按金额从小到大排序
        double[] targets = new double[initialRatios.length]; // 每位投资者的目标金额
        double totalMoney = 0;
        for (int ticket : tickets) {
            totalMoney += ticket;
        }
        for (int i = 0; i < initialRatios.length; i++) {
            targets[i] = initialRatios[i] * totalMoney;
        }

        List<Integer> allocation = new ArrayList<>();
        for (int i = 0; i < initialRatios.length; i++) {
            allocation.add(0); // 初始化每位投资者的分配金额为0
        }

        return backtrack(tickets, allocation, targets, 0); // 从第一张票据开始回溯
    }

    private static List<Integer> backtrack(int[] tickets, List<Integer> allocation, double[] targets, int index) {
        if (index == tickets.length) {
            // 所有票据都已分配，检查是否满足条件
            if (isCloseToTargets(allocation, targets)) {
                return allocation; // 返回分配结果
            }
            return null; // 没有找到满足条件的分配，返回null
        }

        for (int i = 0; i < allocation.size(); i++) {
            // 尝试将当前票据分配给每个投资者
            if (allocation.get(i) + tickets[index] <= targets[i]) {
                allocation.set(i, allocation.get(i) + tickets[index]);
                List<Integer> result = backtrack(tickets, allocation, targets, index + 1);
                if (result != null) {
                    // 找到了满足条件的分配，返回结果
                    return result;
                }
                // 回溯，撤销当前分配
                allocation.set(i, allocation.get(i) - tickets[index]);
            }
        }

        return null; // 没有找到满足条件的分配，返回null
    }

    private static boolean isCloseToTargets(List<Integer> allocation, double[] targets) {
        for (int i = 0; i < allocation.size(); i++) {
            if (Math.abs(allocation.get(i) - targets[i]) / targets[i] > EPSILON) {
                return false; // 分配金额与目标金额差距过大
            }
        }
        return true; // 所有分配金额都接近目标金额
    }
}

// 这个代码示例使用回溯法来尝试所有可能的分配组合，并在每一步中检查是否满足条件（即分配金额与目标金额的差距在可接受的范围内）。它首先按金额对票据进行排序，然后递归地尝试将每张票据分配给不同的投资者，同时跟踪每位投资者的当前分配金额。如果找到了满足条件的分配，它将返回该分配；否则，它将回溯并尝试其他分配方式。

// 请注意，对于大量票据和投资者的情况，回溯法可能会非常耗时，因为它需要尝试所有可能的分配组合。在实际应用中，可能需要考虑使用其他优化技术来减少搜索空间，例如启发式搜索或剪枝策略。
