package project1;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class fHeap {
	
	private fHeapNode minNode;
	private int numNodes;
	
	public fHeap(){} // its a collection of tree so nothing to intialize 
	public boolean checkEmpty(){return (minNode==null);} //checking empty or not
	public void clear(){minNode=null; numNodes=0; }
	public fHeapNode minElement(){return minNode;}
	public int getSize(){return numNodes;}
	
	//possible operations insert , removemin, remove, decreasekey, meld, cascadecut, pairwise_combine
	
	//Operation 1: insert 
	
	public void insert(fHeapNode node, double x){
		node.key = x;
        if (minNode != null) {
            node.left = minNode;
            node.right = minNode.right;
            minNode.right = node;
            node.right.left = node;

            if (x < minNode.key) {
                minNode = node;
            }
        } 
         else {    minNode = node; } // first tree
         numNodes++;
	}	
	
	//Operation :- decrease the key of any node 
	public void decreaseKey(fHeapNode x, double k)
	    {
	        if (k > x.key) {
//	        	throw new IllegalArgumentException("entered value is wrong");
	        }
	        x.key = k;

	        fHeapNode tempParent = x.parent;

	        if ((tempParent != null) && (x.key < tempParent.key)) {
	            cut(x,tempParent);
	            cascadingCut(tempParent);
	        }

	        if (x.key < minNode.key) {
	            minNode = x;
	        }
	    }
	
	//operation :- cascade cut 
	private void cascadingCut(fHeapNode tempParent) {
		// trace back until mark field is false and make it true 
		fHeapNode temp= tempParent.parent;
		if(temp!=null){
			if(!tempParent.mark)//first time child removed
			{
				tempParent.mark=true;
			}
			else {
				//if already marked then we have to cut it from the parent 
				cut(tempParent,temp);
				cascadingCut(temp);// repeat till mark field is false 
			}
		}
		
	}
	
	// Operation :- Normal cut
	private void cut(fHeapNode x, fHeapNode tempParent) {
		 	// remove the node from doubley link list 
			x.left.right = x.right;
	        x.right.left = x.left;
	        tempParent.degree--;
	        
	        //Update parent child pointer
	        if (tempParent.child == x) {
	            tempParent.child = x.right;
	        }

	        if (tempParent.degree == 0) {
	            tempParent.child = null;
	        }
	         
	        // removed subtree must be added to root linklist 
	        x.left= minNode;
	        x.right=minNode.right;
	        minNode.right=x;
	        x.right.left= x;
	        x.parent=null;
	        x.mark=false;
	        
	       }
	
	// operation : meld two heap and result one heap 
	public fHeap meld(fHeap x, fHeap y){
		fHeap temp= new fHeap();
		if((x!=null) && (y!=null)){
			temp.minNode=x.minNode;
		if(temp.minNode!=null){
			if(y.minNode!=null){
				 temp.minNode.right.left = y.minNode.left;
                 y.minNode.left.right = temp.minNode.right;
                 temp.minNode.right = y.minNode;
                 y.minNode.left = temp.minNode;
                 if (y.minNode.key < x.minNode.key) {
                     temp.minNode = y.minNode;
                 }
			}
		}
		else {
            temp.minNode = y.minNode;
        }

        temp.numNodes = x.numNodes + y.numNodes;
	}
		return temp;
	}
	
	//Operaation :- remove min
	public fHeapNode removeMin(){
		fHeapNode temp= minNode;
		if (temp != null) {
            int numChilds = temp.degree;
            fHeapNode x = temp.child;
            fHeapNode tempChild;
            
            while(numChilds>0){
            	tempChild=x.right;
            	
            	//remove currently selected child
            	  x.left.right = x.right;
                  x.right.left = x.left;
                  // Add current selected child in the root list 
                  x.left = minNode;
                  x.right = minNode.right;
                  minNode.right = x;
                  x.right.left = x;
                  //set parent pointer as null
                  x.parent = null;
                  x = tempChild;
                  numChilds--;
            	}
            // all childs added to root list here we have do pairwise combine as well
            //first remove min element now 
            temp.left.right= temp.right;
            temp.right.left=temp.left;
            
            if (temp == temp.right) { // only single noded heap 
                minNode = null;
            } 
            else {
                minNode = temp.right;
                pairwiseCombine();
            }

            // decrement numNodes
            numNodes--;
        }

		return temp;
	}
	
	//Most important operation after doing remove min 
	private void pairwiseCombine() {
		double value= 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
		int size =((int) Math.floor(Math.log(numNodes) * value)) + 1; // maximum possible degree here
		
		//maximum number of possible degree
		List<fHeapNode> list =new ArrayList<fHeapNode>(size);
		
		for (int i = 0; i < size; i++) {
            list.add(null);
        }
		
		// we first find number of total root nodes 
		int roots=0;
		fHeapNode temp= minNode;
		if (temp != null) {
            roots++;
            temp = temp.right;

            while (temp != minNode) {
                roots++;
                temp =temp.right;
            }
         }
		
		// Now find the tree with same degree and link them 
		while (roots > 0) {
            int d = temp.degree;
            fHeapNode nextNode = temp.right;
            
            // find the other tree of same degree
            while(true){
            	fHeapNode k = list.get(d);
                if (k == null) {
                    break;
                }
                
                // min node update here
                if (temp.key > k.key) {
                    fHeapNode temp1 = k;
                    k = temp;
                    temp = temp1;
                }
                
                // Now 2 trees merge into one , k tree will disappear 
                merge(k,temp);
                list.set(d,null);
                d++;
            }
            list.set(d,temp);
            temp=nextNode;
            roots--;
		
	}
		minNode=null;
		
		// Now we must left with trees with different degree
		
		for (int i = 0; i < size; i++) {
            fHeapNode y = list.get(i);
            if (y == null) {
                continue;
            }
            if (minNode != null) {
                y.left.right = y.right;
                y.right.left = y.left;

                // constructing root list again
                y.left = minNode;
                y.right = minNode.right;
                minNode.right = y;
                y.right.left = y;

                // checking min pointer is right
                if (y.key < minNode.key) {
                    minNode = y;
                }
            } 
            else {
                minNode = y;
            }
        }
    }
	
	 private void merge(fHeapNode y, fHeapNode x)
	    {
	        y.left.right = y.right;
	        y.right.left = y.left;
	        y.parent = x;

	        if (x.child == null) {
	            x.child = y;
	            y.right = y;
	            y.left = y;
	        } else {
	            y.left = x.child;
	            y.right = x.child.right;
	            x.child.right = y;
	            y.right.left = y;
	        }

	        x.degree++;
	        y.mark = false;
	    }
	 
	 public void delete(fHeapNode x)
	    {
	        decreaseKey(x, Double.NEGATIVE_INFINITY);
	        removeMin();
	    }
	 
	   public fHeapNode search(int x){
		   Stack<fHeapNode> stack = new Stack<fHeapNode>();
	        stack.push(minNode);
	        
	        //Breath first searching
	        while (!stack.empty()) {
	            fHeapNode curr = stack.pop();
	            
	        	if(curr.data==x) {return curr;}
	            
	            else{
	            	if (curr.child != null) {
	            		stack.push(curr.child);
	            	}
	            	fHeapNode start = curr;
	            	curr = curr.right;

	            	while (curr != start) {

	            		if (curr.child != null) {
	            			stack.push(curr.child);
	            		}
	            		if(curr.data==x) {return curr;}
	            		curr = curr.right;
	            	}
	       
	            }
	        }  
	        
	        
	        return null;
	        
	        
	   }
	    

	 
	 
}
