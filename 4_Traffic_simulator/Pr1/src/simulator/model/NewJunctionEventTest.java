package simulator.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class NewJunctionEventTest {

	@Test
	void test_1() {
		RoadMap map = new RoadMap();

		// add a new junction via an event
		Event e = new NewJunctionEvent(10,"j1", new RoundRobinStrategy(10), new MoveFirstStrategy(), 0, 0);
		e.execute(map);
		
		List<Junction> lj = map.getJunctions();
		
		assertEquals(1, lj.size());
		
		assertEquals("j1", lj.get(0).getId());
	}

}
