package simulator.factories;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

class MoveFirstStrategyBuilderTest {

	@Test
	void test_1() {
		MoveFirstStrategyBuilder eb = new MoveFirstStrategyBuilder();
		
		String dataJSon = "{}";
		DequeuingStrategy o = eb.create_instance(new JSONObject(dataJSon));
		assertTrue( o instanceof MoveFirstStrategy );
		
	}

	@Test
	void test_2() {
		MoveFirstStrategyBuilder eb = new MoveFirstStrategyBuilder();
		
		String dataJSon = "{}";
		DequeuingStrategy o = eb.create_instance(new JSONObject(dataJSon));
		assertTrue( o instanceof MoveFirstStrategy );
		
	}

}
