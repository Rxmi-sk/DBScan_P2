/* Rami Slimane-Kadi
 * 300237431
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;


//NearestNeighborsKD class
public class NearestNeighborsKD {
	private ArrayList<Point3D> neighbors;
	KDTree kdTree;

	/*Constructor for the NearestNeighborsKD class that accepts an ArrayList of Point3D points*/
	public NearestNeighborsKD(ArrayList<Point3D> list){
		kdTree= new KDTree();
		neighbors=list;
		for(Point3D pt: neighbors) {
			kdTree.add(pt);
		}
	}
	
	
	/*Method that takes a Point3D called P and a double called eps as parameters. We then loop through a list 
	 *of Point3D points and check if the distance between P and the point is less or equal to eps (to identify the neighbors).
	 *The points close enough to P are added to an ArrayList which is then returned at the end.
	 */
	public ArrayList<Point3D> rangeQuery(double eps, Point3D p){
		neighbors= new ArrayList<Point3D>();
		rangeQuery(p,eps,neighbors, kdTree.root());
		return neighbors;		
	}
	
	private void rangeQuery(Point3D p, double eps, ArrayList<Point3D> neighbors, KDTree.KDnode node) {
		if(node==null)
			return;
		
		if(p.distance(node.point) < eps)
			neighbors.add(node.point);
		
		if(p.get(node.axis)-eps<=node.value)
			rangeQuery(p,eps,neighbors,node.left);
		
		if(p.get(node.axis) + eps > node.value)
			rangeQuery(p, eps, neighbors, node.right);
	}
}
