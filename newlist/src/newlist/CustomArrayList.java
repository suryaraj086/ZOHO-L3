package newlist;

public class CustomArrayList<E> {

	private int size;
	private int capacity = 16;
	private Object[] array = new Object[capacity];

	public void add(E element) {
		if (size == capacity) {
			resize();
		}
		array[size] = element;
		size++;
	}

	public Object[] toArray() {
		return array;
	}

	public Object get(int index) {
		return array[index];
	}

	public void set(int index, E val) {
		array[index] = val;
	}

	public int size() {
		return size;
	}

	public boolean contains(E element) {
		for (int i = 0; i < size; i++) {
			if (array[i] == element) {
				return true;
			}
		}
		return false;
	}

	public int indexOf(E element) {
		for (int i = 0; i < size; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}

	private void resize() {
		capacity = 2 * capacity;
		Object temp[] = array;
		array = new Object[capacity];
		for (int i = 0; i < temp.length; i++) {
			array[i] = temp[i];
		}
	}

	public void clear() {
		array = new Object[capacity];
	}

	public void remove(int index) {

		if (index >= size || index < 0) {
			return;
		}

		Object temp[] = new Object[capacity];
		int j = 0;
		for (int i = 0; i < size; i++) {
			if (i != index) {
				temp[j] = array[i];
				j++;
			}
		}

		size--;
		array = temp;
	}

	public String toString() {
		String out = "[";
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				out += array[i] + ",";
			}
		}
		out = out.substring(0, out.length() - 1);
		out += "]";
		return out;
	}

}
