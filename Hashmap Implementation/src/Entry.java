
public class Entry<k, v> {
	private k key;
	private v val;

	public Entry() {
	}

	public Entry(k key, v val) {
		this.key = key;
		this.val = val;
	}

	public k getKey() {
		return key;
	}

	public void setKey(k key) {
		this.key = key;
	}

	public v getVal() {
		return val;
	}

	public void setVal(v val) {
		this.val = val;
	}

}
