package newlist;

//import java.util.Arrays;
//import java.util.List;

public class Runner {

	public static void main(String[] args) {
//		List<E>
		CustomArrayList<Integer> arr = new CustomArrayList<>();
		arr.add(1);
		arr.add(10);
		arr.add(100);
		System.out.println("The element at 0th index is " + arr.get(0));
		System.out.println("The array list is " + arr);
		arr.remove(0);
		System.out.println(arr);
		arr.add(10000);
		System.out.println(arr);
		System.out.println("The size before add is " + arr.size());
		arr.add(10);
		System.out.println("The size after add is " + arr.size());
		System.out.println("The array list after add " + arr);
//		System.out.println(Arrays.toString(arr.toArray()));
//		arr.add(156);
//		System.out.println(arr);
	}
}
