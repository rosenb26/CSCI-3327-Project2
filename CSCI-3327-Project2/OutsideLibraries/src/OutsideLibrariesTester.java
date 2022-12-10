
import org.jfree.data.xy.XYSeries;

public class OutsideLibrariesTester {

    public static void main(String[] args) {
        OutsideLibraries tester = new OutsideLibraries();
        
        XYSeries originalData = tester.originalSeries();
        XYSeries saltedData = tester.saltedSeries(originalData);
        XYSeries smoothedData = tester.smoothedSeries(saltedData);

        tester.original(originalData);
        tester.salt(saltedData);
        tester.smooth(smoothedData);
    }
}
