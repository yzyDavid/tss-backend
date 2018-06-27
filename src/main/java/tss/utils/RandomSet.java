package tss.utils;


import java.util.HashSet;

public class RandomSet {

    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if (n > (max - min + 1) || max < min) {
            System.out.println("Not enough num.");
            return;
        }
        while (set.size() < n) {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min + 0.4)) + min;      //防止因为random达不到1而造成无法取得max
            set.add(num);// 将不同的数存入HashSet中
        }
        /*
        for (int i = 0; i < n; i++) {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        int setSize = set.size();
        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
        if(setSize < n) {
            randomSet(min, max, n - setSize, set);// 递归
        }
        System.out.println("testset:"+set.size());
        System.out.println("n:"+n);
*/
    }
}
