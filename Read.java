import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Read {

	public int cityCount(String path) {
		try {
			int count = 0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(path))) {
				results[count++] = line;
			}
			return count;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void readAirports(String path, Airports[] airport) {
		try {
			int count = 0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(path))) {
				results[count++] = line;
			}
			for (int i = 0; i < length; i++) {
				String[] word = results[i].split("	");
				airport[i].cityName = word[0];
				for (int j = 0; j < word.length - 1; j++) {
					Main.airports_count++;
					airport[i].airport[j] = word[j + 1];
					airport[i].airportIndex[j] = Main.airports_count - 1;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readCommand(String path, Airports[] airports, Graph graph, Flights[] flights, BufferedWriter write) {
		int startIndex = 0, endIndex = 0;
		try {
			int count = 0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(path))) {
				results[count++] = line;
			}
			for (int i = 0; i < length; i++) {
				String[] word = results[i].split("	");
				String[] word2 = word[1].split("->");
				for (int k = 0; k < airports.length; k++) {
					if (word2[0].equals(airports[k].cityName)) {
						startIndex = k;
						break;
					}
				}
				for (int k = 0; k < airports.length; k++) {
					if (word2[1].equals(airports[k].cityName)) {
						endIndex = k;
						break;
					}
				}
				if (word[0].equals("listall")) {
					write.write("Command : listall	" + word2[0] + "->" + word2[1] + "	" + word[2]);
					write.newLine();
					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							graph.findAll(airports[startIndex].airportIndex[j], airports[startIndex].airportIndex[j],
									airports[endIndex].airportIndex[k], flights, write);
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listproper")) {
					write.write("Command : listproper	" + word2[0] + "->" + word2[1] + "	" + word[2]);
					write.newLine();
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findCheapest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);

							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.result[g] == Graph.result[0]) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findQuickest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);
							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.different[g].minute == Graph.different[0].minute) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listcheapest")) {
					write.write("Command : listcheapest	" + word2[0] + "->" + word2[1] + "	" + word[2]);
					write.newLine();
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							Main.path[0] = "null";

							graph.findCheapest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);

							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.result[g] == Graph.result[0]) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listquickest")) {
					write.write("Command : listquickest	" + word2[0] + "->" + word2[1] + "	" + word[2]);
					write.newLine();
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findQuickest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);
							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.different[g].minute == Graph.different[0].minute) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listcheaper")) {
					write.write(
							"Command : listcheaper	" + word2[0] + "->" + word2[1] + "	" + word[2] + "	" + word[3]);
					write.newLine();
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findCheapest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);

							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.result[g] == Graph.result[0]
											&& Graph.result[g] < Integer.parseInt(word[3])) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findQuickest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);
							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.different[g].minute == Graph.different[0].minute
											&& Graph.result[g] < Integer.parseInt(word[3])) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listquicker")) {
					write.write(
							"Command : listquicker	" + word2[0] + "->" + word2[1] + "	" + word[2] + "	" + word[3]);
					write.newLine();
					Date date;
					Calendar date2 = Calendar.getInstance();
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
					try {
						date2.setTime(format.parse(word[3]));

					} catch (ParseException ex) {
						ex.fillInStackTrace();
					}
					date = date2.getTime();
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
						Graph.endTime[z] = null;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findCheapest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);

							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.result[g] == Graph.result[0] && Graph.endTime[g].before(date)) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
						Graph.endTime[z] = null;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findQuickest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);
							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.different[g].minute == Graph.different[0].minute
											&& Graph.endTime[g].before(date)) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listexcluding")) {
					write.write("Command : listexcluding	" + word2[0] + "->" + word2[1] + "	" + word[2] + "	"
							+ word[3]);
					write.newLine();
					String[] word3 = word[3].split("");
					String company = word3[0] + word3[1];
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
						Graph.endTime[z] = null;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findCheapest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);

							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.result[g] == Graph.result[0] && Main.path[g].indexOf(company) < 0) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
						Graph.endTime[z] = null;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findQuickest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);
							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.different[g].minute == Graph.different[0].minute
											&& Main.path[g].indexOf(company) < 0) {
										int hour = Graph.different[g].minute / 60;
										int minute = Graph.different[g].minute - (hour * 60);
										if (minute == 0) {
											write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
													+ Graph.result[g]);
										} else {
											write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
													+ Graph.result[g]);
										}
										write.newLine();
									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				} else if (word[0].equals("listonlyfrom")) {
					write.write(
							"Command : listonlyfrom	" + word2[0] + "->" + word2[1] + "	" + word[2] + "	" + word[3]);
					write.newLine();

					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findCheapest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);

							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.result[g] == Graph.result[0]) {
										int counttt = 0;
										String[] id = Main.path[g].split("	");
										if (id[0].indexOf(word[3]) >= 0) {
											counttt++;
										}
										for (int r = 0; r < id.length - 2; r++) {
											String[] id2 = id[r + 1].split("\\|");
											if (id2[2].indexOf(word[3]) >= 0) {
												counttt++;
											}
										}
										if (counttt == id.length - 1) {
											int hour = Graph.different[g].minute / 60;
											int minute = Graph.different[g].minute - (hour * 60);
											if (minute == 0) {
												write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
														+ Graph.result[g]);
											} else {
												write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
														+ Graph.result[g]);
											}
											write.newLine();
										}
									}
								}
							}
						}
					}
					for (int z = 0; z < 100; z++) {
						Graph.result[z] = 0;
						Graph.different[z].minute = 0;
					}

					for (int j = 0; !("İ").equals(airports[startIndex].airport[j]); j++) {
						for (int k = 0; !("İ").equals(airports[endIndex].airport[k]); k++) {
							Graph.b = 0;
							for (int z = 0; z < 100; z++)
								Main.path[z] = "null";

							graph.findQuickest(airports[startIndex].airportIndex[j],
									airports[startIndex].airportIndex[j], airports[endIndex].airportIndex[k], flights);
							if (Main.path[0] != "null") {
								for (int g = 0; g < Graph.b; g++) {
									if (Graph.different[g].minute == Graph.different[0].minute) {
										int counttt = 0;
										String[] id = Main.path[g].split("	");
										if (id[0].indexOf(word[3]) >= 0) {
											counttt++;
										}
										for (int r = 0; r < id.length - 2; r++) {
											String[] id2 = id[r + 1].split("\\|");
											if (id2[2].indexOf(word[3]) >= 0) {
												counttt++;
											}
										}
										if (counttt == id.length - 1) {
											int hour = Graph.different[g].minute / 60;
											int minute = Graph.different[g].minute - (hour * 60);
											if (minute == 0) {
												write.write(Main.path[g] + "	" + hour + ":" + minute + minute + "/"
														+ Graph.result[g]);
											} else {
												write.write(Main.path[g] + "	" + hour + ":" + minute + "/"
														+ Graph.result[g]);
											}
											write.newLine();
										}

									}
								}
							}
						}
					}
					write.newLine();
					write.newLine();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void assignAirportsArray(String path, String[] airports_array) {
		try {
			int count = 0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(path))) {
				results[count++] = line;
			}
			int k = 0;
			for (int i = 0; i < length; i++) {
				String[] word = results[i].split("	");
				for (int j = 0; j < word.length - 1; j++, k++) {
					airports_array[k] = word[j + 1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int flightsCount(String path) {
		try {
			int count = 0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(path))) {
				results[count++] = line;
			}
			return count;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void readFlights(String path, Flights[] flights) {
		try {
			int count = 0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[length];
			for (String line : Files.readAllLines(Paths.get(path))) {
				results[count++] = line;
			}
			for (int i = 0; i < length; i++) {
				String[] word = results[i].split("	");
				flights[i].flightId = word[0];
				String[] word2 = word[1].split("->");
				flights[i].start = word2[0];
				flights[i].end = word2[1];
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
				try {
					flights[i].dept_date.setTime(format.parse(word[2]));
				} catch (ParseException ex) {
					ex.fillInStackTrace();
				}
				String[] word3 = word[3].split(":");
				flights[i].durationHour = Integer.parseInt(word3[0]);
				flights[i].durationMinute = Integer.parseInt(word3[1]);
				flights[i].dept_date.add(Calendar.HOUR_OF_DAY, flights[i].durationHour);
				flights[i].dept_date.add(Calendar.MINUTE, flights[i].durationMinute);
				flights[i].flightEnd = flights[i].dept_date.getTime();
				flights[i].dept_date.add(Calendar.HOUR_OF_DAY, -flights[i].durationHour);
				flights[i].dept_date.add(Calendar.MINUTE, -flights[i].durationMinute);
				flights[i].price = Integer.parseInt(word[4]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
