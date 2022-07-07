import java.util.*;
import java.lang.StringBuilder;

class Node<E>{
    public int key;
    public E value;
    public Node<E> next;
    public Node<E> prev;

    public Node(int key, E value){
        this.key = key; 
        this.value = value;
    }
}

class LRUCache<E>{
    protected HashMap<Integer, Node<E>> hashmap;
    protected int capacity;
    protected Node<E> head, tail;

    public LRUCache(int capacity){
        this.capacity = capacity;
        hashmap = new HashMap<>();
    }

    public void addFirst(Node<E> listNode){
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

    public void removeNode(Node<E> listNode){
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
    
    public void moveToHead(Node<E> listNode){
        this.removeNode(listNode);
        this.addFirst(listNode);
    }

    public E get(int key){
        if (this.hashmap.containsKey(key)){
            Node<E> node = this.hashmap.get(key);
            E result = node.value;
            moveToHead(node);
            return result;
        }
        return null;
    }

    public void put(int key, E value){
        if (this.hashmap.containsKey(key)){
            Node<E> node = hashmap.get(key);
            node.value = value;
            moveToHead(node);
        }
        else {
            Node<E> node = new Node<E>(key, value);
            this.hashmap.put(key, node);
            addFirst(node);

            if (this.hashmap.size() > capacity){
                this.hashmap.remove(tail.key);
                removeNode(tail);
            }
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder("[");
        Node<E> iterator = this.head;
        while (iterator != null){
            str.append("node(" + iterator.key + "," + iterator.value + "),");
            iterator = iterator.next;
        }
        return str.toString() + "]";
    }
}

class Main{
    public static void main(String[] args){
       LRUCache<Integer> integerLru = new LRUCache<Integer>(5);
       integerLru.put(3,8);
       System.out.println(integerLru);
       integerLru.put(4,21);
       System.out.println(integerLru);
       integerLru.put(56,8);
       System.out.println(integerLru);
       integerLru.put(1,8);
       System.out.println(integerLru);
       integerLru.put(89,8);
       System.out.println(integerLru);
       System.out.println(integerLru.get(4));
       System.out.println(integerLru);
       integerLru.put(9,8);
       System.out.println(integerLru);
    }
}



