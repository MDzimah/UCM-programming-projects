package simulator.model;

import java.util.*;

public class MoveFirstStrategy implements DequeuingStrategy {
	
    @Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> aux = new ArrayList<Vehicle>();
		
		if(q.size() != 0)
			aux.add(q.get(0));

		return aux;
	}
}
