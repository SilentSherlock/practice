package tools;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Date:2020/9/18
 * Description: 实现泛型的单词查找树
 * T----结点值的类型
 * 查找树应当可以让用户指定字母表,字母表顺序应该按照Ascii码表
 **/
public class StringTrie<T> {

    private Node root;
    private int count;

    public StringTrie() {
        this.count = 26;//默认查找树只包含26个小写字母
        root = new Node(count);
    }
    public StringTrie(int count) {
        this.count = count;
        root = new Node(count);
    }

    /**
     * 放入一个键值对，值的类型为T，键类型确定为String
     * */
    public void put(String key, T value) {
        root = put(key, value, root, 0);
    }

    private Node put(String key, T value, Node node, int index) {
        if (node == null) node = new Node(count);
        if (index == key.length()) {
            node.value = value;
            return node;
        }
        char cur = key.charAt(index);
        node.nextNodes[cur] = put(key, value, node.nextNodes[cur], ++index);
        return node;
    }
    /**
     * 获得以key对应的值，没找到则返回null
     * */
    public T get(String key) {
        Node result = get(key, root, 0);
        if (result == null) return null;
        return (T) result.value;
    }

    private Node get(String key, Node node, int index) {
        if (node == null) return null;
        if (index == key.length()) return node;
        char cur = key.charAt(index);
        return get(key, node.nextNodes[cur], ++index);
    }

    /**
     * 删除一个键值对
     * */
    public void delete(String key) {
        root = delete(key, root, 0);
    }

    private Node delete(String key, Node node, int index) {
        if (node == null) return null;
        if (index == key.length()) {
            node.value = null;//找到key后，将key对应的value赋空
        }else {
            char cur = key.charAt(index);
            node.nextNodes[cur] = delete(key, node.nextNodes[cur], ++index);//在子树中递归找key
        }
        if (node.value != null) return node;//如果当前node组成的key有值对应则可以直接返回
        for (int i = 0;i < node.nextNodes.length;i++) {
            if (node.nextNodes[i].value != null) return node;//如果当前node还有子树则保留当前节点返回
        }
        return null;//当前key没有任何value，其子结点也没有，则删除这个key。
    }

    /**
     * 获得全部的key
     * */
    public Iterable<String> keys() {
        //获取所有的keys，就是收集以空字符开头的key
        return keysWithPrefix("");
    }
    /**
     * 获得以某个字符串开头的全部keys
     * */
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> queue = new LinkedList<>();
        //调用get，代表先到达前缀所在的那个节点，再向下收集
        collect(get(pre, root, 0), pre, queue);
        return queue;
    }

    //在给定前缀的节点后收集所有的字符
    private void collect(Node node, String pre, Queue<String> queue) {
        if (node == null) return;
        if (node.value != null) queue.add(pre);//找到了一个以pre为前缀的key
        for (int i = 0;i < node.nextNodes.length;i++) {
            //此处因为字母表的原因，只写出大概意思，pre值应该更新为pre加上当前子结点代表的字符
            collect(node.nextNodes[i], pre+i, queue);
        }
    }

}
//节点类
//Java泛型不支持数组
class Node {
    Object value;
    Node[] nextNodes;

    Node(int n) {
        nextNodes = new Node[n];
    }
}