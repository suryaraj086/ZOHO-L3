package hyperloop;

public class Passenger {

	private String name;
	private int age;
	private String destination;

	public Passenger(String name, int age, String destination) {
		this.name = name;
		this.age = age;
		this.destination = destination;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "name=" + name + ", age=" + age + ", destination=" + destination + "\n";
	}

}
