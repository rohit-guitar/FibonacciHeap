package project1;

public class fHeapNode {
	 int data;               // Vertex number
	 fHeapNode child;       // child field
	 fHeapNode left;        // Left sibling
	 fHeapNode right;       // Right sibling 
	 fHeapNode parent;      // Parent node
	 int degree;            // no. of childs
	 double key;               // key value
	 boolean mark;          // if child is removed then its true 
	 
	 
	 // Constructor 
	 public fHeapNode(int a , double b ){
		 this.data=a;
		 this.key=b;
		 right=this;
		 left=this;
	 }
	 
	 public double getKey(){return this.key;}
	
}
