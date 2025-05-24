package simulator.factories;

import java.util.*;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
    private Map<String, Builder<T>> _builders;
    private List<JSONObject> _buildersInfo;

    public BuilderBasedFactory() {
        _builders = new HashMap<>();
        _buildersInfo = new LinkedList<>();
    }

    public BuilderBasedFactory(List<Builder<T>> builders) {
        this();
        for (Builder<T> b: builders) {
            addBuilder(b);
        }
    }

    public void addBuilder(Builder<T> b) {
        _builders.put(b.get_type_tag(), b);
        _buildersInfo.add(b.get_info());
    }

    @Override
    public T create_instance(JSONObject info) {
        if (info == null) {
            throw new IllegalArgumentException("'info' cannot be null");
        }

        String type = info.getString("type");
        Builder<T> builder = _builders.get(type);
        if (builder!= null) {
            JSONObject data = info.has("data")? info.getJSONObject("data"): new JSONObject();
            T instance = builder.create_instance(data);
            if (instance!= null) {
                return instance;
            }
        }

        throw new IllegalArgumentException("Unrecognized 'info': " + info.toString());
    }

    @Override
    public List<JSONObject> get_info() {
        return Collections.unmodifiableList(_buildersInfo);
    }
}