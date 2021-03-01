package roueche.project6;

public interface UnionFind<E> {
	public void makeSet(E x);
	public void union(E x, E y);
	public E find(E x);
}
