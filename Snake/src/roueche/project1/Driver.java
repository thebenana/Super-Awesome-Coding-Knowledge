// Ben Roueche, 04/10/20, Purpose: to make a SinglyLinkedList implementation

package roueche.project1;

public class Driver {

	public static void main(String[] args) {
		/**
		 * This driver tests the methods you have implemented in your singly linked list
		 * All these methods will be required to successfully implement project 1. To
		 * ensure that your methods are correct, all of the tests should PASS. If a test
		 * produces a FAIL, then you have an issue with your implementation. This driver
		 * tests the following methods: addFirst(T value), removeFirst(), addLast(T
		 * value), removeLast(), toString(), isEmpty(), size(), get(int
		 * index) and remove(T value)
		 * 
		 */
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

		/*---- Testing addFirst(), removeFirst(), toString(), isEmpty() and size()--- */
		if (list.isEmpty()) {
			System.out.println("PASS: isEmpty works for an empty list");
		} else {
			System.err.println("FAIL: isEmpty fails for an empty list");
		}

		// Test list with one element
		list.addFirst(50);
		if (list.size() == 1) {
			System.out.println("PASS: size works for a list with one element");
		} else {
			System.err.println("FAIL: size fails for a list with one element");
		}
		System.out.println("---");

		if (list.removeFirst() == 50 && list.isEmpty()) {
			System.out.println("PASS: removeFirst works for single element");
		} else {
			System.err.println("FAIL: removeFirst failed for single element");
		}
		
		// Testing addFirst by adding 10 elements
		for (int i = 10; i <= 100; i += 10) {
			list.addFirst(i);
		}
		if (list.toString().equals("100 90 80 70 60 50 40 30 20 10 ") && list.size() == 10) {
			System.out.println("PASS: addFirst works when adding 10 elements");
			System.out.println("PASS: toString works for a list with 10 elements");
		} else {
			System.err.println("FAIL: addFirst failed when adding 10 elements");
		}

		// Testing isEmpty and removeFirst
		while (!list.isEmpty()) { // as long as the list is not empty, keep removing the first element
			list.removeFirst();
		}

		if (list.toString().equals("") && list.isEmpty()) {
			System.out.println("PASS: removeFirst works when removing all the elements");
		} else {
			System.err.println("FAIL: removeFirst fails when removing all the elements");
		}

		// Testing removeFirst on an empty list
		try {
			System.err.println("FAIL: " + list.removeFirst());
		} catch (IllegalStateException e) {
			System.out.println("PASS: removeFirst throws an exception for empty list");
		}

		/*---- Testing addLast(), removeLast(), toString() and size() --- */
		System.out.println("---");
		// Test list with one element
		list.addLast(50);

		if (list.removeLast() == 50 && list.isEmpty()) {
			System.out.println("PASS: removeLast works for single element");
		} else {
			System.err.println("FAIL: removeLast failed for single element");
		}
		// Testing addLast
		for (int i = 10; i <= 100; i += 10) {
			list.addLast(i);
		}
		if (list.toString().equals("10 20 30 40 50 60 70 80 90 100 ") && list.size() == 10) {
			System.out.println("PASS: addLast works when adding 10 elements");
		} else {
			System.err.println("FAIL: addLast failed when adding 10 elements");
		}

		// Testing isEmpty and removeLast
		while (!list.isEmpty()) {
			list.removeLast();
		}
		if (list.toString().equals("") && list.isEmpty()) {
			System.out.println("PASS: removeLast works when removing all the elements");
		} else {
			System.err.println("FAIL: removeLast fails when removing all the elements");
		}

		// Testing removeLast on an empty list
		try {
			System.err.println("FAIL: " + list.removeLast());
		} catch (IllegalStateException e) {
			System.out.println("PASS: removeLast throws an exception for empty list");
		}

		System.out.println("---");

		// Testing get()
		for (int i = 10; i <= 100; i += 10) {
			list.addFirst(i);
		}

		if (list.get(0) == 100) {
			System.out.println("PASS: get(0) works for 10 elements");
		} else {
			System.err.println("FAIL: get(0) failed for 10 elements");
		}

		try {
			System.err.println("FAIL: " + list.get(14));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("PASS: get(14) throws an IndexOutOfBounds exception for 10 elements");
		}

		System.out.println("---");

		// Testing remove(T value)
		if (list.remove(100) && list.toString().equals("90 80 70 60 50 40 30 20 10 ")) {
			System.out.println("PASS: remove(100) works removes the element 100");
		} else {
			System.err.println("FAIL: get(0) failed for 10 elements");
		}

		if (list.remove(200)) {
			System.err.println("FAIL: remove(200) should return false because 200 isn't in the list.");
		} else {
			System.out.println("PASS: the element 200 isn't in the list, so remove(200) correctly return false");
		}

	}

}
