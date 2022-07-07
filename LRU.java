import java.util.*;

class Node{
    public int key;
    public int value;
    public Node next;
    public Node prev;

    public Node(int key, int value){
        this.key = key; 
        this.value = value;
    }
}

interface DoublyLinkedList{
    public abstract void addFirst(Node listNode);
    public abstract void remove_node(Node listNode);
    public abstract void moveToHead(Node listNode);
}


//Genericに後で変更
class GenericDoublyLinkedList implements DoublyLinkedList{
    public Node head;
    public Node tail;

    public GenericDoublyLinkedList(){
        this.head = null;
        this.tail = null;
    }

    public Node search(int key){
        Node iterator = this.head;
        while (true){
            if (iterator.key == key) return iterator;
            iterator = iterator.next;
        }
        
    }

    //先頭に挿入する
    public void addFirst(Node listNode){
        if (this.head == null){
            this.head = listNode;
            this.tail = listNode;
        }
        else{
            this.head.prev = listNode;
            listNode.next = this.head;
            listNode.prev = null;
            this.head = listNode;
        }
    }

    //消去する
    public void remove_node(Node listNode){
        if (listNode == this.tail){
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        else if (listNode == this.head){
            this.head = this.head.next;
            this.head.prev = null;
        }
        else {
            listNode.prev.next = listNode.next;
            listNode.next.prev = listNode.prev;
        }
    }

    //消去して、先頭に入れ直す
    public void moveToHead(Node listNode){
        this.remove_node(listNode);
        this.addFirst(listNode);
    }
}

class LruCache{
    public int capacity;

    HashMap<Integer, Integer> keyindex = new HashMap<>();
    GenericDoublyLinkedList linkedListCache = new GenericDoublyLinkedList();
    
    public LruCache(int capacity){
        this.capacity = capacity;
    }

    public int get(int key){
        if (!this.keyindex.containsKey(key)){
            return -1;
        }
        else {
            this.linkedListCache.moveToHead(this.linkedListCache.search(key));
            return this.keyindex.get(key);
        }
    }

    public void put(int key, int value){
        if (this.keyindex.containsKey(key)){
            this.linkedListCache.moveToHead(linkedListCache.search(key));
            this.keyindex.replace(key, value);
        }
        else {
            this.keyindex.put(key, value);
            this.linkedListCache.addFirst(new Node(key, value));

            if (this.keyindex.size() > capacity){
                this.keyindex.remove(this.linkedListCache.tail.key, this.linkedListCache.tail.value);
                this.linkedListCache.remove_node(this.linkedListCache.tail);
            }
        }
    }
}

class Main{
    public static void main(String[] args){
        LruCache LRU = new LruCache(5);
        LRU.put(3,8);
        LRU.put(6,2);
        LRU.put(1,5);
        LRU.put(4,1);
        LRU.put(2,3);
        System.out.println(LRU.get(6));
    }
}