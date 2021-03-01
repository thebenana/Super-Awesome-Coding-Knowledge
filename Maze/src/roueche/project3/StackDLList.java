package roueche.project3;

public class StackDLList<E> implements DLLstack<E> {

	private DoublyLinkedList<E> stack;
	
	public StackDLList() {
		stack = new DoublyLinkedList<>();
	}
	
	@Override
	public E pop() {
		return stack.removeTail();
	}

	@Override
	public void push(E a) {
		stack.addLast(a);
	}

	@Override
	public boolean isEmpty() {
		return stack.size() == 0;
	}

}
