public class Solution {
    public static void main(String[] args) {
        ListNode p1 = new ListNode(1);
        /*p1.next = new ListNode(4);
        p1.next.next = new ListNode(3);*/

        ListNode p2 = new ListNode(9);
        p2.next = new ListNode(9);
        p2.next.next = new ListNode(9);

        ListNode result = solute(p1,p2);
        output(result);
    }
    //带头结点的链表
    private static ListNode solute(ListNode p1, ListNode p2){
        ListNode result = new ListNode(0);
        ListNode ptr = result;

        //两个链表先相加
        while (p1 != null&&p2 != null){
            ListNode tmp = new ListNode(p1.val + p2.val);
            ptr.next = tmp;

            ptr = ptr.next;
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1 == null){
            ptr.next = p2;
        }else if(p2 == null){
            ptr.next = p1;
        }
        //处理进位
        ptr = result.next;
        while (ptr.next != null){
            int plus = 0;
            if (ptr.val >= 10){
                plus = ptr.val/10;
                ptr.val = ptr.val%10;
            }
            ptr.next.val = ptr.next.val + plus;
            ptr = ptr.next;
        }
        if (ptr.val >= 10){
            ptr.next = new ListNode(ptr.val/10);
            ptr.val = ptr.val%10;
        }
        return result.next;
    }
    private static class ListNode {
        int val;
        ListNode next;
        ListNode(){
        }
        ListNode(int x){
            val = x;
        }
    }
    private static void output(ListNode p){
        do {
            System.out.print(p.val + "->");
            p = p.next;
        }while (p!=null);
    }
}
