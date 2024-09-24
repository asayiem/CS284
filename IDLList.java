import java.util.ArrayList;

/**
 * Abu Sayiem
 * CS 284 HW2
 * I pledge that I abide by the Stevens Honor System.
 */

public class IDLList<E> {

    //data fields

    private class Node<F> {
        private F data;
        private Node<F> next;
        private Node<F> prev;

        /**
         * creates a node with no prev or next
         * @param data
         */
        public Node(F data) {
            this.data = data;
            next = null;
            prev = null;
        }

        /**
         * creates a node with a prev or next
         * @param data
         * @param next
         * @param prev
         */
        public Node(F data, Node<F> next, Node<F> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    //data fields

    private Node<E> head;
    private Node<E> tail;
    private int size;
    private ArrayList<Node<E>> indices;

    //constructors

    /**
     * Creates an empty list
     */
    public IDLList() {
        head = null;
        tail = null;
        size = 0;
        indices = new ArrayList<>();
    }


    /**
     * adds an element to the given index
     * @param index
     * @param elem
     * @return true
     */
    public boolean add(int index, E elem) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            add(elem);
        } else {
            Node<E> current = indices.get(index);
            Node<E> newCurrent = new Node<>(elem, current, current.prev);
            current.prev.next = newCurrent;
            current.prev = newCurrent;
            size++;
            indices.add(index, newCurrent);
        }
        return true;
    }

    /**
     * adds an element to the head of the list
     * @param elem
     * @return true
     */
    public boolean add(E elem) {
        if (head == null) {
            head = new Node<E>(elem);
            tail = head;
        } else if (head == tail) {
            head = new Node<E>(elem, tail, null);
            tail.prev = head;
        } else {
            head = new Node<E>(elem, head, null);
            head.next.prev = head;
        }

        indices.add(0, head);
        size++;
        return true;
    }

    /**
     * adds an element to the end of the list
     * @param elem
     * @return true
     */
    public boolean append(E elem) {
        if (head == null) {
            head = new Node<E>(elem);
            tail = head;
            size++;
            return indices.add(head);
        }

        if (head == tail) {
            tail = new Node<E>(elem, null, head);
            head.next = tail;
            size++;
            return indices.add(tail);
        }

        tail.next = new Node<E>(elem, null, tail);
        tail = tail.next;
        size++;
        return indices.add(tail);
    }

    /**
     * returns the element at the given index
     * @param index
     * @return element at index
     */
    public E get(int index) {
        if (head == null || index < 0 || index >= size) {
            throw new IllegalArgumentException();
        } else {
            if (index == 0) {
                return head.data;
            } else {
                Node<E> current = head;
                int i = 0;
                while (i < index) {
                    current = current.next;
                    i++;
                }
                return current.data;
            }
        }
    }

    /**
     * returns the first element of a list
     * @return first element
     */
    public E getHead() {
        if (head == null) {
            throw new IllegalArgumentException("Empty List");
        }
        return head.data;
    }

    /**
     * returns the last element of a list
     * @return last element
     */
    public E getLast() {
        if (size == 0) {
            throw new IllegalArgumentException("Empty List");
        }
        return tail.data;
    }

    /**
     * returns the size of the list
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * removes and returns the element at the start of the list
     * @return removed element
     */
    public E remove() {
        if (head == null) {
            throw new IllegalStateException();
        }
        if (head == tail) {
            Node<E> temp = head;
            head = null;
            tail = null;
            size--;
            indices.clear();
            return temp.data;
        }
        Node<E> temp = head;
        head = head.next;
        indices.remove(0);
        size--;
        return temp.data;
    }

    /**
     * removes and returns the element at the end of the list
     * @return removed element
     */
	public E removeLast() {
		if (head == null) { //Empty list
            throw new IllegalStateException();
        }

        if (head == tail) { //Singleton list
            Node<E> temp = tail;
            head = null;
            tail = null;
            size = 0;
            indices.clear();
            return temp.data;
        }

        Node<E> temp = tail;
        tail = tail.prev;
        tail.next = null;
        indices.remove(size - 1);
        size--;
        return temp.data;
	}

    /**
     * removes and returns the element at the given index
     * @param index
     * @return removed element
     */
	public E removeAt(int index) {
		if (index < 0 || index > size) { //Illegal index
            throw new IllegalStateException("Illegal Index!");
        }

        if (index == 0) { // Head
            return remove();
        }

        if (index == size - 1) { // Tail
            return removeLast();
        }

        Node<E> current = indices.remove(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return current.data;
	}

    /**
     * removes the first element of the list which matches the given element. returns a boolean true if the element is found.
     * @param elem
     * @return boolean
     */
	public boolean remove(E elem) {
		if (elem.equals(head.data)) { // remove the first element
			remove();
			return true;
		}

		if (elem.equals(tail.data)) { // remove the last element
			removeLast();
			return true;
		}

		Node<E> current = head;
		int index = 0;
		while (current != null) {
			if (current.data.equals(elem)) {
				current.prev.next = current.next;
				current.next.prev = current.prev;
				indices.remove(index);
				size--;
				return true;
			}
			current = current.next;
			index++;
		}

		return false;
	}

    /**
     * returns the string representation of the list
     */

     public String toString() {
		Node<E> current = head;
		String s = "";
		while (current != null) {
			s = s + current.data + ",";
			current = current.next;
		}
		return s;
	}
}

