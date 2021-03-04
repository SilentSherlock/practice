package tools;

/**
 * Date:2020/9/17
 * Description: optional class description
 **/
public class Trie {
    private Node root;
    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        root = insert(word, 0, root, 1);
    }

    private Node insert(String word, int index, Node node, int value) {
        if (node == null) node = new Node();//树中目前不存在单词中的某个字符
        if (index == word.length()) {//已经到达单词尾部
            node.value = value;
            return node;
        }
        char cur = word.charAt(index);
        node.nextNodes[cur - 'a'] = insert(word, ++index, node.nextNodes[cur - 'a'], value);//返回已经构造好的子树
        return node;
    }
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        return search(word, root, 0) == 1;
    }

    //查找子树中是否有剩余字符
    private int search(String word, Node node, int index) {
        if (node == null) return -1;//如果word中包含树中没有的字符
        if (index == word.length()) return node.value;
        char cur = word.charAt(index);
        return search(word, node.nextNodes[cur - 'a'], ++index);
    }
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return search(prefix, root, 0) != -1;
    }

    class Node {
        int value;
        Node[] nextNodes;

        Node() {
            nextNodes = new Node[26];
        }
        Node(int value) {
            this.value = value;
            nextNodes = new Node[26];
        }
    }
}
