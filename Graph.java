import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

public class Graph {
	public static Bag<Integer>[] array;
	private final Bag<Integer>[] adj;
	static int b = 0;
	static Date[] endTime = new Date[100];
	Date[] startTime = new Date[100];
	static int[] result = new int[100];
	static Diff[] different = new Diff[100];

	@SuppressWarnings("unchecked")
	public Graph(int V) {
		adj = (Bag<Integer>[]) new Bag[V];
		array = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<Integer>();
			array[v] = new Bag<Integer>();
			adj[v].name = Main.airports_array[v];
		}
		for (int x = 0; x < 100; x++)
			different[x] = new Diff();
	}

	public void addEdge(int v, int w, String id) {
		adj[v].addnext(w, id);
		adj[v].child++;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	public void findAll(int start, int index, int end, Flights[] flights, BufferedWriter write) throws IOException {
		int a = 0;
		for (Bag.Node<Integer> i = adj[index].next; a < adj[index].child; a++, i = i.next) {
			for (int d = 0; d < Main.num; d++) {
				if (array[d].name.equals(adj[index].name)) {
					Main.num = adj[index].num;
					break;
				}
			}
			array[Main.num] = adj[index];
			array[Main.num].num = Main.num;
			Main.num++;
			if (adj[index].next.item == end) {
				array[Main.num] = adj[adj[index].next.item];
				array[Main.num].num = Main.num;

				for (int c = 0; c <= Main.num; c++) {
					for (int z = 0; z < flights.length; z++) {
						if (flights[z].start.equals(array[c].name) && flights[z].end.equals(array[c + 1].name)) {
							array[c].sTime = flights[z].dept_date.getTime();
							array[c].eTime = flights[z].flightEnd;
							array[c].price = flights[z].price;
							array[c].flightId = flights[z].flightId;
						}
					}
				}

				int check = 0;
				int[] result = new int[100];
				for (int c = 0; c < Main.num - 1; c++) {
					if (!array[c].eTime.before(array[c + 1].sTime)) {
						check = 1;
					}
				}
				if (check == 1) {
				} else {
					for (int c = 0; c < Main.num; c++) {
						if ((array[c + 1].name).equals(adj[end].name)) {
							write.write(array[c].flightId + "	" + array[c].name + "->" + array[c + 1].name);
							endTime[b] = array[c].eTime;
						} else {
							write.write(array[c].flightId + "	" + array[c].name + "->" + array[c + 1].name + "||");
							if (array[c].name == adj[start].name)
								startTime[b] = array[c].sTime;
						}
						result[b] = result[b] + array[c].price;
					}
					different[b].getTimeDifference(startTime[b], endTime[b]);
					int hour = Graph.different[b].minute / 60;
					int minute = Graph.different[b].minute - (hour * 60);
					if (minute == 0) {
						write.write("	" + hour + ":" + minute + minute);
					} else {
						write.write("	" + hour + ":" + minute);
					}
					write.write("/" + result[b]);
					b++;
					write.newLine();
				}
			} else {
				findAll(start, i.item, end, flights, write);
			}
		}
	}

	public void findCheapest(int start, int index, int end, Flights[] flights) {
		int a = 0;
		for (Bag.Node<Integer> i = adj[index].next; a < adj[index].child; a++, i = i.next) {
			for (int d = 0; d < Main.num; d++) {
				if (array[d].name.equals(adj[index].name)) {
					Main.num = adj[index].num;
					break;
				}
			}
			array[Main.num] = adj[index];
			array[Main.num].num = Main.num;
			Main.num++;
			if (adj[index].next.item == end) {
				array[Main.num] = adj[adj[index].next.item];
				array[Main.num].num = Main.num;

				for (int c = 0; c <= Main.num; c++) {
					for (int z = 0; z < flights.length; z++) {
						if (flights[z].start.equals(array[c].name) && flights[z].end.equals(array[c + 1].name)) {
							array[c].sTime = flights[z].dept_date.getTime();
							array[c].eTime = flights[z].flightEnd;
							array[c].price = flights[z].price;
							array[c].flightId = flights[z].flightId;
						}
					}
				}

				int check = 0;
				for (int c = 0; c < Main.num - 1; c++) {
					if (!array[c].eTime.before(array[c + 1].sTime)) {
						check = 1;
					}
				}
				if (check == 1) {

				} else {
					for (int c = 0; c < Main.num; c++) {
						if ((array[c + 1].name).equals(adj[end].name)) {
							Main.path[b] = Main.path[b]
									+ (array[c].flightId + "	" + array[c].name + "->" + array[c + 1].name);
							endTime[b] = array[c].eTime;
						} else {
							if (Main.path[b] == "null") {
								startTime[b] = array[c].sTime;
								Main.path[b] = (array[c].flightId + "	" + array[c].name + "->" + array[c + 1].name
										+ "||");
							} else {
								Main.path[b] = Main.path[b] + (array[c].flightId + "	" + array[c].name + "->"
										+ array[c + 1].name + "||");
							}
						}
						result[b] = result[b] + array[c].price;
					}
					int tempResult;
					String tempPath;
					int tempDiff;
					Date tempDate;

					different[b].getTimeDifference(startTime[b], endTime[b]);
					for (int y = 0; y <= b; y++) {
						for (int t = y + 1; t <= b; t++) {
							if (result[y] > result[t] && result[t] != 0) {
								tempResult = result[t];
								result[t] = result[y];
								result[y] = tempResult;
								tempDate = endTime[t];
								endTime[t] = endTime[y];
								endTime[y] = tempDate;
								tempDiff = different[t].minute;
								different[t].minute = different[y].minute;
								different[y].minute = tempDiff;
								tempPath = Main.path[t];
								Main.path[t] = Main.path[y];
								Main.path[y] = tempPath;
							}
						}
					}
					b++;
				}
			} else {
				findCheapest(start, i.item, end, flights);
			}
		}
	}

	public void findQuickest(int start, int index, int end, Flights[] flights) {
		int a = 0;
		for (Bag.Node<Integer> i = adj[index].next; a < adj[index].child; a++, i = i.next) {
			for (int d = 0; d < Main.num; d++) {
				if (array[d].name.equals(adj[index].name)) {
					Main.num = adj[index].num;
					break;
				}
			}
			array[Main.num] = adj[index];
			array[Main.num].num = Main.num;
			Main.num++;
			if (adj[index].next.item == end) {
				array[Main.num] = adj[adj[index].next.item];
				array[Main.num].num = Main.num;

				for (int c = 0; c <= Main.num; c++) {
					for (int z = 0; z < flights.length; z++) {
						if (flights[z].start.equals(array[c].name) && flights[z].end.equals(array[c + 1].name)) {
							array[c].sTime = flights[z].dept_date.getTime();
							array[c].eTime = flights[z].flightEnd;
							array[c].price = flights[z].price;
							array[c].flightId = flights[z].flightId;
						}
					}
				}

				int check = 0;
				for (int c = 0; c < Main.num - 1; c++) {
					if (!array[c].eTime.before(array[c + 1].sTime)) {
						check = 1;
					}
				}
				if (check == 1) {

				} else {
					for (int c = 0; c < Main.num; c++) {
						if ((array[c + 1].name).equals(adj[end].name)) {
							Main.path[b] = Main.path[b]
									+ (array[c].flightId + "	" + array[c].name + "->" + array[c + 1].name);
							endTime[b] = array[c].eTime;
						} else {
							if (Main.path[b] == "null") {
								startTime[b] = array[c].sTime;
								Main.path[b] = (array[c].flightId + "	" + array[c].name + "->" + array[c + 1].name
										+ "||");
							} else {
								Main.path[b] = Main.path[b] + (array[c].flightId + "	" + array[c].name + "->"
										+ array[c + 1].name + "||");
							}
						}
						result[b] = result[b] + array[c].price;
					}

					int tempResult;
					int tempDiff;
					String tempPath;
					Date tempDate;
					different[b].getTimeDifference(startTime[b], endTime[b]);

					for (int y = 0; y <= b; y++) {
						for (int t = y + 1; t <= b; t++) {
							if (different[y].minute > different[t].minute) {
								tempDiff = different[t].minute;
								different[t].minute = different[y].minute;
								different[y].minute = tempDiff;
								tempResult = result[t];
								result[t] = result[y];
								result[y] = tempResult;
								tempDate = endTime[t];
								endTime[t] = endTime[y];
								endTime[y] = tempDate;
								tempPath = Main.path[t];
								Main.path[t] = Main.path[y];
								Main.path[y] = tempPath;
							}
						}
					}
					b++;
				}
			} else {
				findQuickest(start, i.item, end, flights);
			}
		}
	}
}
