package Solution;


/**
 * Date:2020.5.16
 * Description: optional class description
 **/
public class Solution {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode nextHead = divideByK(head, k);
        ListNode tmpHead = head;
        if (nextHead != null && nextHead != tmpHead) {
            System.out.println("The first nextHead is " + nextHead.val);
            tmpHead = reverseList(tmpHead, nextHead);
            //merge(tmpHead, nextHead);
            head = tmpHead;
            System.out.println("tmpHead's val is " + head.val);
            tmpHead = nextHead;
            nextHead = divideByK(nextHead, k);
        }
        while (nextHead != null && nextHead != tmpHead) {
            tmpHead = reverseList(tmpHead, nextHead);
            merge(head, tmpHead);
            System.out.println("tmpHead's val is " + head.val);
            tmpHead = nextHead;
            nextHead = divideByK(nextHead, k);
        }
        if (tmpHead == nextHead) {
            if (tmpHead != head) {
                tmpHead = reverseList(tmpHead, null);
                merge(head, tmpHead);
            }else {
                tmpHead = reverseList(tmpHead, null);
                return tmpHead;
            }
        }else if (tmpHead != head) {
            merge(head, tmpHead);
        }
        return head;
    }

    private ListNode divideByK(ListNode head, int k) {
        ListNode tmp = head;
        int length = 0;
        while (tmp != null && length < k) {//tmp = null but length = k?
            length++;
            tmp = tmp.next;
        }
        if (tmp == null && length == k) {
            return head;
        }
        return tmp;
    }

    private ListNode reverseList(ListNode head, ListNode end) {
        ListNode next = head.next;
        ListNode tmp = head;
        tmp.next = null;
        while (next != end) {
            head = next;
            next = head.next;
            head.next = tmp;
            tmp = head;
        }
        return head;
    }

    private void merge(ListNode head, ListNode nextHead) {
        ListNode tmp = head;
        while (tmp.next != null) tmp = tmp.next;
        tmp.next = nextHead;
    }
}
