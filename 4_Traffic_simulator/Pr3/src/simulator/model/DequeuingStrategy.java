package simulator.model;

import java.util.List;

public interface DequeuingStrategy {
	//Deep copy isn't required since the classes that implement this interface have no attributes needing of copying
	List<Vehicle> dequeue(List<Vehicle> q);
}
