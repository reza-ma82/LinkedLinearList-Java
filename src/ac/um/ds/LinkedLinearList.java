package ac.um.ds;

public class LinkedLinearList<T> implements LinearList<T> {
    private class Node<T> {
        public T data;
        public Node prev;
        public Node next;
    }

    public class LinkedListForwardIterator<T> implements LinearList.LinearListForwardIterator<T> {
        private Node<T> currNode;

        // constructors:
        public LinkedListForwardIterator() {
            this.currNode = null;
        }

        public LinkedListForwardIterator(Node<T> node) {
            this.currNode = node;
        }

        @Override
        public T next() {
            currNode = currNode.next;
            return (T) currNode.data;
        }

        @Override
        public boolean hasNext() {
            return !currNode.next.equals(headerNode);
        }
    }

    public class LinkedListBackwardIterator<T> implements LinearList.LinearListBackwardIterator<T> {
        private Node<T> currNode;

        public LinkedListBackwardIterator() {
            this.currNode = null;
        }

        public LinkedListBackwardIterator(Node<T> node) {
            this.currNode = node;
        }

        @Override
        public T next() {
            currNode = currNode.prev;
            return (T) currNode.data;
        }

        @Override
        public boolean hasNext() {
            return !currNode.prev.equals(headerNode);
        }
    }

    public LinkedLinearList() {
        mSize = 0;
        headerNode = new Node<T>();
        headerNode.next = headerNode;
        headerNode.prev = headerNode;
    }

    public LinearListForwardIterator<T> forwardIterator() {
        return new LinkedListForwardIterator<T>(headerNode);
    }

    public LinearListBackwardIterator<T> backwardIterator() {
        return new LinkedListBackwardIterator<T>(headerNode);
    }

    @Override
    public LinearListForwardIterator<T> insert(LinearListForwardIterator<T> it, T data)
            throws IndexOutOfBoundsException {
        Node<T> newNode = new Node<>();
        newNode.data = data;
        if (mSize == 0) {
            headerNode.next = newNode;
            newNode.prev = headerNode;
            headerNode.prev = newNode;
            newNode.next = headerNode;
        } else {
            if (it.hasNext()) {
                Node currNode = ((LinkedListForwardIterator) it).currNode;
                Node next = currNode.next;
                currNode.next = newNode;
                newNode.prev = currNode;
                newNode.next = next;
                next.prev = newNode;
            } else {
                newNode.prev = headerNode.prev;
                headerNode.prev.next = newNode;
                newNode.next = headerNode;
                headerNode.prev = newNode;
            }
        }
        mSize++;
        return it;
    }

    @Override
    public LinearListBackwardIterator<T> insert(LinearListBackwardIterator<T> it, T data)
            throws IndexOutOfBoundsException {
        Node<T> newNode = new Node<>();
        newNode.data = data;
        if (mSize == 0) {
            headerNode.next = newNode;
            newNode.prev = headerNode;
            headerNode.prev = newNode;
            newNode.next = headerNode;
        } else {
            if (it.hasNext()) {
                Node currNode = ((LinkedListBackwardIterator) it).currNode;
                Node prev = currNode.prev;
                currNode.prev = newNode;
                newNode.next = currNode;
                newNode.prev = prev;
                prev.next = newNode;
            } else {
                newNode.prev = headerNode;
                headerNode.next.prev = newNode;
                newNode.next = headerNode.next;
                headerNode.next = newNode;
            }
        }
        mSize++;
        return it;
    }

    @Override
    public LinearListForwardIterator<T> remove(LinearListForwardIterator<T> it) throws IndexOutOfBoundsException {
        Node currNode = ((LinkedListForwardIterator) it).currNode;
        Node next = currNode.next;
        Node prev = currNode.prev;
        next.prev = prev;
        prev.next = next;
        LinkedListForwardIterator<T> newIt=new LinkedListForwardIterator<>(next);
        mSize--;
        return newIt;
    }

    @Override
    public LinearListBackwardIterator<T> remove(LinearListBackwardIterator<T> it) throws IndexOutOfBoundsException {
        Node currNode = ((LinkedListBackwardIterator) it).currNode;
        Node next = currNode.next;
        Node prev = currNode.prev;
        next.prev = prev;
        prev.next = next;
        LinkedListBackwardIterator<T> newIt=new LinkedListBackwardIterator<>(prev);
        mSize--;
        return newIt;
    }

    public int size() {
        return mSize;
    }

    @Override
    public String toString() {
        String str = "";
        Node currNode = headerNode;
        for (int i = 0; i < mSize; i++) {
            currNode = currNode.next;
            str += (" " + currNode.data);
        }
        return str;
    }

    private Node<T> headerNode;
    private int mSize;
}
