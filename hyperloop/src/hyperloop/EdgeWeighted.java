package hyperloop;

public class EdgeWeighted {

	NodeWeighted source;
	NodeWeighted destination;
	double weight;

	EdgeWeighted(NodeWeighted s, NodeWeighted d, double w) {

		source = s;
		destination = d;
		weight = w;
	}

	public String toString() {
		return String.format("(%s -> %s, %f)", source.name, destination.name, weight);
	}

}
