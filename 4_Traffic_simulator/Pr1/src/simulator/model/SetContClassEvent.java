package simulator.model;

import java.util.*;

import messages.Messages;
import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	
	private List<Pair<String, Integer>> cs;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs)  {
		super(time);
        if (cs != null) this.cs = cs;
        else throw new IllegalArgumentException(Messages.INVALID_CLASS_ARGUMENTS.formatted("SetContaminationClassEvent"));
    }

	@Override
	void execute(RoadMap map) throws IllegalArgumentException {
		for (int i = 0; i < cs.size(); ++i) {
			Vehicle v = map.getVehicle(this.cs.get(i).getFirst());
			if (v != null) v.setContClass(this.cs.get(i).getSecond());
			else throw new IllegalArgumentException (Messages.INVALID_OBJECT.formatted("vehicle"));
		}
	}

}
