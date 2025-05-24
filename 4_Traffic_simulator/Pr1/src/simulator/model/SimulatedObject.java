package simulator.model;

import org.json.JSONObject;

import messages.Messages;

public abstract class SimulatedObject {

	protected String _id;

	SimulatedObject(String id) {
		if ( id == null || id.isBlank() )
			throw new IllegalArgumentException(Messages.INVALID_OBJECT_REASON.formatted("ID","empty string"));
		else
			_id = id;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract void advance(int time);

	abstract public JSONObject report();
}
