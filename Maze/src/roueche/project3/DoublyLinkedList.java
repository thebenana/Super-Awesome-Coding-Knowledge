package roueche.project3;

import java.util.NoSuchElementException;


public class DoublyLinkedList<E> implements Iterable<E> {
	
	
	private class Node {
		private E value;
		private Node prev;
		private Node next;
		
		public Node(E value, Node prev, Node next) {
			this.value = value;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private class DLLIterator implements TwoWayIterator<E> {
		// current will be pointing to the next node to
		// give to next(). This is not to be confused with
		// the "next" pointer inside a Node. 
		private Node current;
		
		// Since the very first call to next() should return
		// the first element in the list, current will initially
		// point to the head.
		public DLLIterator() {
			current = header.next;
		}
		
		@Override
		public boolean hasNext() {
			// We cannot provide another element to next() if
			// current is past the end of the list.
			return current.next != null;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			// Since the current node is what we return to next(),
			// we have to store the value temporarily before
			// moving the current point to the next node.
			E result = current.value;
			current = current.next;
			return result;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return current.prev != null;
		}

		@Override
		public E previous() throws NoSuchElementException {
			 if(!hasPrevious()){
	                throw new NoSuchElementException("there is no elemnt left");
	         }
	         E valueToReturn = current.value;
	         current = current.prev;
	         return valueToReturn;
		}
	}
	
	protected Node header;
	protected Node trailer;
	private int size;
	
	public DoublyLinkedList() {
		header = new Node(null, null, null);
		trailer = new Node(null, header, null);
		header.next = trailer;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return header.next == trailer;
	}
	
	private void insertBetween(E newValue, Node node1, Node node2) {
		Node newNode = new Node(newValue, node1, node2);
		node1.next = newNode;
		node2.prev = newNode;
		size++;
	}

	private E removeBetween(Node node1, Node node2) {
		if (this.isEmpty()) {
			throw new IllegalStateException("Cannot remove from empty list");
		}
		E result = node1.next.value;
		
		node1.next = node2;
		node2.prev = node1;
		size--;
		
		return result;
	}
	
	public void insertAtHead(E newValue) {
		insertBetween(newValue, header, header.next);
	}
	
	public void addLast(E newValue) {
		insertBetween(newValue, trailer.prev, trailer);
	}
	
	public boolean remove(E target) {
		Node current = header.next;
		while (current != trailer) {
			if (current.value.equals(target)) {
				removeBetween(current.prev, current.next);
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	public E removeHead() {
		return removeBetween(header, header.next.next);
	}
	
	public E removeTail() {
		return removeBetween(trailer.prev.prev, trailer);
	}
	
	public String toString() {
		if (this.isEmpty()) {
			return "List is empty";
		}
		String result = "";
		Node current = header.next;
		while (current != trailer) {
			result += current.value.toString() + " ";
			current = current.next;
		}
		return result;
	}
	
	public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            Node current = header;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.value;
        }
    }
	
	public String reverseToString() {
		if (this.isEmpty()) {
			return "List is empty";
		}
		String result = "";
		Node current = trailer.prev;
		while (current != header) {
			result += current.value.toString() + " ";
			current = current.prev;
		}
		return result;
	}
	public TwoWayIterator<E> iterator() {
		return new DLLIterator();
	}
}
