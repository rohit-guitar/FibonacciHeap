package project1;

import java.util.ArrayList;
import java.util.LinkedList;

public class mstFheap {
	private fHeap f;
	private double totalCost =0;
	private double [] keyList= null;
	
	//Graph elements
	private int size=0;
	private ArrayList<LinkedList<edge>> neighbour =new ArrayList<LinkedList<edge>>();
	public ArrayList<fHeapNode> nodes = new ArrayList<fHeapNode>();
	public double cost(){return totalCost;}
	

	public mstFheap(ArrayList<LinkedList<edge>> object,int size){
		f=new fHeap();
		neighbour=object;
		this.size=size;
		keyList=new double[size];		
			for(int i=0;i<size;i++){
				keyList[i]= Double.POSITIVE_INFINITY;
				fHeapNode temp= new fHeapNode(i, keyList[i]);
				nodes.add(temp);
			}
			for(int i=0;i<size;i++){
				f.insert(nodes.get(i), keyList[i]);
			}
		}
	
	//public 
	public void start(){
		int [] tracker= new int[size];
		for(int i=0;i<size;i++){
			tracker[i]=0;
		}
		
		final int [] prev = new int[size];
		f.decreaseKey(nodes.get(0), 0);
		long startTime1 = System.currentTimeMillis();

		while (!f.checkEmpty()){
			fHeapNode min = f.removeMin();
			int vertex= min.data;
			tracker[vertex]=1;
			LinkedList<edge> n= neighbour.get(vertex);
			totalCost=totalCost+min.key;
			for(int i=0;i<n.size();i++){
				edge temp=n.get(i);
  				try{
					//if(f.search(temp.v2)!=null){
  					if(tracker[temp.v2]==0){
						if(temp.weight<keyList[temp.v2] && prev[vertex]!=temp.v2){
							//f.decreaseKey(f.search(temp.v2), temp.weight);
							f.decreaseKey(nodes.get(temp.v2), temp.weight);
							keyList[temp.v2]=temp.weight;
							prev[temp.v2]=vertex;
						}
					}
					
					else{continue;}
				}catch(Exception e){}	
					
			}
		}
		long endTime1   = System.currentTimeMillis();
		long totalTime1 = endTime1 - startTime1;
		System.out.println("Fibonacci heap run time:- "+totalTime1);
		for(int l=0;l<size;l++){System.out.print(prev[l]+"  ");}
		System.out.println();
				
	}
		
	
	public void print(){
		System.out.print(" Keylist:-  ");
		for(int i=0;i<neighbour.size();i++){
			System.out.print(neighbour.get(i).size()+" ");
		}
		
	}

}
