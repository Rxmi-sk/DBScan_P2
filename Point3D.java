/* Rami Slimane-Kadi
 * 300237431
 */
public class Point3D {
	
	private double x;
	private double y;
	private double z;
	private int label=-1;
	
	
	/*Point3D constructor that initializes the values of the 3 private variables x, y, and z*/
	public Point3D(double xValue, double yValue, double zValue){
		x=xValue;
		y=yValue;
		z=zValue;
	}
	
	//Getter for label
	public int getLabel() {
		return label;
	}
	
	//Setter for label
	public void setLabel(int value) {
		label=value;
	}
	
	//Getter for X
	public double get(int axis) {
		if(axis==0)
			return x;
		
		else if(axis==1)
			return y;
		
		else
			return z;	
		}
	
	//Getter for X
	public double getX() {
		return x;
	}
	
	//Getter for Y
	public double getY() {
		return y;
	}
	
	//Getter for Z
	public double getZ() {
		return z;
	}
	
	//Setter for X
	public void setX(int val) {
		x= val;
	}
	
	//Setter for Y
	public void setY(int val) {
		y= val;
	}
	
	//Setter for Z
	public void setZ(int val) {
		z= val;
	}
    
	public double L2(Point3D pt){
        //collect individual 1D distances
        double dx=(x-pt.x);
        double dy=(y-pt.y);
        double dz=(z-pt.z); 
        //calculate euclidian 3D distance squared
        return dx*dx+dy*dy+dz*dz;
    }
	
	//returns the square root of the result of L2()
	public double distance(Point3D pt){
        return Math.sqrt(L2(pt));
    }
	
	
	//toString method that displays a point in the form: 3,5,7 
	public String toString() {
		return x+","+y+","+z;
	}

}