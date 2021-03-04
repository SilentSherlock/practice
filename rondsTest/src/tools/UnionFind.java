package tools;

/**
 * Date:2020/9/11
 * Description: optional class description
 **/
public class UnionFind {

    private int[] record;
    private int count;

    public UnionFind(int n) {
        record = new int[n];
        for (int i = 0;i < n;i++) {
            record[i] = i;//初始化时每个点所在集合的标识符都是自己
        }
        count = n;
    }

    //找到p所在集合的标识符
    public int find(int p) {
        if (record[p] != p) record[p] = find(record[p]);//只有当record[p] = p时才说明p找到了他所在集合的标识符，否则就进行路径压缩
        return record[p];
    }

    //合并两个点所在集合
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);
        if (pid == qid) return;//p，q在同一个集合里
        record[pid] = qid;//将p所在集合合并到q所在集合内，可以按秩合并
        count--;//集合数减一
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }
}
