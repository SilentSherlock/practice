package Solution;


/**
 * Date:2020.5.16
 * Description: optional class description
 **/
public class Test {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        /*head.next = new ListNode(2);*/
        /*head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);*/

        ListNode tmp = new Solution().reverseKGroup(head, 1);
        while (tmp != null) {
            System.out.println("tmp is " + tmp.val);
            tmp = tmp.next;
        }
    }
}
