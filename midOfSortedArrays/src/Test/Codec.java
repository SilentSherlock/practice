package Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Date:2020.6.16
 * Description: 利用层序遍历序列化并反序列化二叉树
 **/
public class Codec {
    // Encodes a tree to a single string.
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {this.val = x;}
    }
    public String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode current = queue.poll();
            if (current != null) {
                builder.append("(").append(current.val).append(")");
                queue.offer(current.left);
                queue.offer(current.right);
            }else builder.append("#");
        }

        return builder.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        System.out.println("data is " + data);
        if (data.length() == 1) return null;
        int begin = 1;
        String rootStr = getSubString(data, begin);
        TreeNode root = new TreeNode(Integer.parseInt(rootStr));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        begin = begin + rootStr.length();//begin在第一个右括号位置，每次提取结束begin都应该在右括号处

        while(!queue.isEmpty()) {
            int length = queue.size();
            for (int i = 0;i < length;i++) {
                TreeNode current = queue.poll();
                System.out.println("begin is " + begin);

                begin++;
                if (begin >= data.length()) return root;
                char leftVal = data.charAt(begin);
                if (leftVal != '#') {//说明是左括号
                    begin++;
                    String value = getSubString(data, begin);
                    TreeNode left = new TreeNode(Integer.parseInt(value));
                    current.left = left;
                    queue.offer(left);
                    begin = begin + value.length();
                }else current.left = null;

                begin++;
                if (begin >= data.length()) return root;
                char rightVal = data.charAt(begin);
                if (rightVal != '#') {
                    begin++;
                    String value = getSubString(data, begin);
                    TreeNode right = new TreeNode(Integer.parseInt(value));
                    current.right = right;
                    queue.offer(right);
                    begin = begin + value.length();
                }else current.right = null;
            }
        }
        return root;
    }

    private String getSubString(String str, int beginIndex) {
        //beginIndex应在要找的右括号所匹配的左括号的下一位，即直接提取出要包裹在括号中的数
        for (int i = beginIndex;i < str.length();i++) {
            if (str.charAt(i) == ')') return str.substring(beginIndex, i);
        }
        return null;
    }


}
