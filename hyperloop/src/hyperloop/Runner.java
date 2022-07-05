package hyperloop;

import java.util.Scanner;

public class Runner {
	NodeWeighted A = new NodeWeighted(0, "A");
	NodeWeighted B = new NodeWeighted(1, "B");
	NodeWeighted C = new NodeWeighted(2, "C");
	NodeWeighted D = new NodeWeighted(3, "D");
	NodeWeighted E = new NodeWeighted(4, "E");
	NodeWeighted F = new NodeWeighted(5, "F");
	NodeWeighted G = new NodeWeighted(6, "G");

	GraphWeighted graphWeighted = new GraphWeighted(true);

	public void addSourceAndDestination(String source, String destination, int distance) throws Exception {
		graphWeighted.addEdge(getObject(source), getObject(destination), distance);
	}

	public NodeWeighted getObject(String source) throws Exception {
		if (source == null) {
			throw new Exception("No pod found");
		}
		switch (source) {
		case "A":
			return A;
		case "B":
			return B;
		case "C":
			return C;
		case "D":
			return D;
		case "E":
			return E;
		case "F":
			return F;
		default:
			break;
		}
		return null;
	}

	public static void main(String[] args) {
		Runner run = new Runner();
		Scanner scan = new Scanner(System.in);
		Hyperloop hyper = new Hyperloop();
		boolean bool = true;
//		graphWeighted.addEdge(A, C, 7);
//		graphWeighted.addEdge(B, D, 2);
//		graphWeighted.addEdge(B, C, 2);
//		graphWeighted.addEdge(A, B, 3);
//		graphWeighted.addEdge(B, E, 5);
//		graphWeighted.addEdge(C, E, 1);
//		graphWeighted.addEdge(D, E, 3);
//		String out = graphWeighted.DijkstraShortestPath(A, C);
//		System.out.println(out);
		String Spoint = null;
		while (bool) {
			System.out.println("Enter the command");
			String inp = scan.nextLine();
			if (inp.contains("INIT")) {
				Spoint = inp.charAt(inp.length() - 1) + "";
				int totalStation = Integer.parseInt(inp.charAt(inp.length() - 3) + "");
				for (int i = 0; i < totalStation; i++) {
					String val = scan.nextLine();
					String[] arr = val.split(" ");
					try {
						run.addSourceAndDestination(arr[0], arr[1], Integer.parseInt(arr[2]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (inp.contains("ADD_PASSENGER")) {
				int length = Integer.parseInt(inp.charAt(inp.length() - 1) + "");
				for (int i = 0; i < length; i++) {
					String data = scan.nextLine();
					String[] arr = data.split(" ");
					Passenger pass = new Passenger(arr[0], Integer.parseInt(arr[1]), arr[2]);
					hyper.addPassenger(pass);
				}
				System.out.println("Added");
				hyper.sortList();
			} else if (inp.contains("START_POD")) {
				int members = Integer.parseInt(inp.charAt(inp.length() - 1) + "");
				for (int i = 0; i < members; i++) {
					Passenger pass = null;
					try {
						pass = hyper.getHighAgePassenger();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
					String path = null;
					try {
						path = run.graphWeighted.DijkstraShortestPath(run.getObject(Spoint),
								run.getObject(pass.getDestination()));
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					System.out.println(pass.toString() + path);
					run.graphWeighted.resetNodesVisited();
				}
			} else if (inp.contains("PRINT_Q")) {
				System.out.println(hyper.passengerQueue());
			} else {
				System.out.println("Invalid command");
			}
		}
		scan.close();
	}
}