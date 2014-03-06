package project1;

import java.util.ArrayList;
import java.util.LinkedList;

//prim's mst algorithm here 
public class mst {
	private ArrayList<LinkedList<edge>> neighbour =null;
	private int size=0;

	
	
	public mst(ArrayList<LinkedList<edge>> object,int x)
	{
		this.neighbour=object;
		this.size=x;
	}
	
	public double start(){
		long startTime = System.currentTimeMillis();
		
		final double [] dist = new double [this.size];  // shortest known distance to MST
		final int [] pred = new int [this.size];  // preceeding node in tree
		final boolean [] visited = new boolean [this.size]; // all false initially
		
		for(int i=0;i<dist.length;i++){
			dist[i]=Double.POSITIVE_INFINITY;
		}
		dist[0]=0; // start node
		
		for(int i=0;i<dist.length;i++){
			int next=  minVertex (dist, visited);
			visited[next]=true;
			
			
			final LinkedList<edge> n = neighbour.get(next);
			for(int j=0;j<n.size();j++){
				edge e= n.get(j);
				if(dist[e.v2]>e.weight && pred[next]!=e.v2){
					dist[e.v2]=e.weight;
					pred[e.v2]= next;
				}
			}
		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Run time normal data structure:- "+totalTime);
		
		double cost=0;
		for(int k=0;k<dist.length;k++){
			System.out.print(pred[k]+"  ");
			cost=cost+dist[k];
		}
		System.out.println();
		 return cost;  
	}
	
	private int minVertex(double [] dist, boolean [] v){
		double x= Double.POSITIVE_INFINITY;
		int y=-1;
		for (int i=0; i<dist.length; i++) {
			if (!v[i] && dist[i]<x) {y=i; x=dist[i];}
       }
		return y;
	}

	
	

}
