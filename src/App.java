import java.io.Console;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws Exception {

        String fullPathFilename = args[0]; // Rush2.csv file name

        System.out.println(
                "Welcome to Pokemon Gaole Legend 4 Rush 2\n\n(1) View unique list of Pokemon in the selected stack\n(2) Find next 5 stars Pokemon occurrence\n(3) Create new Pokemon stack and save (append) to csv file\n(4) Print distinct Pokemon and cards count\n(q) to exit the program");

        Console console = System.console();
        String input = console.readLine("Enter your selection >");

        if (input.equals("q")) {
            clearConsole();
        }
        // else if (input.equals("1")) {}
    }

    public static void clearConsole() throws IOException { // Clear console and terminate and exit program
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Task 1
    public static void pressAnyKeyToContinue() {
        // your code here
    }

    // Task 1
    public static void printHeader() {

        // Task 1 - your code here
    }

    // Task 1
    public static void printExitMessage() {

        // Task 1 - your code here
    }

    // Task 1
    public static void savePokemonStack(String pokemonStack, String filename) {

        // Task 1 - your code here
    }

    // Task 2
    public static void printUniquePokemonStack(Integer stack) {
        // Task 2 - your code here
    }

    // Task 2
    public static void printNext5StarsPokemon(String enteredPokemon) {
        // Task 2 - your code here

    }

    // Task 2
    public static void printPokemonCardCount() {
        // Task 2 - your code here
    }

}
