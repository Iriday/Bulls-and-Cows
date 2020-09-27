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
        final String symbols = "0123456789abcdefghijklmnopqrstuvwxyz";
        var scn = new Scanner(System.in);
        var rand = new Random();

        int secretCodeLength = getSecretCodeLengthFromConsole(scn);
        int numOfPossibleSymbols = getNumOfPossibleSymbolsFromConsole(scn, secretCodeLength);

        String initialMessage = createInitialGameMessage(secretCodeLength, symbols, numOfPossibleSymbols);
        System.out.println(initialMessage);
        System.out.println("Okay, let's start a game!");

        String secretCode = generateSecretCode(rand, secretCodeLength, symbols, numOfPossibleSymbols);

        for (int i = 1; true; i++) {
            System.out.println("Turn: " + i + ":");
            String userCode = getUserCodeFromConsole(scn, secretCodeLength, symbols, numOfPossibleSymbols);

            int[] outcome = grade(secretCode, userCode);

            String outMsg = createOutcomeMessage(outcome);
            System.out.println(outMsg);
            // check if win
            if (outcome[1] == secretCodeLength) {
                System.out.println("Congratulations! you guessed the secret code!");
                return;
            }
        }
    }

    public static int getSecretCodeLengthFromConsole(Scanner scn) {
        System.out.println("Please enter the secret's code length:");
        while (true) {
            try {
                int num = Integer.parseInt(scn.nextLine().trim());
                if (num >= 1 && num <= 36) {
                    return num;
                }
                System.out.println("Error: secret's code length should be >=1 and <=36, please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Error: incorrect input, please tray again.");
            }
        }
    }

    public static int getNumOfPossibleSymbolsFromConsole(Scanner scn, int secretCodeLength) {
        System.out.println("Input the number of possible symbols in the code:");
        while (true) {
            try {
                int num = Integer.parseInt(scn.nextLine().trim());
                if (num < secretCodeLength) {
                    System.out.println("Error: it's not possible to generate a code with a length of "
                            + secretCodeLength + " with " + num + " unique symbols. Please try again.");
                } else if (num > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z). Please try again.");
                } else {
                    return num;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: incorrect input, please tray again.");
            }
        }
    }

    public static String createInitialGameMessage(int codeLength, String symbols, int numOfPossibleSymbols) {
        var sb = new StringBuilder("The secret is prepared: ")
                .append("*".repeat(codeLength))
                .append(" (0-");
        if (numOfPossibleSymbols <= 10) {
            sb.append(symbols.charAt(numOfPossibleSymbols - 1));
        } else {
            sb.append("9, a-")
                    .append(symbols.charAt(numOfPossibleSymbols - 1));
        }
        sb.append(").");

        return sb.toString();
    }

    public static String generateSecretCode(Random random, int codeLength, String symbols, int numOfPossibleSymbols) {
        if (codeLength < 1) {
            throw new IllegalArgumentException("Error: codeLength should be >=1.");
        }
        if (numOfPossibleSymbols > symbols.length()) {
            throw new IllegalArgumentException("Error: numOfPossibleSymbols should be <= symbols.length");
        }
        if (codeLength > numOfPossibleSymbols) {
            throw new IllegalArgumentException("Error: codeLength should <= numOfPossibleSymbols");
        }
        var gameVal = new LinkedHashSet<Character>();

        while (gameVal.size() < codeLength) {
            gameVal.add(symbols.charAt(random.nextInt(numOfPossibleSymbols)));
        }
        return gameVal.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    public static String getUserCodeFromConsole(Scanner scn, int codeLen, String symbols, int numOfPossibleSymbols) {
        while (true) {
            String userCode = scn.nextLine().trim();
            if (isCodeCorrect(userCode, codeLen, symbols, numOfPossibleSymbols)) {
                return userCode;
            }
            System.out.println("Error: incorrect input, please try again.");
        }
    }

    // simple but slow, can be optimized
    public static boolean isCodeCorrect(String code, int codeLen, String symbols, int numOfPossibleSymbols) {
        return code.matches("[" + symbols.substring(0, numOfPossibleSymbols) + "]{" + codeLen + "}");
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

    public static String createOutcomeMessage(int[] outcome) {
        var sb = new StringBuilder("Grade: ");
        int cows = outcome[0];
        int bulls = outcome[1];

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
