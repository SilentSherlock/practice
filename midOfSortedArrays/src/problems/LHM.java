package problems;


import java.util.HashMap;
import java.util.Map;

/**
 * Date:2020.5.25
 * Description: realize LinkedHashMap
 **/
public class LHM {

    private Map<Integer, Node> cacheMap;
    private int size;
    private int capacity;
    private Node head, tail;//带头结点和尾节点，方便链表操作

    public LHM (int capacity) {
        cacheMap = new HashMap<>();
        size = 0;
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.after = tail;
        tail.before = head;
    }

    class Node {
        int key;
        int value;
        Node before;
        Node after;
        Node(){}
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(int key, int value) {
        Node node = cacheMap.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        }else {
            node = new Node(key, value);
            cacheMap.put(key, node);
            if (size == capacity) {
                Node tmp = removeTail();
                cacheMap.remove(tmp.key);
                addToHead(node);
            }else {
                addToHead(node);
                size++;
            }
        }
    }

    public int get(int key) {
        Node node = cacheMap.get(key);
        if (node != null) {
            moveToHead(node);
            return node.value;
        }
        return -1;
    }

    //摘下尾部节点
    private Node removeTail() {
        Node node = tail.before;
        removeNode(node);
        return node;
    }

    //在头部插入节点
    private void addToHead(Node node) {
        node.after = head.after;
        node.after.before = node;
        head.after = node;
        node.before = head;
    }

    //摘下节点
    private void removeNode(Node node) {
        node.before.after = node.after;
        node.after.before = node.before;
    }

    //将节点添加到头部
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
}
