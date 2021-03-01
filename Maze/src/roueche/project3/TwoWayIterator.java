package roueche.project3;

import java.util.Iterator;

public interface TwoWayIterator<E> extends Iterator<E> {
	public boolean hasPrevious();
	public E previous();
}
