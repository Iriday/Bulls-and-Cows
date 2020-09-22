package bullscows;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        var scn = new Scanner(System.in);
        var rand = new Random();

        String gameValue = generateGameValue(rand, 4);
        String userValue = getUserValueFromConsole(scn);

        int[] result = grade(gameValue, userValue);

        String outStr = createOutputString(result, gameValue);
        System.out.println(outStr);
    }

    public static String generateGameValue(Random random, int len) {
        if (len < 1) {
            throw new IllegalArgumentException("Error: len should be >=1.");
        }
        if (len > 10) {
            throw new IllegalArgumentException("Error: can't generate a number with a length of "
                    + len + ", because there aren't enough unique digits.");
        }
        var gameVal = new LinkedHashSet<Integer>();

        while (gameVal.size() < len) {
            gameVal.add(random.nextInt(10));
        }
        return gameVal.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    public static String getUserValueFromConsole(Scanner scn) {
        while (true) {
            String userValue = scn.nextLine();
            if (userValue.strip().matches("[\\d]{4}")) {
                return userValue;
            }
            System.out.println("Incorrect input, please try again.");
        }
    }

    /**
     * @return int[] where: [0] = num of cows, [1] = num of bulls
     */
    public static int[] grade(String gameValue, String userValue) {
        int[] result = new int[2];
        for (int i = 0; i < gameValue.length(); i++) {
            int index = gameValue.indexOf(userValue.charAt(i));
            if (index != -1) {
                if (index == i) {
                    result[1]++;
                } else {
                    result[0]++;
                }
            }
        }
        return result;
    }

    private static String createOutputString(int[] result, String gameValue) {
        var sb = new StringBuilder("Grade: ");
        int cows = result[0];
        int bulls = result[1];

        if (cows == 0 && bulls == 0) {
            sb.append("none");
        } else if (cows > 0 && bulls > 0) {
            sb.append(cows).append(" cows and ").append(bulls).append(" bulls");
        } else if (cows > 0) {
            sb.append(cows).append(" cows");
        } else if (bulls > 0) {
            sb.append(bulls).append(" bulls");
        } else {
            throw new IllegalStateException("Something went wrong");
        }
        sb.append(". The secret code is ").append(gameValue).append(".");
        return sb.toString();
    }
}
