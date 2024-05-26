// 百度 贪心算法
// 在使用Java代码和贪心算法来解决这个场景时，我们可以采取一种简单的方法来尝试使两位投资者的最终投资金额尽量接近他们的初始分配比例。这里的关键是优先分配大额票据给当前投资金额较少的投资者，以平衡两者之间的差距。
// 以下是一个使用贪心算法的Java代码示例：


import java.util.Arrays;

public class InvestmentAllocation {

    public static void main(String[] args) {
        int[] tickets = {300, 200, 100}; // 票据金额
        double[] initialRatios = {0.2, 0.8}; // 初始分配比例

        int[] allocations = greedyAllocate(tickets, initialRatios);

        // 输出每位投资者的分配结果
        for (int i = 0; i < allocations.length; i++) {
            System.out.println("Investor " + (i + 1) + ": " + allocations[i]);
        }
    }

    public static int[] greedyAllocate(int[] tickets, double[] initialRatios) {
        Arrays.sort(tickets); // 注意这里为了平衡分配，我们需要按金额从小到大排序
        double[] targets = new double[initialRatios.length]; // 每位投资者的目标金额
        for (int i = 0; i < initialRatios.length; i++) {
            targets[i] = initialRatios[i] * Arrays.stream(tickets).sum(); // 计算每位投资者的目标金额
        }

        int[] allocations = new int[initialRatios.length]; // 每位投资者的分配金额
        int index = tickets.length - 1; // 从最大的票据开始分配

        while (index >= 0) {
            // 找到当前分配较少的投资者
            int minIndex = 0;
            for (int i = 1; i < allocations.length; i++) {
                if (allocations[i] < allocations[minIndex]) {
                    minIndex = i;
                }
            }

            // 尝试分配当前票据给该投资者
            if (allocations[minIndex] + tickets[index] <= targets[minIndex]) {
                allocations[minIndex] += tickets[index];
            }

            index--; // 处理下一张票据
        }

        return allocations;
    }
}

// 请注意，上述代码在排序时选择了按金额从小到大排序，因为我们想要从最小的票据开始分配，以便在必要时可以调整大额票据的分配。然而，由于我们想要尽量接近初始分配比例，实际上按金额从大到小排序可能更为直观，但这取决于具体的贪心策略。

// 在上面的代码中，我们计算了每位投资者的目标金额，并在分配过程中始终尝试使每位投资者的当前金额不超过其目标金额。当所有票据都被分配后，我们就得到了最终的分配结果。

// 这个算法可能不是最优的，因为它没有考虑所有可能的分配组合。然而，在大多数情况下，它应该能够给出一个接近初始分配比例的合理结果。如果需要更精确的分配，可能需要使用更复杂的算法或优化技术
