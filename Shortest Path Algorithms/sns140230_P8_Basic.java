package tp;

import java.util.Scanner;

/**
 * @author Suraj Sangani given a directed graph as input, if the graph has
 *         uniform weights (i.e., same positive weights for all edges), then it
 *         runs BFS to find shorest paths. Otherwise, if the graph is a
 *         directed, acyclic graph (DAG), then it runs DAG shortest paths.
 *         Otherwise, if the graph has only nonnegative weights, then it runs
 *         Dijkstra's algorithm. If all these test fail, then it runs the
 *         Bellman-Ford algorithm. If the graph has negative cycles, then it
 *         prints the message "Booyah!!". The graph stores the edges as a linked
 *         list of edges. The graph is stored as an adjacency list.
 * 
 *         The user has to type "Output" in order to print the answer.
 */
public class sns140230_P8_Basic {
	private static int count = 0;
	public static int result = 0;
	private static int V;// Number of vertices in the graph
	private static int E;// Number of edges in the graph
	private static int s;// Source the graph
	private static GraphNode[] DFSadjlist;// Adjacency list
	Queue tq = new Queue();
	private static int to = 0;// source node
	private static int from = 0;// destination node

	public sns140230_P8_Basic() {
	}

	/**
	 * Function to Load the graph into Memory. It also decides which algorithm
	 * to run based on the input
	 * 
	 */
	public boolean loadGraph() {

		sns140230_P8_Basic graphAdjList = new sns140230_P8_Basic();
		Scanner scanner = new Scanner(System.in);
		int count = 0;
		this.V = scanner.nextInt();// read the number of vertices
		this.E = scanner.nextInt();// read the number of edges
		int[] edges = new int[E];// array to store the edge weights
		this.from = scanner.nextInt();// read the source
		this.to = scanner.nextInt();// read the destination
		DFSadjlist = new GraphNode[V + 1];
		for (int i = 1; i <= V; i++) {
			DFSadjlist[i] = new GraphNode(i);
		}

		// Read all the edges in the graph
		scanner.nextLine();
		while (scanner.hasNext()) {
			String s1 = scanner.nextLine();
			if (s1.equals("Output"))
				break;
			String delimiter = " ";
			String[] temp;

			// System.out.println(s1);
			temp = s1.split(delimiter);
			// add the source, destination and weight of the edge into the graph
			addEdge(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]),
					Integer.valueOf(temp[2]));
			edges[count] = Integer.valueOf(temp[2]);// store the weight of the
													// edge
			count++;
		}
		// If uniform weights run BFS
		for (int i = 0; i < count; i++) {
			int temp = edges[0];
			if (edges[i] == temp && i == count - 1) {
				graphAdjList.BFS(to);
				return true;
			}
		}
		Boolean f = false;
		for (int i = 0; i < count; i++) {
			if (edges[i] > 0) {
				f = true;
			} else {
				f = false;
				break;
			}

		}
		// If non negative weights run Dijkstra
		for (int i = 0; i < count; i++) {
			if (edges[i] > 0 && f && i == count - 1) {
				graphAdjList.Dijkstra(to);
				return true;
			}
		}
		// If no cycles in graph run DAG else run BellMan Ford
		for (int i = 0; i < count; i++) {
			Boolean flag = graphAdjList.cycledetection(DFSadjlist[from]);
			if (flag == false) {
				graphAdjList.DAG(to);
				return true;
			} else {
				graphAdjList.BellmanFord(to);
				return true;
			}
		}

		return true;
	}

	/**
	 * Function to detect a cycle in thr graph
	 * 
	 * @param graphNode
	 *            The source of the graph
	 * @return true if cycle detected else false
	 */
	public boolean cycledetection(GraphNode graphNode) {
		Boolean flag = false;
		graphNode.color = 1;
		LinkedList temp = graphNode.edges;
		Node node = temp.first;
		while (node != null) {
			Edge edge = (Edge) node.values;
			if (edge.v.color == 0) {
				edge.v.parent = graphNode;
				flag = cycledetection(edge.v);
			} else if (edge.v.color == 1)// seen node so cycle detected
			{
				flag = true;
				return flag;
			}
			node = node.next;
		}
		graphNode.color = 2;
		return flag;
	}

	/**
	 * Function to add the edge into memory
	 * 
	 * @param u
	 *            Source of the edge
	 * @param v
	 *            Destination of the edge
	 * @param w
	 *            Weight of the edge
	 */
	void addEdge(int u, int v, int w) {
		DFSadjlist[u].add(new Edge(DFSadjlist[u], DFSadjlist[v], w));
	}

	/**
	 * Helper function to print the graph
	 */
	public void printGraph() {
		// print the graph
		System.out.println("Vertices: " + V + " Edges " + E);
		for (int i = 1; i <= V; i++) {
			LinkedList list = DFSadjlist[i].edges;
			System.out.println(i + " with degree " + DFSadjlist[i].outdegree);
			list.print();
		}
	}

	public static void main(String[] args) {
		sns140230_P8_Basic graphAdjList = new sns140230_P8_Basic();
		graphAdjList.loadGraph();
	}

	/**
	 * BellMan Ford - Start from the source
	 * 
	 * @param dest
	 */
	public void BellmanFord(int dest) {
		BF(DFSadjlist[from], dest);
	}

	/**
	 * Implementation of the Bellman Ford Algorithm
	 * 
	 * @param graphnode
	 *            source of the graph
	 * @param dest
	 *            destination of the graph
	 */
	public void BF(GraphNode graphnode, int dest) {

		long startTime = System.currentTimeMillis();
		// Initialize
		Boolean flag = false;
		int answer = 0;
		int c = 0;
		DFSadjlist[from].d = 0;
		Queue q = new Queue();
		// Start with the root node
		q.enqueue(graphnode.v);
		while (q.hasItems()) {
			int x = q.dequeue();
			GraphNode u = new GraphNode();
			for (int i = 1; i < DFSadjlist.length; i++)// search for the
														// dequeued int in the
														// adjacency list
			{
				if (DFSadjlist[i].v == x) {
					u = DFSadjlist[i];
				}
			}
			LinkedList temp = u.edges;// create a linked list of edges
			Node node = temp.first;// first node in the linked list
			while (node != null) {
				Edge edge = (Edge) node.values;// access the edge
				if (edge.v.d > edge.u.d + edge.w)// Relax all the edges in the
													// graph
				{
					edge.v.d = edge.u.d + edge.w;
					edge.v.parent = edge.u;
					q.enqueue(edge.v.v);// enqueue the destination of the edge
				}
				if (edge.v.v == dest)// found the destination
				{
					answer = edge.v.d;
					flag = true;
				}
				node = node.next;
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("B-F " + answer + " " + (endTime - startTime)
				+ "msec");
		if (V < 100) {
			for (int i = 1; i <= V; i++) {
				if (DFSadjlist[i].parent != null)
					System.out.println(i + " " + DFSadjlist[i].d + " "
							+ DFSadjlist[i].parent.v);
				else
					System.out.println(i + " " + DFSadjlist[i].d + " -");
			}
		}
	}

	/**
	 * Function to topologically sort the graph
	 */
	public void TopologicalSort() {
		for (int i = 1; i < V; i++) {
			// System.out.println(i);
			Sort(DFSadjlist[i]);
		}
	}

	/**
	 * Function to carry out the topological sort i.e. graph will be sorted top
	 * to bottom or left to right
	 * 
	 * @param graphnode
	 */
	public void Sort(GraphNode graphnode) {
		graphnode.visited = true;
		tq.enqueue(graphnode.v);// enqueue the root node
		LinkedList temp = graphnode.edges;
		Node node = temp.first;
		while (node != null) {
			Edge edge = (Edge) node.values;
			if (edge.v.visited == false) {
				Sort(edge.v);// recursive call
			}
			node = node.next;
		}
	}

	/**
	 * Function to find the shortest path in a Directed Acyclic Graph
	 * 
	 * @param dest
	 *            destination
	 */
	public void DAG(int dest) {
		DAG(DFSadjlist[from], dest, true);// find shortest path from source to
											// destination
		dest = 0;
		// find shortest path from source to all paths
		for (int i = 1; i <= V; i++) {
			dest = i;
			System.out.print(i + " ");
			// System.out.println();
			Boolean flag = DAG(DFSadjlist[from], dest, false);
			if (flag == false && V < 100)
				System.out.println("-");
		}
	}

	public Boolean DAG(GraphNode graphnode, int dest, Boolean flag) {
		long startTime = System.currentTimeMillis();
		// Initialize
		Boolean f = false;
		int pred = 0;
		GraphNode destination = new GraphNode();
		for (int i = 1; i < DFSadjlist.length; i++) {
			if (DFSadjlist[i].v == dest) {
				destination = DFSadjlist[i];
			}
		}
		destination.d = Integer.MAX_VALUE;
		graphnode.d = 0;
		for (int i = 2; i <= V; i++) {
			DFSadjlist[i].d = Integer.MAX_VALUE;
		}
		int c = 0;
		int ct = 0;
		int count = 0;
		TopologicalSort();
		DFSadjlist[from].d = 0;
		int minarray[] = new int[1000000];
		int predarray[] = new int[1000000];
		Queue q = new Queue();
		// Enqueue the source node
		q.enqueue(graphnode.v);
		while (q.hasItems()) {
			int x = q.dequeue();
			GraphNode u = new GraphNode();
			for (int i = 1; i < DFSadjlist.length; i++) {
				if (DFSadjlist[i].v == x) {
					u = DFSadjlist[i];
				}
			}
			LinkedList temp = u.edges;
			Node node = temp.first;
			while (node != null) {
				Edge edge = (Edge) node.values;
				if (edge.v.d > edge.u.d + edge.w)// Relax the edges
				{
					edge.v.d = edge.u.d + edge.w;
					edge.v.parent = edge.u;
					c++;
					q.enqueue(edge.v.v);

					if (edge.v.v == dest)// found the destination
					{
						minarray[ct] = edge.v.d;
						predarray[ct] = edge.u.v;
						ct++;
					}
				}
				node = node.next;
			}
			count++;
		}
		long endTime = System.currentTimeMillis();
		int min = Integer.MAX_VALUE;
		// display the minimum path from source to destination
		for (int i = 0; i < minarray.length; i++) {
			if (minarray[i] != 0 && minarray[i] < min) {
				min = minarray[i];
				// System.out.println(min + "minarray");
			}
		}
		if (min == Integer.MAX_VALUE) {
			System.out.print(0 + " ");
		} else if (flag == false && V < 100)
			System.out.print(min + " ");
		else
			System.out.print("DAG " + min + " " + (endTime - startTime)
					+ "msec");
		int counter = 0;
		// display the predecessor node
		for (int i = 0; i < predarray.length; i++) {
			if (predarray[i] != 0) {
				counter++;
			}
		}
		if (counter != 0) {
			f = true;
			if (flag == false && V < 100) {
				System.out.println(predarray[counter - 1]);
			}

			else
				System.out.println();
		}
		return f;
	}

	/**
	 * Implementation of Dijsktra's Algorithm
	 * 
	 * @param dest
	 *            destination
	 */
	public static void Dijkstra(int dest) {
		Dijkstra(DFSadjlist[from], dest, true);// find shortest path from source
												// to destination
		dest = 0;
		// find shortest path from source to all vertices
		for (int i = 1; i <= V; i++) {
			dest = i;
			if (V < 100)
				System.out.print(i + " ");

			Boolean flag = Dijkstra(DFSadjlist[from], dest, false);
			if (i == from)
				System.out.println("0" + " - ");
			else if (flag == false && V < 100)
				System.out.println("INF" + " - ");
		}
	}

	/**
	 * Function to implement the Dijsktra's Algorithm
	 * 
	 * @param graphnode
	 *            the source of the path
	 * @param dest
	 *            the destination of the path
	 * @param flag
	 *            to find if we have to find the shortest path from source to
	 *            destination or source to all vertices
	 * @return
	 */
	public static Boolean Dijkstra(GraphNode graphnode, int dest, Boolean flag) {
		long startTime = System.currentTimeMillis();
		// Initialize
		graphnode.d = 0;
		for (int i = 1; i <= V; i++) {
			if (DFSadjlist[i].v != graphnode.v)
				DFSadjlist[i].d = Integer.MAX_VALUE;
		}
		int c = 0;
		Queue q = new Queue();
		// Enqueue the source node
		q.enqueue(graphnode.v);

		while (q.hasItems()) {
			int x = q.dequeue();
			GraphNode u = new GraphNode();// Search for the int in the adjacency
											// list
			for (int i = 1; i < DFSadjlist.length; i++) {
				if (DFSadjlist[i].v == x) {
					u = DFSadjlist[i];
				}
			}
			int pred = u.v;// the predecessor node
			LinkedList temp = u.edges;
			Node node = temp.first;
			while (node != null) {
				Edge edge = (Edge) node.values;
				if (edge.v.d > edge.u.d + edge.w)// relax the edge
				{
					edge.v.d = edge.u.d + edge.w;
					edge.v.parent = edge.u;
					q.enqueue(edge.v.v);
					c++;
					if (edge.v.v == dest)// found the destination
					{
						long endTime = System.currentTimeMillis();
						if (flag == true)
							System.out.println("Dij " + c + " "
									+ (endTime - startTime) + "msec");
						else if (!flag && V < 100)
							System.out.println(c + " " + edge.v.parent.v + " ");
						while (q.hasItems()) {
							q.dequeue();
						}
						return true;
					}
				}
				node = node.next;
			}
		}
		return false;
	}

	/**
	 * Function to carry out Breadth First Search
	 * 
	 * @param dest
	 */
	public void BFS(int dest) {
		BFSVisit(DFSadjlist[from], dest, true);// find shortest path from source
												// to destination
		dest = 0;
		// find shortest path from source to all vertices
		for (int i = 1; i <= V; i++) {
			dest = i;
			if (V < 100)
				System.out.print(i + " ");
			// System.out.println();
			BFSVisit(DFSadjlist[from], dest, false);
		}

	}

	/**
	 * Implementation of Breadth First Search(Reference from Cormen)
	 * 
	 * @param graphNode
	 *            the source of the path
	 * @param dest
	 *            destination of the path
	 * @param flag
	 *            flag to find if we have to find the shortest path from source
	 *            to destination or source to all vertices
	 */
	private void BFSVisit(GraphNode graphNode, int dest, boolean flag) {
		long startTime = System.currentTimeMillis();
		// Initialize
		for (int i = 1; i < DFSadjlist.length; i++) {
			DFSadjlist[i].color = 0;
		}
		graphNode.color = 1;
		int count = 0;
		graphNode.d = 0;
		Queue q = new Queue();
		q.enqueue(graphNode.v);
		// System.out.println(graphNode.v);
		while (q.hasItems()) {
			count++;
			int x = q.dequeue();
			// System.out.println("X" + x);
			GraphNode u = new GraphNode();
			for (int i = 1; i < DFSadjlist.length; i++)// search for the int in
														// the adjacency list
			{
				if (DFSadjlist[i].v == x) {
					u = DFSadjlist[i];
				}
			}
			int pred = u.v;// predecessor node
			LinkedList temp = u.edges;
			Node node = temp.first;
			while (node != null) {
				Edge edge = (Edge) node.values;
				if (edge.v.color == 0) {
					edge.v.color = 1;
					edge.v.d = u.d + 1;
					edge.v.parent = u;
					q.enqueue(edge.v.v);
					// System.out.println(edge.u.v + " " + edge.v.v);
					if (edge.v.v == dest) {
						// System.out.println(count + "count");
						result = edge.w * (count);
						long endTime = System.currentTimeMillis();

						if (flag == true)
							System.out.println("BFS " + (result) + " "
									+ (endTime - startTime) + "msec");
						else if (flag == false && V < 100)
							System.out.println((result) + " " + pred + " ");
						return;
					}
				}
				node = node.next;
			}

		}

	}
}

/**
 * Class to store the edges of the graph
 * 
 *
 */

class Edge {
	GraphNode u; // from node
	GraphNode v; // to node
	int w; // weight

	public Edge(GraphNode u, GraphNode v, int w) {
		super();
		this.u = u;
		this.v = v;
		this.w = w;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (u == null) {
			if (other.u != null)
				return false;
		} else if (!u.equals(other.u))
			return false;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		if (w != other.w)
			return false;
		return true;
	}

	public String toString() {
		return v + " with weight-" + w;
	}
}

/**
 * Implementation of Queue using Linked Lists
 * 
 * @author Darshan Sangani
 *
 */
class Queue {

	private LinkedList list = new LinkedList();

	// GraphNode graphnode;
	public void enqueue(int x) {
		list.insertEnd(x);
	}

	public int dequeue() {
		int a = list.deleteFront();
		return a;
	}

	public boolean hasItems() {
		return !list.isEmpty();
	}

	public int[] iterator() {
		int x[] = new int[100];
		// System.out.println("Here");
		int ct = 0;
		list.print();
		while (!list.isEmpty()) {
			Node a = list.first;
			x[ct] = list.deleteFront();
			// System.out.println("In queue" + x[ct]);
			ct++;
			a = a.next;
		}
		for (int i = 0; i < ct; i++)
			list.insertEnd(x[i]);
		list.print();
		return x;
	}

}

class GraphNode {

	public int d;
	int v; // node name
	LinkedList edges;
	int outdegree;
	public int color;
	public GraphNode parent;
	boolean visited;

	GraphNode(int v) {
		this.v = v;
		edges = new LinkedList();
		outdegree = 0;
		d = Integer.MAX_VALUE;
		visited = false;
	}

	public GraphNode() {
		// TODO Auto-generated constructor stub
	}

	public void add(Edge edge) {
		this.edges.insertEnd(edge);
		outdegree++;
	}

	public void delete(Edge edge) {
		this.edges.deleteNode(edge);
		outdegree--;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphNode other = (GraphNode) obj;
		if (v != other.v)
			return false;
		return true;
	}

	public String toString() {
		return "node " + v;
	}
}

class Node {
	Node next;
	int value;
	Object values;

	public Node(int data) {
		super();
		this.value = data;
	}

	public Node(Object data) {
		super();
		this.values = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public int getData() {
		return value;
	}

}

// class Node {
// int data;
// Node next;
//
// Node(int x){
// data = x;
// }
// }
class LinkedList {
	// String name;
	Node first;

	LinkedList() {
		// name = "";
		first = null;
	}

	LinkedList(String n) {
		// name = n;
		first = null;
	}

	public int size(LinkedList a) {
		int count = 0;
		Node n = a.first;
		while (n != null) {
			n = n.next;
			count++;
		}
		return count;
	}

	public Node firstnode(LinkedList a) { // returns first node of the
											// linkedlist
		return first;
	}

	public void delete(LinkedList a) { // delete the linkedlist, assigning
										// first->null
		first = null;
	}

	public void insertFront(Edge x) { // inserting elements in front eg:
										// 'Linkedlist a is 9->8->7->null so
										// when inserted for data 6, now a is
										// 6->9->8->7->null
		Node n = new Node(x);
		n.next = first;
		first = n;
	}

	public void insertFront(int x) { // inserting elements in front eg:
										// 'Linkedlist a is 9->8->7->null so
										// when inserted for data 6, now a is
										// 6->9->8->7->null
		Node n = new Node(x);
		n.next = first;
		first = n;
	}

	public int deleteFront() { // deleting elements in front eg: 'Linkedlist a
								// is 9->8->7->null so when deleted from front,
								// now a is 8->7->null
		Node p = first;
		Node q = p.next;
		first = q;
		return p.value;
	}

	public void reverse(Node currentNode) {
		// check for empty list
		if (currentNode == null)
			return;

		/*
		 * if we are at the TAIL node: recursive base case:
		 */
		if (currentNode.next == null) {
			// set HEAD to current TAIL since we are reversing list
			first = currentNode;
			return; // since this is the base case
		}

		reverse(currentNode.next);
		currentNode.next.next = currentNode;
		currentNode.next = null; // set "old" next pointer to NULL
	}

	public void deleteNode(Edge a) { // deleting elements by node eg:
										// 'Linkedlist a is 9->8->7->null so
										// when deleted for 7, now a is
										// 9->8->null
		Node p = first;
		if (p.next != null) {
			Node q = p.next;
			while (p.values != a) {
				q = p;
				p = p.next;
			}
			p = p.next;
			q.next = p;
		} else {
			first = null;
		}
	}

	public void print() { // printing the linkedlist in this format with
							// elements pointing to next element '9->8->7->null'
							// ending with null
		Node p = first;
		while (p != null) {
			System.out.print(p.value + "->");
			p = p.next;
		}
		System.out.print("null");
		System.out.println("");
	}

	public boolean isEmpty() { // to check whether the linkedlist is empty
		if (first == null)
			return true;
		else
			return false;
	}

	public void insertEnd(Edge x) { // inserting elements in front eg:
									// 'Linkedlist a is 9->8->7->null so when
									// inserted for data 6, now a is
									// 9->8->7->6->null
		if (isEmpty())
			insertFront(x);
		else {
			Node n = new Node(x);
			Node p = first;
			while (p.next != null) {
				p = p.next;
			}
			p.next = n;
		}
	}

	public void insertEnd(int x) { // inserting elements in front eg:
									// 'Linkedlist a is 9->8->7->null so when
									// inserted for data 6, now a is
									// 9->8->7->6->null
		if (isEmpty())
			insertFront(x);
		else {
			Node n = new Node(x);
			Node p = first;
			while (p.next != null) {
				p = p.next;
			}
			p.next = n;
		}
	}
}
