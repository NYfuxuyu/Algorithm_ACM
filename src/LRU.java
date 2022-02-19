import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/19 13:57
 */

class DoubleLinkedNode{
    int key;
    int value;
    DoubleLinkedNode prev;
    DoubleLinkedNode next;

    public DoubleLinkedNode() {
    }
    public DoubleLinkedNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
public class LRU {
    private int size;
    private int capacity;
    private Map<Integer, DoubleLinkedNode> cache;
    DoubleLinkedNode head, tail;

    public LRU(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        cache = new HashMap<>();
        head = new DoubleLinkedNode();
        tail = new DoubleLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public static void main(String[] args) {
        LRU lRUCache  = new LRU(2);
        lRUCache.put(1, 1);
        lRUCache.put(2, 2);
        System.out.println(lRUCache.get(1));
        lRUCache.put(3, 3);
        System.out.println(lRUCache.get(2));
        lRUCache.put(4, 4);
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(4));
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        DoubleLinkedNode temp = cache.get(key);
        moveToFirst(temp);
        return temp.key;
    }
    public void put(int key, int value) {
        DoubleLinkedNode node = cache.get(key);
        if (!cache.containsKey(key)) {
            DoubleLinkedNode newNode = new DoubleLinkedNode(key, value);
            cache.put(key, newNode);
            addFirst(newNode);
            ++size;
            if (size > capacity) {
                DoubleLinkedNode tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
            }else {
            node.value = value;
            moveToFirst(node);
        }

    }
    public void addFirst(DoubleLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    public void removeNode(DoubleLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
    }
    public void moveToFirst(DoubleLinkedNode node) {

        removeNode(node);
        addFirst(node);
    }
    public DoubleLinkedNode removeTail() {
        DoubleLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}
