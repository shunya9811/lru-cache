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

interface DoublyLinkedList<E>{
    public abstract void addFirst(Node<E> listNode);
    public abstract void remove_node(Node<E> listNode);
    public abstract void moveToHead(Node<E> listNode);
}


//Genericに後で変更
class GenericDoublyLinkedList<E> implements DoublyLinkedList<E>{
    public Node<E> head;
    public Node<E> tail;

    public GenericDoublyLinkedList(){
        this.head = null;
        this.tail = null;
    }
    
    //これでO(capacity)になってしまうから、アルゴリズム的に未完成
    public Node<E> search(int key){
        Node<E> iterator = this.head;
        while (true){
            if (iterator.key == key) return iterator;
            iterator = iterator.next;
        }
        
    }

    //先頭に挿入する
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

    //消去する
    public void remove_node(Node<E> listNode){
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
    public void moveToHead(Node<E> listNode){
        this.remove_node(listNode);
        this.addFirst(listNode);
    }
}

class genericLruCache<E>{
    public int capacity;

    HashMap<Integer, E> keyindex = new HashMap<>();
    GenericDoublyLinkedList<E> linkedList = new GenericDoublyLinkedList<E>();
    
    public genericLruCache(int capacity){
        this.capacity = capacity;
    }

    public E get(int key){
        if (!this.keyindex.containsKey(key)){
            return null;
        }
        else {
            this.linkedList.moveToHead(this.linkedList.search(key));
            return this.keyindex.get(key);
        }
    }

    public void put(int key, E value){
        if (this.keyindex.containsKey(key)){
            if (this.keyindex.get(key) != value){
                this.keyindex.replace(key, value);
                this.linkedList.remove_node(this.linkedList.search((key)));
                this.linkedList.addFirst(new Node<E>(key, value));
            }
            else {
                this.linkedList.moveToHead(this.linkedList.search(key));
            }
        }
        else {
            this.keyindex.put(key, value);
            this.linkedList.addFirst(new Node<E>(key, value));

            if (this.keyindex.size() > capacity){
                this.keyindex.remove(this.linkedList.tail.key, this.linkedList.tail.value);
                this.linkedList.remove_node(this.linkedList.tail);
            }
        }
    }

    public String toString(){
            StringBuilder str = new StringBuilder("[" + "|");
        Node<E> iterator = this.linkedList.head;
        while (iterator != null){
            str.append(iterator.key + "," + iterator.value + "|");
            iterator = iterator.next;
        }
        return str.toString() + "]";
    }
}

class Main{
    public static void main(String[] args){
        //genericLruCache<Integer> integerLRU = new genericLruCache<Integer>(5);
        //integerLRU.put(3,8);
        //System.out.println(integerLRU);
        //integerLRU.put(6,2);
        //System.out.println(integerLRU);
        //integerLRU.put(1,5);
        //System.out.println(integerLRU);
        //integerLRU.put(4,1);
        //System.out.println(integerLRU);
        //integerLRU.put(2,3);
        //System.out.println(integerLRU);
        //integerLRU.put(21,3);
        //System.out.println(integerLRU);
        //System.out.println(integerLRU.get(6));
        //System.out.println(integerLRU);
        

        genericLruCache<Character> characterLRU = new genericLruCache<Character>(3);
        characterLRU.put(2,'U');
        System.out.println(characterLRU);
        characterLRU.put(1,'B');
        System.out.println(characterLRU);
        characterLRU.put(4,'T');
        System.out.println(characterLRU);
        characterLRU.put(2,'E');
        System.out.println(characterLRU);
        characterLRU.put(32,'R');
        System.out.println(characterLRU);
        System.out.println(characterLRU.get(2));
        System.out.println(characterLRU);
    }
}