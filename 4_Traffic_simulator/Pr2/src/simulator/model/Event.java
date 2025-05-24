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

/* Quizás más fácil haberlo hecho con Comparator en lugar de Comparable:

	Comparator<Person> cmp = new Comparator<Person>() {
	  @Override
	  public int compare(Person a, Person b) {
	    return a.getEdad() - b.getEdad();
	  }
	};
	//Con expresión lambda: Comparator<Person> cmpEdad = (a, b) -> Integer.compare(a.getEdad(), b.getEdad());
	
	Con orden natural y método estático: Comparator<Person> cmpEdad2 = Comparator.comparingInt(Person::getEdad);
	
	Útil para decidir por qué se ordena primero: 
	Comparator<Person> cmp =
	  Comparator.comparing(Person::getApellido)
	            .thenComparing(Person::getNombre)
	            .thenComparingInt(Person::getEdad);
            

	Y luego pasas el comparator a la clase por el constructor:
	Set<Pair> s = new TreeSet<>(cmp);
 */
 