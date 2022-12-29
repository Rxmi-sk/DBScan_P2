/* Rami Slimane-Kadi
 * 300237431
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;  

public class DBScan {
	private double epsilon;
	private double minPoints;
	private ArrayList<Point3D> listOfPoints;
	private int clusterCounter;

	//DBScan constructor	
	public DBScan(ArrayList<Point3D> listOfPoints){
		this.listOfPoints= listOfPoints;
	
	}
	
	//setter for epsilon/also returns epsilon
	public double setEps(double eps) {
		epsilon=eps;
		return epsilon;
	}
	
	//setter for minPoints/also returns minPoints
	public double setMinPts(double minPts){
		minPoints=minPts;
		return minPoints;
	}
	
	//Method that finds the number of clusters for the linear solution within the csv file
	public void findClusters(){
		clusterCounter=0;
		Point3D Q;
		
		for(Point3D P: this.listOfPoints) {
			if(P.getLabel()!=-1) {
				continue;
			}
			
			NearestNeighbors neighbours= new NearestNeighbors(this.listOfPoints);
			ArrayList<Point3D> N= neighbours.rangeQuery(epsilon,P);
			if (N.size()<minPoints) {
				P.setLabel(0);
				continue;
			}
				
			clusterCounter=clusterCounter+1;
			P.setLabel(clusterCounter);
			Stack<Point3D> S= new Stack<Point3D>();
			
			for(int i=0; i<N.size();i++) {
				S.push(N.get(i));
			}
			
			while(!(S.isEmpty())){
				Q=S.pop();
				
				if(Q.getLabel()==0) {
					Q.setLabel(clusterCounter);
				}
				
				if(Q.getLabel()!=-1) {
					continue;
				}
				
				Q.setLabel(clusterCounter);
				N=(ArrayList<Point3D>)neighbours.rangeQuery(epsilon,Q);
				if (N.size()>=minPoints) {
					for(int i=0; i<N.size();i++) {
						S.push(N.get(i));
					}
				}
			}
	}
	}
	//Method that finds the number of clusters for the KDTree solution within the csv file
	public void findClustersKD(){
		clusterCounter=0;
		Point3D Q;
		
		for(Point3D P: this.listOfPoints) {
			if(P.getLabel()!=-1) {
				continue;
			}
			
			NearestNeighborsKD neighbours= new NearestNeighborsKD(this.listOfPoints);
			ArrayList<Point3D> N= neighbours.rangeQuery(epsilon,P);
			if (N.size()<minPoints) {
				P.setLabel(0); //Point set to noise
				continue;
			}
				
			clusterCounter=clusterCounter+1;
			P.setLabel(clusterCounter);
			Stack<Point3D> S= new Stack<Point3D>();
			
			for(int i=0; i<N.size();i++) {
				S.push(N.get(i));
			}
			
			while(!(S.isEmpty())){
				Q=S.pop();
				
				if(Q.getLabel()==0) {
					Q.setLabel(clusterCounter);
				}
				
				if(Q.getLabel()!=-1) {
					continue;
				}
				
				Q.setLabel(clusterCounter);
				N=(ArrayList<Point3D>)neighbours.rangeQuery(epsilon,Q);
				if (N.size()>=minPoints) {
					for(int i=0; i<N.size();i++) {
						S.push(N.get(i));
					}
				}
			}
	}
	}
	
	//getter for clusetCounter
	public int getNumberOfClusters() {
		return clusterCounter;
	}
	
	//getter for the listOfPoints
	public ArrayList<Point3D> getPoints(){
		return this.listOfPoints;
	}
	
	// reads a csv file of 3D points and then creates the points using the coordinates in the csv file and then adds it in to a list/returns it
	public static ArrayList<Point3D> read(String filename) throws FileNotFoundException{
		ArrayList<Point3D> arrayL= new ArrayList<Point3D>();
		Scanner sc = new Scanner(new File(filename));
		sc.nextLine();
		sc.nextLine();
		
		while (sc.hasNextLine()){
			String currentLine=sc.nextLine();
			String [] arr= currentLine.split(",");
	
			double x=Double.parseDouble(arr[0]);
			double y=Double.parseDouble(arr[1]);
			double z=Double.parseDouble(arr[2]);
			
			Point3D pt= new Point3D(x,y,z);
			arrayL.add(pt);
		}			
		sc.close();
		
		return arrayL;
	}
	
	//Creates a new file with parameter filename as the name of the file and with each line corresponding to a point
	public void save(String filename) throws IOException{
		File file = new File(filename);
		FileOutputStream fos= new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("x,y,z,C,R,G,B");
		bw.newLine();
		bw.write("0,0,0,0,0,0,0");
		bw.newLine();
		
		ArrayList<Point3D> temp= getPoints();
		
		for(int i=0;i<temp.size(); i++) {
			Point3D pt= temp.get(i);
			double x=pt.getX();
			double y=pt.getY();
			double z=pt.getZ();
			int lab=pt.getLabel();
			double color=pt.getLabel()/getNumberOfClusters();
			bw.write(x+","+y+","+z+","+lab+","+color+","+color+","+color);
			bw.newLine();
		}
		
		bw.close();
	    }
		

	
	public static void main(String[] args){
		
		try {
			DBScan x= new DBScan(read("Point_Cloud_1.csv"));
			x.setEps(.98);
			x.setMinPts(3);
			
			long startTime = System.nanoTime();
			x.findClustersKD();
			long endTime = System.nanoTime();
			long duration = (endTime - startTime)/1000000; // in milliseconds.
			x.save("Point_Cloud_1_KD_clusters_"+x.epsilon+"_"+x.minPoints+"_"+x.getNumberOfClusters()+".csv");
			System.out.println(duration+"ms"); //printing total runtime for findclusters
		}
		
		catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}
		
		catch(IOException e){
			System.out.println("Error");
		}
		
		
	
	}
}