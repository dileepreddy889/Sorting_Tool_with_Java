package sorting;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        String inputFilePath = null;
        String outputFilePath = null;
        String dataType = "long"; // Default to long
        String sortingType = "natural"; // Default sorting type is natural

        // Process the arguments to get the input/output file paths, data type, and sorting type
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-dataType":
                    if (i + 1 < args.length) {
                        dataType = args[i + 1];
                        i++; // Skip the next argument since it's a value for this parameter
                    }
                    break;
                case "-sortingType":
                    if (i + 1 < args.length) {
                        sortingType = args[i + 1];
                        i++; // Skip the next argument since it's a value for this parameter
                    }
                    break;
                case "-inputFile":
                    if (i + 1 < args.length) {
                        inputFilePath = args[i + 1];
                        i++; // Skip the next argument since it's a value for this parameter
                    }
                    break;
                case "-outputFile":
                    if (i + 1 < args.length) {
                        outputFilePath = args[i + 1];
                        i++; // Skip the next argument since it's a value for this parameter
                    }
                    break;
                default:
                    System.out.println("\"" + args[i] + "\" is not a valid parameter. It will be skipped.");
                    break;
            }
        }

        Scanner scanner = null;
        try {
            // Set up input stream
            if (inputFilePath != null) {
                scanner = new Scanner(new File(inputFilePath));
            } else {
                scanner = new Scanner(System.in);
            }

            // Redirect output to a file if specified
            if (outputFilePath != null) {
                PrintStream fileOut = new PrintStream(new File(outputFilePath));
                System.setOut(fileOut);
            }

            // Call the appropriate method based on the data type and sorting type
            switch (dataType) {
                case "long" -> {
                    if ("byCount".equals(sortingType)) {
                        sortIntegersByCount(scanner);
                    } else {
                        sortIntegers(scanner);
                    }
                }
                case "word" -> {
                    if ("byCount".equals(sortingType)) {
                        sortWordsByCount(scanner);
                    } else {
                        sortWords(scanner);
                    }
                }
                case "line" -> {
                    if ("byCount".equals(sortingType)) {
                        sortLinesByCount(scanner);
                    } else {
                        sortLines(scanner);
                    }
                }
                default -> System.out.println("Unknown data type: " + dataType);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // Sorting integers in natural order
    private static void sortIntegers(Scanner scanner) {
        List<Integer> numberList = new ArrayList<>();

        // Reading integers
        while (scanner.hasNextInt()) {
            numberList.add(scanner.nextInt());
        }

        // Sorting integers
        Collections.sort(numberList);

        // Writing output
        System.out.println("Total numbers: " + numberList.size() + ".");
        System.out.print("Sorted data: ");
        for (int number : numberList) {
            System.out.print(number + " ");
        }
        System.out.println(); // Move to a new line after printing all numbers
    }

    // Sorting integers by count
    private static void sortIntegersByCount(Scanner scanner) {
        List<Integer> numberList = new ArrayList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                numberList.add(scanner.nextInt());
            } else {
                String invalidInput = scanner.next();
                System.out.println("\"" + invalidInput + "\" is not an int. It will be skipped.");
            }
        }
        System.out.println("Total numbers: " + numberList.size() + ".");
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : numberList) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort(Map.Entry.<Integer, Integer>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()));

        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            int percentage = (int) Math.round((double) entry.getValue() / numberList.size() * 100);
            System.out.println(entry.getKey() + ": " + entry.getValue() + " time(s), " + percentage + "%");
        }
    }

    // Sorting words in natural order
    private static void sortWords(Scanner scanner) {
        List<String> wordList = new ArrayList<>();
        while (scanner.hasNext()) {
            wordList.add(scanner.next());
        }

        Collections.sort(wordList);

        System.out.println("Total words: " + wordList.size() + ".");
        System.out.print("Sorted data: ");
        for (String word : wordList) {
            System.out.print(word + " ");
        }
        System.out.println();
    }

    // Sorting words by count
    private static void sortWordsByCount(Scanner scanner) {
        List<String> wordList = new ArrayList<>();
        while (scanner.hasNext()) {
            wordList.add(scanner.next());
        }
        int size = wordList.size();
        System.out.println("Total words: " + size + ".");
        Map<String, Integer> countMap = new HashMap<>();

        for (String word : wordList) {
            countMap.put(word, countMap.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()));

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            int percentage = (int) Math.round((double) entry.getValue() / size * 100);
            System.out.println(entry.getKey() + ": " + entry.getValue() + " time(s), " + percentage + "%");
        }
    }

    // Sorting lines in natural order
    private static void sortLines(Scanner scanner) {
        List<String> lineList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lineList.add(scanner.nextLine());
        }
        int size = lineList.size();
        System.out.println("Total lines: " + size + ".");

        lineList.sort(Comparator.naturalOrder());
        System.out.println("Sorted data:");
        for (String line : lineList) {
            System.out.println(line);
        }
    }

    // Sorting lines by count
    private static void sortLinesByCount(Scanner scanner) {
        List<String> lineList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lineList.add(scanner.nextLine());
        }
        int size = lineList.size();
        System.out.println("Total lines: " + size + ".");
        Map<String, Integer> countMap = new HashMap<>();

        for (String line : lineList) {
            countMap.put(line, countMap.getOrDefault(line, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()));

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            int percentage = (int) Math.round((double) entry.getValue() / size * 100);
            System.out.println(entry.getKey() + ": " + entry.getValue() + " time(s), " + percentage + "%");
        }
    }
}
