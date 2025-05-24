package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private final int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){ this.timeSlot = timeSlot; }
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, 
			int lastSwitchingTime, int currTime)
	{
		if (roads.isEmpty()) return -1;
		else if (currGreen == -1) return this.findIndexMaxRowSizeMatrix(qs, 0);
		else if(currTime - lastSwitchingTime < timeSlot) return currGreen;
		else return this.findIndexMaxRowSizeMatrix(qs, currGreen + 1);
	
	}

	
	/*PRIVATE*/
	
	/*We use this to loop around all the possible queues starting at a point p and find the largest 
	queue, if there are multiple of that size we get the first*/
	int findIndexMaxRowSizeMatrix(List<List<Vehicle>> qs, int startIndex) {
		//first we get the starting position and initialise the maximun
		int pos = startIndex % qs.size();
		int maxSize = qs.get(pos).size();

		//we do a loop from the index until the size + index
		for(int i = startIndex; i < qs.size() + startIndex; ++i) {
			//we take the modulous to find the index it refers to and do normal comparisons from that point
			int ind = i % qs.size();
			if(maxSize < qs.get(ind).size()) {
				maxSize = qs.get(ind).size();
				pos = ind;
			}
		}
		
		return pos;
	}
}
