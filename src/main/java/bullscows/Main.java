package bullscows;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        var scn = new Scanner(System.in);
        var rand = new Random();

        int secretCodeLength = getSecretCodeLengthFromConsole(scn);
        String secretCode = generateSecretCode(rand, secretCodeLength);
        System.out.println("Okay, let's start a game!");

        for (int i = 1; true; i++) {
            System.out.println("Turn: " + i + ":");
            String userCode = getUserCodeFromConsole(scn, secretCodeLength);

            int[] outcome = grade(secretCode, userCode);

            String outStr = createOutputString(outcome);
            System.out.println(outStr);
            // check if win
            if (outcome[1] == secretCodeLength) {
                System.out.println("Congratulations! you guessed the secret code!");
                return;
            }
        }
    }

    public static int getSecretCodeLengthFromConsole(Scanner scn) {
        while (true) {
            System.out.println("Please enter the secret's code length: ");
            try {
                return Integer.parseInt(scn.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: incorrect input, please try again");
            }
        }
    }

    public static String generateSecretCode(Random random, int len) {
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

    public static String getUserCodeFromConsole(Scanner scn, int codeLen) {
        while (true) {
            String userValue = scn.nextLine().trim();
            if (userValue.matches("[\\d]{" + codeLen + "}")) {
                return userValue;
            }
            System.out.println("Error: incorrect input, please try again.");
        }
    }

    /**
     * @return int[] where: [0] = num of cows, [1] = num of bulls
     */
    public static int[] grade(String secretCode, String userCode) {
        int[] result = new int[2];
        for (int i = 0; i < secretCode.length(); i++) {
            int index = secretCode.indexOf(userCode.charAt(i));
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

    private static String createOutputString(int[] result) {
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
        return sb.toString();
    }
}
