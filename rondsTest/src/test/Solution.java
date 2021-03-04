package test;

/**
 * Date:2020/10/10
 * Description: optional class description
 **/
import java.util.*;


public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 寻找朋友总数
     * @param M int整型二维数组
     * @return int整型
     */
    public int findFriendNum (int[][] M) {
        // write code here
        int m = M.length;
        if (m == 0) return 0;
        int n = M[0].length;
        if (n == 0) return 0;
        UnionFind unionFind = new UnionFind(M);
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (M[i][j] == 1) {
                    if (i != 0 && M[i-1][j] == 1) unionFind.union(i*n+j, (i-1)*n+j);
                    if (j != 0 && M[i][j-1] == 1) unionFind.union(i*n+j, i*n+j-1);
                }
            }
        }
        return unionFind.count();
    }

    class UnionFind {
        private int[] record;
        private int count;

        public UnionFind (int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            record = new int[m*n];
            for (int i = 0;i < m;i++) {
                for (int j = 0;j < n;j++) {
                    if (grid[i][j] == 1) {
                        record[i*n+j] = i*n+j;
                        count++;
                    }
                }
            }
        }

        public int find(int p) {
            if (p != record[p]) record[p] = find(record[p]);
            return record[p];
        }

        public void union(int p, int q) {
            int pid = find(p);
            int qid = find(q);
            if (pid == qid) return;
            record[pid] = qid;
            count--;
        }
        public int count() {
            return this.count;
        }
    }
}
