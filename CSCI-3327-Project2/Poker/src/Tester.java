

public class Tester {

    public static void main(String[] args) {
        /* Run the sumulation 1,000,000 times. */
        Simulator sim = new Simulator(1000000);
        sim.simulate();
        sim.printResults();
    }
}
