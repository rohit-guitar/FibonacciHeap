package project1;

import java.util.*;

public class GenGraph {
	private int nodes;
	private int density;
	private ArrayList<LinkedList<edge>> neighbourlist= null; // neighbour lists
	private int adjMatrix [] [] = null;   
	
	

	public GenGraph(int a, int b){
		this.nodes= a;
		this.density=b;
		neighbourlist = new ArrayList<LinkedList<edge>>(nodes);
		adjMatrix= new int[nodes] [nodes];
		for (int i=0;i<nodes;i++) neighbourlist.add(new LinkedList<edge>());
		 
	}
	
	public void makeGraph(){
		Random var = new Random();		
		for (int i=0; i < nodes; i++)
			   for (int j=0; j < nodes; j++)
			      adjMatrix[i][j] = 0;
		
		
		do{
			int i=0;
			int j=0;
			int weight=0;
			resetNeighbourList();
			int k=0;
			while(k<density){
				i=var.nextInt(nodes);
				j=var.nextInt(nodes);
				if(i!=j && adjMatrix[i][j]==0){
					weight= var.nextInt(1000) + 1;
					edge e1 = new edge(i, j, weight);
					neighbourlist.get(i).add(e1);
					edge e2 = new edge(j, i, weight);
					neighbourlist.get(j).add(e2);
					adjMatrix[i][j]=1;
					adjMatrix[j][i]=1;
					k++;
				}
			}
			
		}while(!checkConnected());
		
	}
	
	public void resetNeighbourList(){
		for(int i=0;i<nodes;i++){
			neighbourlist.get(i).clear();
		}
	}


	public boolean checkConnected(){  // checking neighbour list if all nodes are preent then true 
		
		//System.out.println("check");
		int[] a= new  int[nodes];
		for(int i=0;i<nodes;i++)  a[i]=0;
		
		for(int i=0;i<nodes;i++){
			LinkedList<edge> temp= neighbourlist.get(i);
			if(temp.isEmpty()) return false;
			a[i]=1;
			for(int j=0;j<temp.size();j++){
				edge e=temp.get(j);
				a[e.v2]=1;
			}
		}
		
		for(int i=0;i<nodes;i++){
			if(a[i]==0) return false;
		}
		return true;
		
	}
	
	public void print(){
		int c=0;
		for(int i=0;i<nodes;i++){
			LinkedList<edge> temp =neighbourlist.get(i);
			for(int j=0;j<temp.size();j++){
				edge e=temp.get(j);
				System.out.println(e.v1+" "+e.v2+" "+e.weight);
				c++;
			}
		}
		System.out.println("Count=" + c);

		
	}
	
	
	public ArrayList<LinkedList<edge>> getNeighbourlist(){
		ArrayList<LinkedList<edge>> temp = new ArrayList<LinkedList<edge>>();
		
		for(int i=0;i<nodes;i++){
			LinkedList<edge> temp1 = new LinkedList<edge>();
			for(int j=0;j<neighbourlist.get(i).size();j++){
				edge temp2 = new edge(neighbourlist.get(i).get(j).v1,neighbourlist.get(i).get(j).v2,neighbourlist.get(i).get(j).weight);
				temp1.add(temp2);
			}
			temp.add(temp1);
		}
		return temp;
	}
	public int[][] getMatrix(){
		return adjMatrix;
	}
	public int size(){return nodes;}
	
	
}

