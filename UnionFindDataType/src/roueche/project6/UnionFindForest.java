package roueche.project6;
import java.util.TreeMap;

public class UnionFindForest<E> implements UnionFind<E> {

	private class Node {
		private E value; // stores value
		private Node parent; // stores parent
		private int rank; // stores position
		
		public Node(E v) {
			value = v;
			parent = null;
			rank = 0;
		}
	}
	
	private TreeMap<E, Node> tree;
	
	public UnionFindForest() {
		tree = new TreeMap<>();
	}
	
	@Override
	public void makeSet(E x) { // makes a new set
		if(!tree.containsKey(x)) { // if doesn't contain key, make newNode with x and assign the parent to newNode, then put into tree
			Node newNode = new Node(x);
			newNode.parent = newNode;
			tree.put(x, newNode);
		}
	}
	
	@Override
	public void union(E x, E y) { // combine Nodes
		Node tempX = tree.get(find(x)); // stores names in Node
		Node tempY = tree.get(find(y));
		
		if(tempX == tempY) {
			return;
		}
		
		if(tempX.rank == tempY.rank) { // checks rank
			tempY.parent = tempX;
			tempX.rank++;
		} else if (tempX.rank > tempY.rank) { // if bigger assign Y to X
			tempY.parent = tempX;
			tempX.rank++;
		} else { // if smaller assign X to Y
			tempX.parent = tempY;
			tempY.rank++;
		}
	}
	
	private Node findHelper(Node n) { // helper to cycle through parents
		if(n.parent == n) {
			return n;
		}
		n.parent = findHelper(n.parent);
		return n.parent;
		
	}
	
	@Override
	public E find(E x) { // recursive to find value
		if(!tree.containsKey(x)) {
			throw new IllegalStateException("No key");
		}
		return findHelper(tree.get(x)).value;
	}
}
