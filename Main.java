package kmeans.clustering;
import java.util.List;
import javax.swing.JOptionPane;
public class Main {
	public static void main(String[] args) {
		List points;
		List clusters;
		List returned;
		
		String getNumK = JOptionPane.showInputDialog("How many Clusters would you like? (2 or 4)", JOptionPane.PLAIN_MESSAGE);
		int numK = Integer.parseInt(getNumK);
		
		String seedStr = JOptionPane.showInputDialog("Please enter the seed string", JOptionPane.PLAIN_MESSAGE);
		long seed = Integer.parseInt(seedStr);
		long clusterSeed = Integer.parseInt(seedStr) * 2;
		
		points = Point.createPoints(seed);
		clusters = Cluster.createClusters(numK, clusterSeed, points);
		KMeans.getEucKMeans(clusters, points);
		KMeans.getManKMeans(clusters, points);
		KMeans.euclideanCentroid(clusters);
		KMeans.manhattanCentroid(clusters);
		
		boolean eucDistance = false;
		boolean manDistance = false;
		int iterations = 0 ;
		
		do {
			eucDistance = KMeans.checkEucCentroid(clusters);
			manDistance = KMeans.checkManCentroid(clusters);
			
			if(!eucDistance) {
				KMeans.clearEucClusters(clusters, points);
				KMeans.getEucKMeans(clusters, points);
				KMeans.euclideanCentroid(clusters);
			}
			
			if(!manDistance) {
				KMeans.clearManClusters(clusters, points);
				KMeans.getManKMeans(clusters, points);
				KMeans.manhattanCentroid(clusters);
			}
			
			iterations++;
		} while(!(eucDistance && manDistance));
		
		for(int c = 0; c < numK; c++) {
			Cluster temp = (Cluster) clusters.get(c);
			temp.printEuclideanPoints();
		}
		
		for(int c = 0; c < numK; c++) {
			Cluster temp = (Cluster) clusters.get(c);
			temp.printManhattanPoints();
		}
		
		System.out.format("\nIterations: %d\n", iterations);
		
		for(int c = 0; c < numK; c++) {
			Cluster temp = (Cluster) clusters.get(c);
			System.out.format("\nEuclidean Centroid # %d", c);
			temp.getEuclideanCentroid().printPoint();
			System.out.println();
		}
		
		for(int c = 0; c < numK; c++) {
			Cluster temp = (Cluster) clusters.get(c);
			System.out.format("\nManhattanCentroid # %d", c);
			temp.getManhattanCentroid().printPoint();
			System.out.println();
		}
		
		double totalEuclideanIntraClusterDistances = 0;
		double totalManhattanIntraClusterDistances = 0;
		
		for(int i = 0; i < clusters.size(); i++)
		{
			double eucSum = Cluster.intraClusterDistance((Cluster) clusters.get(i));
			double manSum = Cluster.intraClusterDistance((Cluster) clusters.get(i));
			
			totalEuclideanIntraClusterDistances = totalEuclideanIntraClusterDistances + eucSum;
			totalManhattanIntraClusterDistances = totalManhattanIntraClusterDistances + manSum;
			
			System.out.format("\nEuclidean Cluster #%d (IntraClusterDistance) = %f", i, eucSum);
			System.out.format("\nManhattan Cluster #%d (IntraClusterDistance) = %f\n", i, manSum);
		}
		
		System.out.format("\nTotal Euclidean IntraClusterDistance = %f", totalEuclideanIntraClusterDistances);
		System.out.format("\nTotal Manhattan IntraClusterDistance = %f\n", totalManhattanIntraClusterDistances);
		
		double eucMin = Cluster.minDistanceBetweenClusters(clusters);
		double manMin = Cluster.minDistanceBetweenClusters(clusters);
		
		System.out.format("\nMin Distance (Euclidean Clusters) = %f", eucMin);
		System.out.format("\nMin Distance (Manhattan Clusters) = %f\n", manMin);
		
		double eucMax = Cluster.maxDistanceBetweenClusters(clusters);
		double manMax = Cluster.maxDistanceBetweenClusters(clusters);
		
		System.out.format("\nMax Distance (Euclidean Clusters) = %f", eucMax);
		System.out.format("\nMax Distance (Manhattan Clusters) = %f\n", manMax);
	}
}

