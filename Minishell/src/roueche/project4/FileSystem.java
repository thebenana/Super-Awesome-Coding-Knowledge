package roueche.project4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack; // uses Java's Stack

public class FileSystem implements Serializable {
	
	public class Node implements Serializable {
		private Node parent;
		private ArrayList<Node> children;
		private String name;
		private boolean isDirectory;
		
		public Node(String n, Node p, boolean d) {
			this.parent = p;
			this.name = n;
			this.children = new ArrayList<Node>();
			this.isDirectory = d;
		}
		
		public boolean isDirectory() {
			return isDirectory;
		}
		
		public ArrayList<Node> children() {
			return this.children;
		}
		
		public void appendChild(String n, boolean d) {
			children.add(new Node(n, this, d)); // makes new child with "this" being the same as currentDir
		}
		
		public boolean isRoot() {
			if(parent == null) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private Node root;
	private Node currentDirectory;
	
	public FileSystem() {
		this.root = new Node("root", null, true); // makes root node
		this.currentDirectory = root; // assigns root node as the overarching node
	}
	
	private void checkMakeFile(String name) throws Exception { // checks to see if file name exists
		for(Node n : currentDirectory.children) {
			if(n.name.equals(name)) {
				throw new IllegalArgumentException("Has name already");
			}
		}
	}
	
	public void ls() {
		if(currentDirectory.children != null) {
			for(Node n : currentDirectory.children) {
				System.out.println(n.name); // for each child, print the name to display all sub-files/dirs
			}
		} else {
			System.out.println(""); // just a little something to make it show that there are no children in dir
		}
	}
	
	public void mkdir(String name) throws Exception {
		checkMakeFile(name); // checks name
		currentDirectory.appendChild(name, true); // adds new dir as a child
	}
	
	public void touch(String name) throws Exception {
		checkMakeFile(name); // checks name
		currentDirectory.appendChild(name, false); // adds new file as a child
	}
	
	public void cd(String name) throws Exception {
		if(name.equals("..") && currentDirectory != root) {
			currentDirectory = currentDirectory.parent; // puts you in the parent dir if ".." is entered as a name
			return;
		} else if(name.equals("..") && currentDirectory == root) {
			throw new IllegalArgumentException("Can't go back, already at root"); // handles if you're already in root
		}
		for(Node n : currentDirectory.children) {
			if(n.name.equals(name) && !n.isDirectory) {
				throw new IllegalArgumentException("Can't cd a file");
			} else if(n.name.equals(name) && n.isDirectory) {
				currentDirectory = n; // puts you in the dir you entered once conditions are checked
				return;
			}
		}
	}
	
	public void rm(String name) throws Exception {
		for(int i = 0; i < currentDirectory.children.size(); i++) {
			Node n = currentDirectory.children.get(i); // get child at given index
			if(n.name.equals(name) && n.isDirectory == false) {
				currentDirectory.children.remove(n); // remove child 
				return;
			} 
			if(n.name.contentEquals(name) && n.isDirectory() == true) {
				throw new IllegalArgumentException("Is a dir, not a file");
			}
		}
		throw new IllegalArgumentException("File does not exist");
	}
	
	public void rmdir(String name) throws Exception { // same as "rm"
		for(int i = 0; i < currentDirectory.children.size(); i++) {
			Node n = currentDirectory.children.get(i);
			if(n.name.equals(name) && n.isDirectory == true) {
				if(!n.children.isEmpty()) {
					throw new IllegalArgumentException("Empty");
				}
				currentDirectory.children.remove(n);
				return;
			} 
			if(n.name.contentEquals(name) && n.isDirectory() == false) { // changed from true to false to show that it's a dir
				throw new IllegalArgumentException("Is a file, not a dir");
			}
		}
		throw new IllegalArgumentException("Dir does not exist");
	}
	
	public String tree() {
		StringBuilder sb = new StringBuilder();
		treeHelper(sb, currentDirectory, 0); // starts at current dir
		return sb.toString();
	}
	
	private void treeHelper(StringBuilder sb, Node n, int level) { // helper
		if(n == null) { // handles null
            return;
        }

        for(int i = 0; i < level; i++) { // handles adding extra spaces
            sb.append("  ");
        }
        
        sb.append(n.name + "\n"); // inserts name and makes a new line
        
        if(n.isDirectory) { // if it's a directory, go through the children and recursively handle them until root dir is reached
        	for(int i = 0; i < n.children.size(); i++) {
                treeHelper(sb, n.children.get(i), level + 1);
            }
        }
        
    }
	
	public String pwd() {
		Stack<String> stack = new Stack<>();
		StringBuilder sb = new StringBuilder();
		Node n = currentDirectory; // a temp node
		
		while(n != null) {
			stack.push(n.name); // push names into a stack until null
			n = n.parent; // assign to parent 
		}
		while(stack.size() != 0) {
			sb.append(stack.pop() + "/"); // pop stack into stringbuilder to print name
		}
		return sb.toString();
    }
}
