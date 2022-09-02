//import java.util.Map;

public class Runner {

	public static void main(String[] args) {
//		Map<K, V>
		CustomHashMap<Integer, Integer> map = new CustomHashMap<>();
		map.put(1, 5);
		map.put(2, 10);
		map.put(3, 15);
		map.put(4, 20);

		System.out.println("the value of 1 is " + map.get(1));
		System.out.println("The value of 2 is " + map.get(2));
		System.out.println("The value of 3 is " + map.get(3));

		System.out.println("is key 1 is present " + map.containsKey(1));
		System.out.println("The size of the map " + map.size());
		map.remove(3);
		System.out.println("the value of 3 after removal " + map.get(3));
		System.out.println("The size of the map after removal " + map.size());
		map.put(1, 20);
		System.out.println("the overwrite of key 1 " + map.get(1));

	}
}
