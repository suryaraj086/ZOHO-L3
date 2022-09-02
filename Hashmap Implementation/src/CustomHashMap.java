import java.util.LinkedList;

public class CustomHashMap<k, v> {
	private int size = 0;

	private int currentCapacity = 16;

	private LinkedList<Entry<k, v>>[] arr = new LinkedList[currentCapacity];

	public void put(k key, v val) {
		int index = key.hashCode() % currentCapacity;
		LinkedList<Entry<k, v>> list = arr[index];
		if (list == null) {
			list = new LinkedList<>();
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey() == key) {
				list.get(i).setVal(val);
				return;
			}
		}
		Entry<k, v> obj = new Entry<k, v>(key, val);
		list.add(obj);
		arr[index] = list;
		size++;
	}

	public void remove(k key) {
		int index = key.hashCode() % currentCapacity;
		LinkedList<Entry<k, v>> list = arr[index];
		if (list == null) {
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey() == key) {
				list.remove(i);
				size--;
				return;
			}
		}
	}

	public v get(k key) {
		int index = key.hashCode() % currentCapacity;
		LinkedList<Entry<k, v>> list = arr[index];
		if (list == null) {
			return null;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey() == key) {
				return list.get(i).getVal();
			}
		}
		return null;
	}

	public LinkedList<k> keySet() {
		LinkedList<k> val = new LinkedList<>();
		for (int i = 0; i < arr.length; i++) {
			LinkedList<Entry<k, v>> list = arr[i];
			if (list != null) {
				for (int k = 0; k < list.size(); k++) {
					val.add(list.get(i).getKey());
				}
			}
		}
		return val;
	}

	public boolean containsKey(k key) {
		int index = key.hashCode() % currentCapacity;
		LinkedList<Entry<k, v>> list = arr[index];
		if (list == null) {
			return false;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey() == key) {
				return true;
			}
		}
		return false;
	}

	public int size() {
		return size;
	}

}