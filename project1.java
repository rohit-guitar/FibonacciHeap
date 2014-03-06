package project1;

import java.io.IOException;


public class project1 {

	public static void main(String args[]) throws IOException{
		System.gc();
		System.out.println("Enter 1 for random mode then give nodes and density as arguments");
		System.out.println("Enter 2 for file mode and give file name as argument");
		int size=0;
		int density=0;
		if(Integer.parseInt(args[0])==1){
			 size = Integer.parseInt(args[1]);
			 density = Integer.parseInt(args[2]);
			
			if(density> ((size*(size-1))/2))
			{
				density=(size*(size-1))/2; // Maximum no. of edges
			} 
			GenGraph g= new GenGraph(size, density);
			g.makeGraph();
			// calling for finding mst
				mst m= new mst(g.getNeighbourlist(),size);
				mstFheap m1= new mstFheap(g.getNeighbourlist(), size);

				System.out.println("Normal data structure cose:-"+m.start());
				m1.start();
				System.out.println("Fibonacci Heap data structure:-"+m1.cost());
				//m1.print();
			}
		else if(Integer.parseInt(args[0])==2){
			GraphFile g= new GraphFile();
			g.fileRead(args[1]);
			 size=g.getSize();
			 density=g.getDensity();	
				if(density> ((size*(size-1))/2))
				{
					density=(size*(size-1))/2; // Maximum no. of edges
				} 
				
				// calling for finding mst
					mst m= new mst(g.getNeighbourlist(),size);
					mstFheap m1= new mstFheap(g.getNeighbourlist(), size);

					System.out.println("Normal data structure cose:-"+m.start());
					m1.start();
					System.out.println("Fibonacci Heap data structure:-"+m1.cost());
					//m1.print();
		}
	
	}
	
}
