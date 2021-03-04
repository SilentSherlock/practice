package Solution;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Date:2020.5.17
 * Description: leetcode problem 210
 **/
public class Solution {

    private List<List<Integer>> map;
    private Stack<Integer> result = new Stack<>();
    private int[] visited;
    private boolean flag = false;//判断是否有环

    private void dfs(int v) {
        visited[v] = 1;
        for (Integer node : map.get(v)) {
            if (visited[node] == 0) {//为0说明未走过
                dfs(node);
                if (flag) return;//
            }else if (visited[node] == 1) {//为1说明正在搜索中
                flag = true;
                return;
            }
        }
        visited[v] = 2;//为2代表搜索完成
        result.push(v);
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        visited = new int[numCourses];
        map = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            map.add(new LinkedList<>());
        }
        for (int[] edge: prerequisites){
            map.get(edge[1]).add(edge[0]);
        }
        for (List<Integer> list : map) {
            System.out.println(list.toString());
        }
        for (int i = 0;i < numCourses && !flag;i++) {
            if (visited[i] == 0) dfs(i);
        }
        int[] result = {};
        if (flag) return result;
        else {
            result = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                result[i] = this.result.pop();
            }
            return result;
        }
    }
}
