package heaps.element;

public class Pair {

	/** priority */
	public int p;

	/** value */
	public int v;

	public Pair(int priority, int value) {
		this.p = priority;
		this.v = value;
	}

	@Override
	public String toString() {
		return "[p=" + p + ",v=" + v + "]";
	}

}