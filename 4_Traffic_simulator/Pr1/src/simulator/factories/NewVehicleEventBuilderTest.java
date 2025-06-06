package simulator.factories;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;

class NewVehicleEventBuilderTest {

	static private TrafficSimulator createSim() {
		TrafficSimulator ts = new TrafficSimulator();

		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);

		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);

		String dataJson1 = "{\n"
				+ "     	 \"time\" : 1,\n" + "         \"id\"   : \"j1\",\n" + "      	 \"coor\" : [100,200],\n"
				+ "      	 \"ls_strategy\" : { \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} },\n"
				+ "      	 \"dq_strategy\" : { \"type\" : \"move_first_dqs\",  \"data\" : {} }\n" + "   	 }";

		String dataJson2 = "{\n"
				+ "     	 \"time\" : 1,\n" + "         \"id\"   : \"j2\",\n" + "      	 \"coor\" : [100,200],\n"
				+ "      	 \"ls_strategy\" : { \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} },\n"
				+ "      	 \"dq_strategy\" : { \"type\" : \"move_first_dqs\",  \"data\" : {} }\n" + "   	 }";

		NewJunctionEventBuilder jeb = new NewJunctionEventBuilder(lssFactory, dqsFactory);
		ts.addEvent(jeb.create_instance(new JSONObject(dataJson1)));
		ts.addEvent(jeb.create_instance(new JSONObject(dataJson2)));

		
		String dataJson3 = "{\n"
				+ "    	  \"time\"     : 1,\n" + "    	   \"id\"       : \"r1\",\n"
				+ "           \"src\"      : \"j1\",\n" + "           \"dest\"     : \"j2\",\n"
				+ "           \"length\"   : 10000,\n" + "           \"co2limit\" : 500,\n"
				+ "           \"maxspeed\" : 120,\n" + "           \"weather\"  : \"SUNNY\"\n" + "   	  }";

		NewCityRoadEventBuilder reb = new NewCityRoadEventBuilder();
		ts.addEvent(reb.create_instance(new JSONObject(dataJson3)));

		return ts;

	}

	@Test
	void test_1() {
	
		TrafficSimulator ts = createSim();
				
		String inputJson = "{\n"
				+ "          \"time\"      : 1,\n"
				+ "          \"id\"        : \"v1\",\n"
				+ "          \"maxspeed\"  : 100,\n"
				+ "          \"class\"     : 3,\n"
				+ "          \"itinerary\" : [\"j1\", \"j2\"]\n"
				+ "      }";
		
		
		NewVehicleEventBuilder eb = new NewVehicleEventBuilder();
		Event e = eb.create_instance(new JSONObject(inputJson));	
		
		ts.addEvent(e);
		
		ts.advance();

		//The calculations done within the string are not correct according to a city road
		String s = "{\"time\":1,\"state\":{\"roads\":[{\"speedlimit\":120,\"co2\":261,\"weather\":\"SUNNY\",\"vehicles\":[\"v1\"],\"id\":\"r1\"}],\"vehicles\":[{\"distance\":87,\"road\":\"r1\",\"co2\":261,\"location\":87,\"id\":\"v1\",\"class\":3,\"speed\":87,\"status\":\"TRAVELING\"}],\"junctions\":[{\"green\":\"none\",\"queues\":[],\"id\":\"j1\"},{\"green\":\"r1\",\"queues\":[{\"road\":\"r1\",\"vehicles\":[]}],\"id\":\"j2\"}]}}";
		assertTrue(new JSONObject(s).similar(ts.report()));
	}

	@Test
	void test_2() {
	
		// error in time
		String inputJson = "{\n"
				+ "          \"time\"      : \"boom!\",\n"
				+ "          \"id\"        : \"v1\",\n"
				+ "          \"maxspeed\"  : 100,\n"
				+ "          \"class\"     : 3,\n"
				+ "          \"itinerary\" : [\"j1\", \"j2\"]\n"
				+ "      }";
		
		
		NewVehicleEventBuilder eb = new NewVehicleEventBuilder();
		assertThrows(Exception.class, () -> eb.create_instance( new JSONObject(inputJson) ));	
	
	}

}
