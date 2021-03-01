package roueche.project3;

public class QueueDoublyLinkedList<E> implements Queue<E> {
	private DoublyLinkedList<E> queue;
	
	public QueueDoublyLinkedList(){
		queue = new DoublyLinkedList<>();
	}

	@Override
	public E dequeue() {
		// TODO Auto-generated method stub
		return queue.removeHead();
	}

	@Override
	public void enqueue(E v) {
		// TODO Auto-generated method stub
		queue.addLast(v);
	}

	@Override
	public E first() {
		// TODO Auto-generated method stub
		return queue.get(1);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return queue.isEmpty();
	}
	
	public String toString() {
		return queue.toString();
		
	}

}
