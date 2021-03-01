package roueche.project1;

public class SinglyLinkedList<E> {
	private class Node {
		private E value;
		private Node next;
		
		public Node(E v, Node n) {
			this.value = v;
			this.next = n;
		}
	}
	
	private Node head;
	private int size;
	
	public SinglyLinkedList() {
		this.size = 0;
		this.head = null;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void addFirst(E newValue) {
		Node newNode = new Node(newValue, this.head);
		this.head = newNode;
		size++;
	}
	
	public void addLast(E newValue) {
		Node newNode = new Node(newValue, null);
		if(head != null) {
			Node temp = head;
			while(temp.next != null) {
				temp = temp.next;
			}
			temp.next = newNode;
		} else {
			this.head = newNode;
		}
		size++;
	}
	
	public E get(int index) {
		E toReturn;
		Node current = head;
		
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			for(int i = 0; i < index; i++) {
				current = current.next;
			}
			toReturn = current.value;
			return toReturn;
		}
	}
	
	public E removeLast() { 
		E toReturn = null;
		Node temp = head;
		
		if(isEmpty()) {
			throw new IllegalStateException();
		}
		if(size == 1) {
			toReturn = this.head.value;
			size--;
			this.head = null;
			return toReturn;
		} else {
			while(temp.next.next != null) {
				temp = temp.next;
			}
			toReturn = temp.next.value;
			temp.next = null;
			size--;
			return toReturn;
		}
	} 
	
	public E removeFirst() { 
        E toReturn = null;
		if(isEmpty()) {
        	throw new IllegalStateException();
        }
		if(size == 1) {
			toReturn = this.head.value;
			size--;
			this.head = null;
			return toReturn;
		} else {
			toReturn = this.head.value;
			size--;
			this.head = head.next;
			return toReturn;
		}
        
    }
	
	public boolean remove(E val) {  
        if (head == null) {
            throw new IllegalStateException(); 
        }
   
        Node temp = head; 
  
        if (size == 1) { 
            if(this.head.value.equals(val)) {
            	this.head = null;
            	size--;
            	return true;
            } else {
            	return false;
            }
        } else if(head.value.equals(val)) {
        	this.head = head.next;
        	size--;
        	return true;
        } else {
        	while(temp.next != null && !temp.next.value.equals(val)) {
        		temp = temp.next;
        	}
        	if(temp.next == null) {
        		return false;
        	} else {
        		size--;
        		temp.next = temp.next.next;
        		return true;
        	}
        } 
    }
	
	public String toString() {
		Node curr = this.head;
		String result = "";
		
		while(curr != null) {
			result += curr.value + " ";
			curr = curr.next;
		}
		return result;
	}
	
}
