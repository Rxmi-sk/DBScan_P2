/* Rami Slimane-Kadi
 * 300237431
 */
import java.util.ArrayList;

//NearestNeighbors class
public class NearestNeighbors {
	private ArrayList<Point3D> newList;
	
	/*Constructor for the NearestNeighbor class that accepts an ArrayList of Point3D points*/
	public NearestNeighbors(ArrayList<Point3D> newList){
		this.newList= newList;
	}
	
	
	/*Method that takes a Point3D called P and a double called eps as parameters. We then loop through a list 
	 *of Point3D points and check if the distance between P and the point is less or equal to eps (to identify the neighbors).
	 *The points close enough to P are added to an ArrayList which is then returned at the end.
	 */
	public ArrayList<Point3D> rangeQuery(double eps, Point3D P){
		ArrayList<Point3D> list= new ArrayList<Point3D>();

		for(Point3D point: newList) {
			if(P.distance(point)<=eps)
				list.add(point);
		}
		
		return list;
		
	}
	
	
	

}
