package simulator.factories;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

class MostCrowdedStrategyBuilderTest {

	@Test
	void test_1() {
		MostCrowdedStrategyBuilder eb = new MostCrowdedStrategyBuilder();
		
		String dataJSon = "{\"timeslot\" : 5}";
		LightSwitchingStrategy o = eb.create_instance(new JSONObject(dataJSon));

		assertTrue( o instanceof MostCrowdedStrategy );
		
	}

	@Test
	void test_2() {
		MostCrowdedStrategyBuilder eb = new MostCrowdedStrategyBuilder();
		
		String dataJSon = "{}";
		LightSwitchingStrategy o = eb.create_instance(new JSONObject(dataJSon));
		assertTrue( o instanceof MostCrowdedStrategy );
		
	}

	@Test
	void test_4() {
		MostCrowdedStrategyBuilder eb = new MostCrowdedStrategyBuilder();
		
		String dataJSon = "{}";
		LightSwitchingStrategy o = eb.create_instance(new JSONObject(dataJSon));
		assertTrue( o instanceof MostCrowdedStrategy );
		
	}


}
