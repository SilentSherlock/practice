package problem;

import tools.ListNode;

/**
 * Date:2020/9/6
 * Description: optional class description
 **/
public class NewCoder {

    public static void main(String[] args) {
        ListNode pHead1 = new ListNode(2);
        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(5);
        ListNode node3 = new ListNode(5);
        ListNode pHead2 = new ListNode(8);
        pHead1.next = node1;
        node1.next= node2;
        pHead2.next = node3;
        node3.next = node2;


    }




    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode node1 = pHead1;
        ListNode node2 = pHead2;
        while (node1 != null) {
            node1.val = -1;
            node1 = node1.next;
        }
        while (node2 != null) {
            node2.val = 0;
            node2 = node2.next;
        }

        node1 = pHead1;
        while (node1 != null) {
            if (node1.val == 0) return node1;
            node1 = node1.next;
        }
        return null;
    }
}