import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public static String readFileAsString(String filepath) {
        StringBuilder output = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filepath))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static ArrayList<ElectionResult> parse2016ElectionResults(String data) {
        ArrayList<ElectionResult> results = new ArrayList<>();

        String[] rows = data.split("\n");

        for (int i = 1; i < rows.length; i++) {
            rows[i] = fixRow(rows[i]);
            String[] line = rows[i].split(",");

            double votesDem = Double.parseDouble(line[1]);
            double votesGop = Double.parseDouble(line[2]);
            double totalVotes = Double.parseDouble(line[3]);
            double perDem = Double.parseDouble(line[4]);
            double perGop = Double.parseDouble(line[5]);
            int diff = Integer.parseInt(line[6]);
            double perPointDiff = Double.parseDouble(line[7]);
            String stateAbbr = line[8];
            String countryName = line[9];
            int combinedFips = Integer.parseInt(line[10]);

            ElectionResult result = new ElectionResult(votesDem, votesGop, totalVotes, perDem, perGop, diff, perPointDiff, stateAbbr, countryName, combinedFips);
            result.resultToString();
            results.add(result);

        }
        return results;
    }


    private static String fixRow(String row) {

        while (row.indexOf("\"") != -1) {
            int quoteStartIndex = row.indexOf("\"");
            int quoteEndIndex = row.indexOf("\"", quoteStartIndex + 1);

            String wordWQuotes = row.substring(quoteStartIndex, quoteEndIndex + 1);

            String unfixedWord = row.substring(quoteStartIndex + 1, quoteEndIndex);
            String fixedWord = unfixedWord.replace(",", "");

            row = row.replace(wordWQuotes, fixedWord);

        }

        while (row.indexOf("%") != -1) {
            int signIndex = row.indexOf("%");
            row = row.substring(0, signIndex) + row.substring(signIndex + 1, row.length());

        }

        return row;

    }
}