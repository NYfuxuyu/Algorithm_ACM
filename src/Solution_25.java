import java.util.List;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/19 18:19
 */
class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int value, ListNode next) {
        this.val = value;
        this.next = next;
    }

    public ListNode() {
    }
}

public class Solution_25 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode head2 = head;
        for (int i = 2; i <= 5; i++) {
            head2.next = new ListNode(i);
            head2 = head2.next;
        }
        printList(head);
        ListNode res = reverseKGroup(head, 2);
        printList(res);
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy, end = dummy;
        while (end.next != null) {
            for(int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }
            ListNode next = end.next;
            end.next = null;
            ListNode start = pre.next;
            pre.next = reverse(start);
            start.next = next;
            pre = start;
            end = start;
        }
        return dummy.next;
    }

    public static ListNode reverse(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }
}
