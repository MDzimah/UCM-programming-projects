package simulator.model;

public abstract class Event implements Comparable<Event> {

  private static long _counter = 0;

  protected int _time;
  protected long _time_stamp;

  Event(int time) {
    if (time < 1)
      throw new IllegalArgumentException("Invalid time: "+time);
    else {
      _time = time;
      _time_stamp = _counter++;
    }
  }

  public int getTime() { return _time; }

  @Override
  public int compareTo(Event o) {
	  if (this._time < o._time) return -1;
	  else if(this._time == o._time) {
		  if (this._time_stamp < o._time_stamp) return -1;
		  else if(this._time_stamp == o._time_stamp) return 0;
		  else return 1;
	  }
	  else return 1;
  }

  abstract void execute(RoadMap map);
}