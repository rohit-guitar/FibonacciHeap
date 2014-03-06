package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class GraphFile {
	private int size;
	private int density;
	private ArrayList<LinkedList<edge>> neighbourlist =null;
	
	
  public void fileRead(String fName) throws IOException {
	  // Location of file to read
	  boolean n=true;
	  String temp="C:\\Users\\Rohit\\workspace\\PrimAlgo\\src\\project1\\";
      File file = new File(temp+fName+".txt");

      try {

          Scanner scanner = new Scanner(file);

          while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              String[] parts = line.split(" ");
              if(n){
            	  if(parts.length!=2){System.out.println("File format is not correct");System.exit(0);}
            	  this.size=Integer.parseInt(parts[0]);
            	  this.density=Integer.parseInt(parts[1]);
            	  this.neighbourlist= new ArrayList<LinkedList<edge>>(this.size);
            	  for(int i=0;i<this.size;i++){
            		  this.neighbourlist.add(new LinkedList<edge>());
            	  }
            	  n=false;
              }
              
              else{
            	  int v1= Integer.parseInt(parts[0]);
            	  int v2= Integer.parseInt(parts[1]);
            	  int w= Integer.parseInt(parts[2]);
            	  edge e= new edge(v1,v2,w);
            	  this.neighbourlist.get(v1).add(e);
            	  e= new edge(v2,v1,w);
            	  this.neighbourlist.get(v2).add(e);
              }
          }
          scanner.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
  } 
  
  public void print(){
		int c=0;
		for(int i=0;i<this.size;i++){
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
		
		for(int i=0;i<this.size;i++){
			LinkedList<edge> temp1 = new LinkedList<edge>();
			for(int j=0;j<neighbourlist.get(i).size();j++){
				edge temp2 = new edge(neighbourlist.get(i).get(j).v1,neighbourlist.get(i).get(j).v2,neighbourlist.get(i).get(j).weight);
				temp1.add(temp2);
			}
			temp.add(temp1);
		}
		return temp;
	}
	
  public int getSize(){return size;}
  public int getDensity(){return density;}

  }


