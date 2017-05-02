package kmeans.clustering;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class KMeans {
	public static void getEucKMeans(List clusterList, List pointList) {		
		for(int c = 0; c < pointList.size(); c++) {
			Point alpha = (Point) pointList.get(c);
			double eD = 0;

			for(int x = 0; x < clusterList.size(); x++) {
				Cluster beta = (Cluster) clusterList.get(x);
				Point betaAlpha = (Point) beta.getEuclideanCentroid();
				eD = Cluster.euclideanDistance(alpha, betaAlpha);
				
				if(alpha.getEuclidean() == 0) {
					alpha.setEuclidean(eD);
					alpha.setClusterParentEuclidean((x + 1));
					beta.addEuclideanPoint(alpha);
				}
				
				if((alpha.getEuclidean() != 0) && (alpha.getEuclidean() > eD)) {
					int clusterID = (alpha.getClusterParentEuclidean() - 1);
					Cluster temp = (Cluster) clusterList.get(clusterID);
					temp.removeEuclideanPoint(alpha);
					alpha.setEuclidean(eD);
					alpha.setClusterParentEuclidean((x + 1));
					beta.addEuclideanPoint(alpha);
				}
			}
		}
	}
	
	public static void getManKMeans(List clusterList, List pointList) {		
		for(int c = 0; c < pointList.size(); c++) {
			Point alpha = (Point) pointList.get(c);
			double mD = 0;

			for(int x = 0; x < clusterList.size(); x++) {
				Cluster beta = (Cluster) clusterList.get(x);
				Point betaGamma = (Point) beta.getManhattanCentroid();
				mD = Cluster.manhattanDistance(alpha, betaGamma);
				
				if(alpha.getManhattan() == 0) {
					alpha.setManhattan(mD);
					alpha.setClusterParentManhattan((x + 1));
					beta.addManhattanPoint(alpha);
				}
				
				if((alpha.getManhattan() != 0) && (alpha.getManhattan() > mD)) {
					int clusterID = (alpha.getClusterParentManhattan() - 1);
					Cluster temp = (Cluster) clusterList.get(clusterID);
					temp.removeManhattanPoint(alpha);
					alpha.setManhattan(mD);
					alpha.setClusterParentManhattan((x + 1));
					beta.addManhattanPoint(alpha);
				}
			}
		}
	}

	 public static void euclideanCentroid(List clusterList) {
		for(int c = 0; c < clusterList.size(); c++) {
			Cluster alpha = (Cluster) clusterList.get(c);
			
			Point centroid = alpha.getEuclideanCentroid();
			
			int x = centroid.getCoordX();
			int y = centroid.getCoordY();
			alpha.modifyOldEuclideanCentroid(x,y);
			
			int xTotal = 0;
			int yTotal = 0;
			
			for(int i = 0; i < alpha.getEuclideanCount(); i++) {
				Point beta = alpha.getEuclideanPoint(i);
				
				int manX = beta.getCoordX();
				int manY = beta.getCoordY();
				
				xTotal = xTotal + manX;
				yTotal = yTotal + manY;
			}
			int totalPoints = alpha.getEuclideanCount() + 1;
			int xMean = xTotal / totalPoints;
			int yMean = yTotal / totalPoints;
			alpha.modifyEuclideanCentroid(xMean, yMean);
			
			//Point oldC = alpha.getOldEuclideanCentroid();
			//System.out.format("\n\n\n\nxEucTotal/yEucTotal = (%d, %d)\n\n", xTotal, yTotal);
			//System.out.format("nxMean/yMmean = (%d, %d)\n\n\n\n", xMean, yMean);
			//oldC.printPoint();
			//Point newC = alpha.getEuclideanCentroid();
			//newC.printPoint();
		}
	}
	
	public static void manhattanCentroid(List clusterList) {
		for(int c = 0; c < clusterList.size(); c++) {
			Cluster alpha = (Cluster) clusterList.get(c);
			
			Point centroid = alpha.getManhattanCentroid();
			
			int x = centroid.getCoordX();
			int y = centroid.getCoordY();
			alpha.modifyOldManhattanCentroid(x,y);
			
			int xTotal = 0;
			int yTotal = 0;
			
			for(int i = 0; i < alpha.getManhattanCount(); i++) {
				Point beta = alpha.getManhattanPoint(i);
				
				int manX = beta.getCoordX();
				int manY = beta.getCoordY();
				
				xTotal = xTotal + manX;
				yTotal = yTotal + manY;
			}
			int totalPoints = alpha.getManhattanCount() + 1;
			int xMean = xTotal / totalPoints;
			int yMean = yTotal / totalPoints;
			alpha.modifyManhattanCentroid(xMean, yMean);

			
			//Point oldC = alpha.getOldManhattanCentroid();
			//System.out.format("\n\n\n\nxManTotal/yManTotal = (%d, %d)\n\n", xTotal, yTotal);
			//System.out.format("nxMean/yMmean = (%d, %d)\n\n\n\n", xMean, yMean);
			//oldC.printPoint();
			//Point newC = alpha.getManhattanCentroid();
			//newC.printPoint();
		}
	}
	
	public static void clearEucClusters(List clusterList, List pointList) {		
		for(int c = 0; c < clusterList.size(); c++) {
			Cluster temp = (Cluster) clusterList.get(c);
			temp.clearEucPoints();
		}
		
		for(int c = 0; c < pointList.size(); c++) {
			Point temp = (Point) pointList.get(c);
			temp.setClusterParentEuclidean(0);
			temp.setEuclidean(0);
		}
	}
	
	public static void clearManClusters(List clusterList, List pointList) {		
		for(int c = 0; c < clusterList.size(); c++) {
			Cluster temp = (Cluster) clusterList.get(c);
			temp.clearManPoints();
		}
		
		for(int c = 0; c < pointList.size(); c++) {
			Point temp = (Point) pointList.get(c);
			temp.setClusterParentManhattan(0);
			temp.setManhattan(0);
		}
	}
	
	public static boolean checkEucCentroid(List clusterList) {
		boolean toReturn = false;
		for(int c = 0; c < clusterList.size(); c++) {
			Cluster alpha = (Cluster) clusterList.get(c);
			Point newEuclidean = (Point) alpha.getEuclideanCentroid();
			Point oldEuclidean = (Point) alpha.getOldEuclideanCentroid();
			
			double x = Cluster.euclideanDistance(newEuclidean, oldEuclidean);
			
			if(x == 0)
				toReturn = true;
		}
		return toReturn;
	}
	
	public static boolean checkManCentroid(List clusterList) {
		boolean toReturn = false;
		for(int c = 0; c < clusterList.size(); c++) {
			Cluster alpha = (Cluster) clusterList.get(c);
			Point newManhattan = (Point) alpha.getManhattanCentroid();
			Point oldManhattan = (Point) alpha.getOldManhattanCentroid();
			
			double x = Cluster.manhattanDistance(newManhattan, oldManhattan);
			if(x == 0)
				toReturn = true;
		}
		return toReturn;
	}
}

