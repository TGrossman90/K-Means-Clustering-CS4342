package kmeans.clustering;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Cluster {
	private List euclideanPoints;
	private List manhattanPoints;
	private Point euclideanCentroid;
	private Point manhattanCentroid;
	private Point oldEuclideanCentroid;
	private Point oldManhattanCentroid;
	private int clusterID;
	private int euclideanPointCount;
	private int manhattanPointCount;
	
	public Cluster(int c, Point centroid) {
		this.clusterID = c;
		this.euclideanPoints = new ArrayList();
		this.manhattanPoints = new ArrayList();
		this.euclideanCentroid = centroid;
		this.manhattanCentroid = centroid;
		this.euclideanPointCount = 0;
		this.manhattanPointCount = 0;
		this.oldEuclideanCentroid = new Point(0,0);
		this.oldManhattanCentroid = new Point(0,0);
	}
	
	public Point getEuclideanCentroid() {
		Point centroid = (Point) this.euclideanCentroid;
		return centroid;
	}
	
	public Point getOldEuclideanCentroid() {
		Point centroid = (Point) this.oldEuclideanCentroid;
		return centroid;
	}
	
	public Point getManhattanCentroid() {
		Point centroid = (Point) this.manhattanCentroid;
		return centroid;
	}
	
	public Point getOldManhattanCentroid() {
		Point centroid = (Point) this.oldManhattanCentroid;
		return centroid;
	}
	
	public Point getEuclideanPoint(int x) {
		return (Point) this.euclideanPoints.get(x);
	}
	
	public Point getManhattanPoint(int x) {
		return (Point) this.manhattanPoints.get(x);
	}
	
	public int getEuclideanCount() {
		return this.euclideanPointCount;
	}
	
	public int getManhattanCount() {
		return this.manhattanPointCount;
	}
	
	public void addEuclideanPoint(Point x) {
		this.euclideanPoints.add(x);
		this.euclideanPointCount = this.euclideanPointCount + 1;
	}
	
	public void addManhattanPoint(Point x) {
		this.manhattanPoints.add(x);
		this.manhattanPointCount = this.manhattanPointCount + 1;
	}
	
	public void removeEuclideanPoint(Point x) {
		this.euclideanPoints.remove(x);
		this.euclideanPointCount = this.euclideanPointCount - 1;
	}
	
	public void removeManhattanPoint(Point x) {
		this.manhattanPoints.remove(x);
		this.manhattanPointCount = this.manhattanPointCount - 1;
	}
	
	public void modifyEuclideanCentroid(int x, int y) {
		this.euclideanCentroid.updatePoint(x, y);
	}
	
	public void modifyManhattanCentroid(int x, int y) {
		this.manhattanCentroid.updatePoint(x, y);
	}
	
	public void modifyOldEuclideanCentroid(int x, int y) {
		this.oldEuclideanCentroid.updatePoint(x, y);
	}
	
	public void modifyOldManhattanCentroid(int x, int y) {
		this.oldManhattanCentroid.updatePoint(x, y);
	}
	public int getClusterID() {
		return this.clusterID;
	}
	
	public static Cluster createCluster(int c, Point centroid) {
		return new Cluster(c, centroid);
	}
	
	public void clearEucPoints() {
		this.euclideanPoints.clear();
		this.euclideanPointCount = 0;
	}
	
	public void clearManPoints() {
		this.manhattanPoints.clear();
		this.manhattanPointCount = 0;
	}
	
	public static List createClusters(int x, long seed, List pointList) {
		Random generator = new Random(seed);
		List clusters = new ArrayList(x);
		for(int c = 0; c < x; c++) {
			int i = generator.nextInt(101);
			int j = generator.nextInt(101);
			
			Point centroid = new Point(i,j);
			clusters.add(createCluster(c, centroid));
		}
		return clusters;
	}
	
	public void printEuclideanPoints() {
		for(int i = 0; i < this.euclideanPointCount; i++) {
			Point beta = (Point) this.getEuclideanPoint(i);
			int x = beta.getCoordX();
			int y = beta.getCoordY();
			System.out.format("\n(Euclidean)Cluster %d - Point %d: (%d, %d)", (this.clusterID), (i + 1), x, y);
		}
		System.out.println();
	}
	
	public void printManhattanPoints() {
		for(int i = 0; i < this.manhattanPointCount; i++) {
			Point beta = (Point) this.getManhattanPoint(i);
			int x = beta.getCoordX();
			int y = beta.getCoordY();
			System.out.format("\n(Manhattan)Cluster %d - Point %d: (%d, %d)", (this.clusterID), (i + 1), x, y);
		}
		System.out.println();
	}
	
	public static double euclideanDistance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.getCoordX() - b.getCoordX(), 2) + Math.pow(a.getCoordY() - b.getCoordY(), 2)); 
    }
	
	public static double euclideanDistance(Cluster a, Cluster b) {
		Point x = (Point) a.getEuclideanCentroid();
		Point y = (Point) b.getEuclideanCentroid();
		return Math.sqrt(Math.pow(x.getCoordX() - y.getCoordX(), 2) + Math.pow(x.getCoordY() - y.getCoordY(), 2)); 
    }
	
	public static int manhattanDistance(Point a, Point b) {
		return Math.abs(a.getCoordX() - b.getCoordX()) + Math.abs(a.getCoordY() - b.getCoordY()); 
	}
	
	public static double manhattanDistance(Cluster a, Cluster b) {
		Point x = (Point) a.getEuclideanCentroid();
		Point y = (Point) b.getEuclideanCentroid();
		return Math.abs(x.getCoordX() - y.getCoordX()) + Math.abs(x.getCoordY() - y.getCoordY()); 
    }
	
	public static double intraClusterDistance(Cluster cluster) {
		 double sum = 0;

		for(int c = 1; c < cluster.getEuclideanCount(); c++) 
		{
			Point alpha = (Point) cluster.getEuclideanPoint(c - 1);
			Point beta = (Point) cluster.getEuclideanPoint(c);
			sum += euclideanDistance(beta, alpha);
		}
		 return sum;
	}
	
	public static double minDistanceBetweenClusters(List clusters) {
		List tmpClusters = new ArrayList(clusters);
		double min = Integer.MAX_VALUE;
	 
		while (tmpClusters.size() > 0) {
			for (int i = 0; i < tmpClusters.size(); i++) {
				if ((euclideanDistance((Cluster) clusters.get(0), (Cluster) tmpClusters.get(0)) < min) 
						&& (euclideanDistance((Cluster) clusters.get(0), (Cluster) tmpClusters.get(0)) != 0)) {
					min = euclideanDistance((Cluster) clusters.get(0), (Cluster) tmpClusters.get(0));
				}
			}
			tmpClusters.remove(0);
		}
	 return min;
	 } 
	
	public static double maxDistanceBetweenClusters(List clusters) {
		List tmpClusters = new ArrayList(clusters);
		double min = 0;
	 
		while (tmpClusters.size() > 0) {
			for (int i = 0; i < tmpClusters.size(); i++) {
				if ((euclideanDistance((Cluster) clusters.get(0), (Cluster) tmpClusters.get(0)) > min) 
						&& (euclideanDistance((Cluster) clusters.get(0), (Cluster) tmpClusters.get(0)) != 0)) {
					min = euclideanDistance((Cluster) clusters.get(0), (Cluster) tmpClusters.get(0));
				}
			}
			tmpClusters.remove(0);
		}
	 return min;
	 } 
}
