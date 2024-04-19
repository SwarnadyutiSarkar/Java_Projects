import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAnalysisTool {
    public static void main(String[] args) {
        String csvFile = "data.csv";
        BufferedReader br = null;
        String line;
        String csvSplitBy = ",";

        List<Double> data = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                for (String value : values) {
                    data.add(Double.parseDouble(value));
                }
            }

            if (!data.isEmpty()) {
                double average = calculateAverage(data);
                double minimum = calculateMinimum(data);
                double maximum = calculateMaximum(data);

                System.out.println("Data Analysis Results:");
                System.out.println("Average: " + average);
                System.out.println("Minimum: " + minimum);
                System.out.println("Maximum: " + maximum);
            } else {
                System.out.println("No data available for analysis!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static double calculateAverage(List<Double> data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    private static double calculateMinimum(List<Double> data) {
        double min = data.get(0);
        for (double value : data) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private static double calculateMaximum(List<Double> data) {
        double max = data.get(0);
        for (double value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
