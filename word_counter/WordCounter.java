import java.util.Scanner;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a sentence:");
        String sentence = scanner.nextLine();

        int wordCount = countWords(sentence);

        System.out.println("Word count: " + wordCount);

        scanner.close();
    }

    public static int countWords(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return 0;
        }

        String[] words = sentence.split("\\s+");
        return words.length;
    }
}
