
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DataProcessor {

    private ArrayList<ArrayList<String>> data;
    private final double TOTAL_GAMES_IN_DATA_SET;

    public DataProcessor() {
        this.data = new ArrayList<>();
        this.TOTAL_GAMES_IN_DATA_SET = 2560;
    }

    public void populateData() throws FileNotFoundException {
        File directory = new File("NFL_Game_Stats");

        for (File file : directory.listFiles()) {
            ArrayList<String> temp = new ArrayList<>();
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                temp.add(reader.nextLine());
            }
            temp.remove(0);
            this.data.add(temp);
        }
    }

    public void gamesByYear() {
        int firstYear = 2010;
        int totalGames = 0;
        for (ArrayList<String> x : this.data) {
            totalGames += x.size();
            System.out.println("Year: " + firstYear + "; totalGames: " + x.size());
            firstYear++;
        }
        System.out.println("\nTotal Games over 10 years: " + totalGames);
    }

    public double pointsPerGame() {
        double total = 0;
        int counter = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] gameData = game.split(",");
                total += Double.valueOf(gameData[8]) + Double.valueOf(gameData[13]);
                counter++;
            }
        }
        return total / counter;
    }

    public void writeGameTotalsToFile() throws FileNotFoundException {
        File file = new File("gameTotals.txt");
        PrintWriter writer = new PrintWriter(file);
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] gameData = game.split(",");
                writer.println(gameData[8] + " " + gameData[13] + " "
                        + (Integer.parseInt(gameData[8]) + Integer.parseInt(gameData[13])));
            }
        }
        writer.close();
    }

    public double atleast100RushingYards(String team) {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (team.equalsIgnoreCase("home")) {
                    if (Integer.parseInt(temp[5]) >= 100) {
                        count++;
                    }
                }
                else {
                    if (Integer.parseInt(temp[10]) >= 100) {
                        count++;
                    }
                }
            }
        }
        return count / this.TOTAL_GAMES_IN_DATA_SET;
    }

    public double team10Points(String team) {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (team.equalsIgnoreCase("home")) {
                    if (Integer.parseInt(temp[8]) >= 10) {
                        count++;
                    }
                }
                else {
                    if (Integer.parseInt(temp[13]) >= 10) {
                        count++;
                    }
                }
            }
        }
        return count / this.TOTAL_GAMES_IN_DATA_SET;
    }

    public double teamUnder10Points(String team) {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (team.equalsIgnoreCase("home")) {
                    if (Integer.parseInt(temp[8]) < 10) {
                        count++;
                    }
                }
                else {
                    if (Integer.parseInt(temp[13]) < 10) {
                        count++;
                    }
                }
            }
        }
        return count / this.TOTAL_GAMES_IN_DATA_SET;
    }

    public double each100RushingYards() {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (Integer.parseInt(temp[5]) >= 100 && Integer.parseInt(temp[10]) >= 100) {
                    count++;
                }
            }
        }
        return count / this.TOTAL_GAMES_IN_DATA_SET;
    }

    public double each10Points() {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (Integer.parseInt(temp[8]) >= 10 && Integer.parseInt(temp[13]) >= 10) {
                    count++;
                }
            }
        }
        return count / this.TOTAL_GAMES_IN_DATA_SET;
    }

    public int passingYardsInterval(int lower, int upper, String team) {
        int index;
        if (team.equalsIgnoreCase("home")) {
            index = 6;
        }
        else {
            index = 11;
        }
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");

                if (Integer.parseInt(temp[index]) >= lower && Integer.parseInt(temp[index]) <= upper) {
                    count++;
                }

            }
        }
        return count;
    }

    public double homeConditionalRushingYards() {
        int numeratorCount = 0;
        int denominatorCount = 0;

        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (Integer.parseInt(temp[4]) >= 20) {
                    denominatorCount++;
                    if (Integer.parseInt(temp[5]) >= 100) {
                        numeratorCount++;
                    }
                }
            }
        }
        return 1.0 * numeratorCount / denominatorCount;
    }

    public int maxAwayPassing() {
        int max = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (Integer.parseInt(temp[11]) > max) {
                    max = Integer.parseInt(temp[11]);
                }
            }
        }
        return max;
    }

    public int[] oddEven() {
        int[] oddEven = new int[2];
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if ((Integer.parseInt(temp[8]) + Integer.parseInt(temp[13])) % 2 == 1) {
                    oddEven[0]++;
                }
                else {
                    oddEven[1]++;
                }
            }
        }
        return oddEven;
    }

    public int gamesOver() {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (Integer.parseInt(temp[14]) == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int exactlyProjectedTotal() {
        int count = 0;
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                if (Integer.parseInt(temp[14]) == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public void turnoverDistributionFunction() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("turnoverDistribution.txt"));
        Map<Integer, Integer> map = new TreeMap<>();
        for (ArrayList<String> year : this.data) {
            for (String game : year) {
                String[] temp = game.split(",");
                map.put(Integer.parseInt(temp[12]), map.getOrDefault(Integer.parseInt(temp[12]), 0) + 1);
            }
        }
        for (int x : map.keySet()) {
            writer.println(x + " " + map.get(x));
        }
        writer.close();
    }
}
