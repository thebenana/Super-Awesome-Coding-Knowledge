package roueche.project3;
public interface Queue<E> {
	public E dequeue();
	public void enqueue(E v);
	public E first();
	public int size();
	public boolean isEmpty();

}
