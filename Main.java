import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
		

public class Main {
	static int airports_count = 0;
	static String[] airports_array;
	static int num = 0;
	static String[] path = new String[100];

	public static void main(String[] args) {
		Read read = new Read();
		int cCount = read.cityCount(args[0]);
		int fCount = read.flightsCount(args[1]);

		Flights[] flights = new Flights[fCount];
		Airports[] airports = new Airports[cCount];

		for (int i = 0; i < cCount; i++)
			airports[i] = new Airports();

		for (int i = 0; i < fCount; i++)
			flights[i] = new Flights();

		read.readFlights(args[1], flights);
		read.readAirports(args[0], airports);
		airports_array = new String[airports_count];
		read.assignAirportsArray(args[0], airports_array);
		Graph graph = new Graph(airports_count);
		int[] temp_end_index = new int[flights.length];
		int[] temp_start_index = new int[flights.length];
		String[] tempId = new String[flights.length];
		try {
			File file = new File(args[3]);
			FileWriter writer = new FileWriter(file);
			BufferedWriter write = new BufferedWriter(writer);
			int count = 0;
			int length = Files.readAllLines(Paths.get(args[1])).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(args[1]))) {
				results[count++] = line;
			}

			for (int i = 0; i < flights.length; i++) {
				for (int j = 0; j < airports_array.length; j++) {
					if (flights[i].start.equals(airports_array[j])) {
						temp_start_index[i] = j;
					} else if (flights[i].end.equals(airports_array[j])) {
						temp_end_index[i] = j;
					}
					tempId[i] = flights[i].flightId;
				}
			}
			for (int i = 0; i < flights.length; i++) {
				graph.addEdge(temp_start_index[i], temp_end_index[i], tempId[i]);
			}
			read.readCommand(args[2], airports, graph, flights, write);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
