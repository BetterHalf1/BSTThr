import java.util.Iterator;
//BY KENNETH TANG AND NATHAN TRAN

public class BSTThr<Key extends Comparable<Key>, Value> extends BST<Key, Value> {

    //Instance variables here
    //Note that the Node class is inherited and accessible

    private Node parent = null;

    public BSTThr() {
        super();//call constructor of base class
        //initialize instance variables of this class
    }


    //Taken from BST.java
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }


    //Override private put(Node, Key, Value) of BST
    //NO NEED TO OVERRIDE THE PUBLIC VERSION!
    //THIS METHOD REQUIRES THE MOST WORK!
    protected Node put(Node x, Key key, Value val) {
        if (x == null) {
            Node newNode = new Node(key, val, 0);
            if (parent != null) {
                int cmp = parent.key.compareTo(key);
                threadHelper(newNode, cmp); //updates prev and next for necessary nodes
            }
            return newNode;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            parent = x;
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            parent = x;
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    public Iterable<Pair<Key, Value>> pairs() {
        //Create PairList (see below) in here, returns all pairs.
        Node first = getLoNode();
        Node last = getHighNode();
        return new PairList(first, last);
    }

    public Iterable<Pair<Key, Value>> pairs(Key lo, Key hi) {
        //Create PairList in here (see below), returns pairs between lo and hi.
        Node first = getNode(lo);
        Node last = getNode(hi);

        return new PairList(first, last);
    }

    //Inner class for Iterable<Pair<Key, Value>>.
//To create value returned by pairs().
    private class PairList implements Iterable<Pair<Key, Value>> {
        //instance variables

        private Node firstNode;
        private Node lastNode;
        private Node currentNode;

        public PairList(Node first, Node last) {
            //determine first and last node INITIALIZE
            currentNode = firstNode = first;
            lastNode = last;

        }

        public Iterator<Pair<Key, Value>> iterator() {
            //Create PairIterator in here (see below), eventually return that object
            return new PairIterator();
        }

        //Inner inner class for return value of iterator()
        private class PairIterator implements Iterator<Pair<Key, Value>> {
            //instance variable(s)
            private Key k;
            private Value v;
            private MyPair currentPair;

            public PairIterator() {
                //determine first element
                k = firstNode.key;
                v = firstNode.val;
                currentPair = new MyPair<>(k, v);

            }

            public Pair<Key, Value> next() {
                //determine what to return
                //advance to next
                k = currentNode.key;
                v = currentNode.val;
                currentPair = new MyPair<>(k, v);
                currentNode = currentNode.next;
                return currentPair;
            }

            public boolean hasNext() {
                //determine if there's a next element
                return currentNode != lastNode.next;
            }

            public void remove() {
                //no code here!
            }
        }
    }

    private class MyPair<Key, Value> implements Pair<Key, Value> {
        //instance variables
        Key key;
        Value val;

        public MyPair(Key k, Value v) {
            //init instance variables
            key = k;
            val = v;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return val;
        }

        public String toString() {

            return "Key: " + key + " Val: " + val;
        }
    }

    //Update prev and next for necessary nodes
    private void threadHelper(Node newNode, int cmp) {
        if (cmp > 0) {
            if (parent.prev != null) {
                parent.prev.next = newNode;
                newNode.prev = parent.prev;
            }
            newNode.next = parent;
            parent.prev = newNode;

        } else {
            if (parent.next != null) {
                parent.next.prev = newNode;
                newNode.next = parent.next;
            }
            newNode.prev = parent;
            parent.next = newNode;
        }
    }

    //return Node with Key k
    private Node getNode(Key k) {
        return getNode(root, k);
    }

    //helper method for above:
    //will find the next closest value within the range if key cannot be found
    private Node getNode(Node x, Key k) {
        //use binary search

        if (k == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp < 0)
            if (x.left == null) return x;
            else return getNode(x.left, k);
        else if (cmp > 0)
            if (x.right == null) return x;
            else return getNode(x.right, k);
        else return x;
    }

    private Node getLoNode() {
        return getLoNode(root);
    }

    private Node getLoNode(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private Node getHighNode() {
        return getHighNode(root);
    }

    private Node getHighNode(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    //To get you started with client:
    public static void main(String[] args) {
        BSTThr<String, Integer> st = new BSTThr<String, Integer>();
        String a = "abigail";
        String b = "benny";
        String c = "claris";
        String d = "derek";
        String e = "electric";

        System.out.println("Put abigail");
        st.put(a, 1);

        System.out.println("Put benny");
        st.put(b, 1);

        System.out.println("Put electric");
        st.put(e, 1);

        System.out.println("Put derek");
        st.put(d, 4);

        System.out.println("Put claris");
        st.put(c, 2);

        System.out.println("Put derek");
        st.put(d, 4);

        System.out.println(st.getNode("electric"));
        for (Pair x : st.pairs("abigail", "zebra"))
            System.out.println(x);

    }
}
