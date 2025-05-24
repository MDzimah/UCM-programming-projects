package simulator.factories;

import org.json.JSONObject;

import messages.Messages;

public abstract class Builder<T> {
	private String _type_tag;
	private String _desc;

	public Builder(String typeTag, String desc) {
		if (typeTag == null || desc == null || typeTag.isBlank() || desc.isBlank())
			throw new IllegalArgumentException(Messages.INVALID_OBJECT.formatted("type/desc"));

		_type_tag = typeTag;
		_desc = desc;
	}

	public String get_type_tag() {
		return _type_tag;
	}

	public JSONObject get_info() {
		JSONObject info = new JSONObject();
		info.put("type", _type_tag);
		info.put("desc", _desc);

		JSONObject data = new JSONObject();
		fill_in_data(data);
		info.put("data", data);
		return info;
	}

	protected void fill_in_data(JSONObject o) {}

	@Override
	public String toString() {
		return _desc;
	}

	/*Through dynamic linkage, each builder (RoundRobin, SetContClassEvent, SetWeather, etc.)
    retrieves its information from the "data" JSONObject. The keys used for such are predefined 
    for each type of object (for example, if I have an InterCityRoad that is trying to retrieve 
    its maximum allowed speed, then I will use the key "maxspeed" to accomplish this task) 
	
	To sum it up:
	"create instance" gets the values of each key in data and uses it
	to create an instance of the object depending on the class. It does
	this via various getters (that depend on the type referenced by the key)*/
	protected abstract T create_instance(JSONObject data);
}
