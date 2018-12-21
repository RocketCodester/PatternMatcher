
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//---------------------------------------------------------------
//         Project
//         PatternMatcher.java
//         Converts between integers and strings.
//---------------------------------------------------------------
public class PatternMatcher {

    static final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    static ArrayList<String> al = new ArrayList<>(Arrays.asList(ALPHABET));
    ArrayList<String> alpha = new ArrayList<>(
            Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    static ArrayList tempDiff;

    static String letter;
    static int numbers[];
    static int N;
    static double[][] arrays;
    static int[][] format;
    static String[][] spaces;
    static int defaultSpacing = 4;
    static String delimiter = " ";
    static Scanner sc = new Scanner(System.in);
    static boolean integer = false;
    static String strings[];

    public static void main(String[] args) throws IOException {
        //letter = getInputStr();//"aceg";//getInputStr();
        getInput();
        //numbers = getInputInt();
        if (!integer) {
            fillArrays(letter);
            displayHorizontally(letter);
        } else {
            for (int i = 0; i < numbers.length; i++) {
                System.out.println(al.get(numbers[i]) + " ");
            }

        }

        //match1();
    }

    public static void match1() {
        GaussianElimination ge = new GaussianElimination();

        double[] B = new double[N];
        double[][] A = new double[N][N];

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                A[i][N - j - 1] = (int) Math.pow(i, j);//System.out.print(A[i][N-j-1]+ " ");
            }
        }
        System.arraycopy(arrays[0], 0, B, 0, N);
        int next = (ge.printNext(ge.solve(A, B)) + 26) % 26;
        System.out.print(next + ": ");
        if (0 <= next && next < 26) {
            System.out.println(al.get(next));
        }
        //System.out.println(al.get(ge.printNext(ge.solve(A, B))));
    }

    public static void match2() {
        int p1 = (int) arrays[1][0];
        int p2 = (int) arrays[1][1];
        int c1 = 1, c2 = 0;
        boolean b1 = true;
        for (int i = 1; i < N; i++) {
            if (arrays[1][i] == p1) {
                c1++;
                b1 = true;
                System.out.println("c1: " + c1);
            } else if ((int) arrays[1][i] == p2) {
                p2 = (int) arrays[1][i];
                c2++;
                b1 = false;
                System.out.println("c2: " + c2);
            } else {
                System.out.println("End");
            }
        }
        System.out.println("Final   c1: " + c1 + " c2: " + c2);
        if (b1) {
            int a = (int) arrays[0][N - 1] + p2;
            System.out.println(a + "\n" + al.get(a));
        } else {
            int a = (int) arrays[0][N - 1] + p1;
            System.out.println(a + "\n" + al.get(a));
        }
    }

    public static void match3() {
        int p1 = (int) arrays[1][0];
        int p2 = (int) arrays[1][1];
        int p3 = (int) arrays[1][2];
        int c1 = 1, c2 = 0, c3 = 0;
        boolean b1 = true;
        for (int i = 1; i < N; i++) {
            if ((int) arrays[1][i] == p1) {
                c1++;
                b1 = true;
                System.out.println("c1: " + c1);
            } else if ((int) arrays[1][i] == p2) {
                p2 = (int) arrays[1][i];
                c2++;
                b1 = false;
                System.out.println("c2: " + c2);
            } else if ((int) arrays[1][i] == p3) {
                p2 = (int) arrays[1][i];
                c2++;
                b1 = false;
                System.out.println("c2: " + c2);
            } else {
                System.out.println("End");
            }
        }
        System.out.println("c1: " + c1 + " c2: " + c2);
        if (b1) {
            int a = (int) arrays[0][N - 1] + p2;
            System.out.println(a + "\n" + al.get(a));
        } else {
            int a = (int) arrays[0][N - 1] + p1;
            System.out.println(a + "\n" + al.get(a));
        }
    }

    public static void fillArrays(String letter) {
        N = letter.length();
        format = new int[N + 1][N];
        arrays = new double[N + 1][N];
        //Extract first set of indicies
        for (int i = 0; i < N; i++) {
            arrays[0][i] = al.indexOf(String.valueOf(letter.charAt(i)));
        }

        //Calculate the differences between the ith+1 indicies
        for (int i = 0; i < arrays[0].length; i++) {
            arrays[i + 1] = difBetweenIndiciesMod(arrays[i], i);
        }
        //Calculate the N of the number and put them in format array
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N; j++) {
                format[i][j] = String.valueOf(arrays[i][j]).length();
            }
        }
    }

    public static void debug() {
        //System.out.printf("%50s\n", Arrays.toString(letter.toCharArray()));
        //System.out.printf("%-10s%44s\n", "Indicies:", Arrays.toString(arrays[0]));
        for (int i = 0; i < arrays[0].length; i++) {
            System.out.println(i + ") Arrays of Differences: " + Arrays.toString(arrays[i + 1]));
        }
        for (int i = 0; i < N + 1; i++) {
            // System.out.println(i + ") Format" + Arrays.toString(format[i]));
        }
    }

    public static void verifyArrays() {
        System.out.println("                          " + Arrays.toString(letter.toCharArray()));
        System.out.println("Indicies:                 " + Arrays.toString(arrays[0]));
        for (int i = 0; i < arrays[0].length; i++) {
            System.out.println(i + ") Arrays of Differences: " + Arrays.toString(arrays[i + 1]));
        }
    }

    public static void displayVertically(String letter) {
        for (int i = 0; i < N; i++) {
            System.out.print(letter.charAt(i) + ":" + space(defaultSpacing - format[0][i] + 1));
            for (int j = 0; j < N - i; j++) {
                System.out.print(arrays[j][i] + space(defaultSpacing + format[0][j] - format[j + 1][i], delimiter));
            }
            System.out.println();
        }
    }

    public static void displayHorizontally(String letter) {
        ////////////////////Get letters/////////////////////////////////////////
        for (int i = 0; i < N; i++) {
            System.out.printf("%-6s", letter.charAt(i)); //System.out.print(space(defaultSpacing + format[0][i] - 1, delimiter) + letter.charAt(i));
        }

        System.out.println();
        ///////////////////Get numbers//////////////////////////////////////////
        /*for (int i = 0; i < N; i++) {
            System.out.printf("%-4s", arrays[0][i]); //System.out.print(space(defaultSpacing, delimiter) + arrays[0][i]);  
        }
        System.out.println();*/
        ///////////////////Get differences//////////////////////////////////////

        for (int j = 0; j < N + 1; j++) {
            for (int i = 0; i < N - j; i++) {
                System.out.printf("%-6s", (int) arrays[j][i]);
            }
            System.out.println();
        }

        /*for (int j = 0; j < N; j++) {
            for (int i = 0; i < N - j-1; i++) {
                System.out.print(space(defaultSpacing + format[0][i] - format[j + 1][i], delimiter) + arrays[j + 1][i]);
            }

            System.out.println();
        }*/
    }

    public static String space(int N) {
        StringBuilder outputBuffer = new StringBuilder(N);
        for (int i = 0; i < N; i++) {
            outputBuffer.append(" ");
        }
        return outputBuffer.toString();
    }

    public static String space(int N, String s) {
        StringBuilder outputBuffer = new StringBuilder(N);
        for (int i = 0; i < N; i++) {
            outputBuffer.append(s);
        }
        return outputBuffer.toString();
    }

    public static double[] difBetweenIndicies(double[] array, int count) {
        for (int i = 0; i < arrays[0].length - count - 1; i++) {
            arrays[count + 1][i] = array[i + 1] - array[i];
        }
        return arrays[count + 1];
    }

    public static double[] difBetweenIndiciesMod(double[] array, int count) {
        for (int i = 0; i < arrays[0].length - count - 1; i++) {
            arrays[count + 1][i] = (array[i + 1] - array[i]) % 26;
        }
        return arrays[count + 1];
    }

    public static String getInputStr() {
        System.out.print("Enter a string: ");
        return sc.next();
    }

    public static String[] getInputStr2() {
        for (int i = 0; i < 2; i++) {
            System.out.print("Enter String " + (i + 1) + ": ");
            strings[i] = sc.next();
        }
        System.out.print("Enter a string: ");
        return strings;
    }

    public static int[] getInputInt() {
        System.out.print("Enter the number of integers to enter: ");
        int count = sc.nextInt();
        numbers = new int[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Enter integer " + (i + 1) + ": ");
            numbers[i] = sc.nextInt();
        }
        return numbers;
    }

    public static void getInput() {
        System.out.println("1: String to Integer\n2: Integers to Strings\n3: Strings to Integers");
        switch (sc.nextInt()) {
            case 2:
                numbers = getInputInt();
                integer = true;
                break;
            case 3:
                strings = getInputStr2();
                break;
            default:
                letter = getInputStr();
                break;
        }
    }
}
