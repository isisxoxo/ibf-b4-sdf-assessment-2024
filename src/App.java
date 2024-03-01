import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class App {

    static List<String> pokemonList = new ArrayList<>();
    static Map<Integer, List<String>> pokemonMap = new HashMap<>();
    static Console console = System.console();

    public static void main(String[] args) throws Exception {

        String fullPathFilename = args[0]; // Rush2.csv file name
        FileService fs = new FileService();
        pokemonList = fs.ReadCSV(fullPathFilename); // Read Rush2.csv file as ArrayList

        for (int i = 0; i < pokemonList.size(); i++) {
            pokemonMap.put(i + 1, Arrays.asList(pokemonList.get(i).split(",")));
        }

        while (true) {
            clearConsole();
            printHeader(); // Start with menu

            String input = console.readLine("Enter your selection >");

            if (input.equals("q")) {

                printExitMessage();
                break;

            } else if (input.equals("1")) {

                boolean isInvalidStack = false;
                while (!isInvalidStack) {
                    try {
                        Integer stack = Integer.parseInt(
                                console.readLine("Display the list of unique Pokemon in stack (1 - 8) >\n"));
                        printUniquePokemonStack(stack);
                        isInvalidStack = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid stack. Please input a correct number instead.");
                    }
                }

            } else if (input.equals("2")) {

                String enteredPokemon = console.readLine(
                        "Search for the next occurrence of 5 stars Pokemon in all stacks based on entered Pokemon > \n");
                printNext5StarsPokemon(enteredPokemon);

            } else if (input.equals("3")) {

                String pokemons = console.readLine("Create a new Pokemon stack and save to a new file >\n");
                String fullPathFilename3 = console.readLine("Enter filename to save (e.g. path/filename.csv) >\n");
                while (!fullPathFilename3.endsWith(".csv")) {
                    System.out.println("Invalid file name. Please provide a file with .csv extension.");
                    fullPathFilename3 = console.readLine("Enter filename to save (e.g. path/filename.csv) >\n");
                }
                savePokemonStack(pokemons, fullPathFilename3);

            } else if (input.equals("4")) {

                System.out.println("Top 10 Pokemon across all stacks: ");
                printPokemonCardCount();

            } else {
                System.out.println("Choose a valid option from the menu.");
            }
            pressAnyKeyToContinue();
        }
    }

    public static void clearConsole() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Task 1 - Menu
    public static void printHeader() {
        System.out.println(
                "Welcome to Pokemon Gaole Legend 4 Rush 2\n\n(1) View unique list of Pokemon in the selected stack\n(2) Find next 5 stars Pokemon occurrence\n(3) Create new Pokemon stack and save (append) to csv file\n(4) Print distinct Pokemon and cards count\n(q) to exit the program");
    }

    // Task 1 - Exit message (input = q)
    public static void printExitMessage() {
        System.out.println("\nThank you for using the program...\nHope to see you soon...");
    }

    // Task 1 - FileService
    public static void savePokemonStack(String pokemonStack, String filename) throws IOException {
        FileService fileService = new FileService();
        fileService.writeAsCSV(pokemonStack, filename);
    }

    // Task 2 - Press any key to continue
    public static void pressAnyKeyToContinue() {
        console.readLine("Press any key to continue...");
    }

    // Task 2 - Option 1
    public static void printUniquePokemonStack(Integer stack) {
        // Task 2 - Print unique pokemon from selected stack

        if (stack >= 1 && stack <= 8) {
            List<String> pokemonsFromStack = pokemonMap.get(stack);
            List<String> pokemonsFromStackUnique = pokemonsFromStack.stream().distinct()
                    .collect(Collectors.toList());
            for (int i = 0; i < pokemonsFromStackUnique.size(); i++) {
                System.out.println(i + 1 + " ==> " + pokemonsFromStackUnique.get(i));
            }
        } else {
            System.err.println("Invalid stack number.");
        }

    }

    // Task 2 - Option 2
    public static void printNext5StarsPokemon(String enteredPokemon) {

        for (Map.Entry<Integer, List<String>> entry : pokemonMap.entrySet()) {

            System.out.println("Set " + entry.getKey());

            List<String> allPokemon = entry.getValue();

            if (allPokemon.contains(enteredPokemon)) {
                for (int i = 0; i < allPokemon.size(); i++) {
                    String currentPokemon = allPokemon.get(i);
                    if (currentPokemon.startsWith("5*")) {
                        // If pokemon found in stack and got subsequent 5 star
                        int numCardsToGo = i - allPokemon.indexOf(enteredPokemon);
                        if (numCardsToGo > 0) {
                            System.out.println(currentPokemon + " found. " + numCardsToGo + " cards to go.");
                            break;
                        }
                    } else {
                        if (i == (allPokemon.size() - 1)) {
                            System.out.println("No 5 stars Pokemon found subsequently in the stack.");
                        }
                    }
                }
            } else {
                // If pokemon not found in stack
                System.out.println(enteredPokemon + " not found in this set.");
            }

        }
    }

    // Task 2 - Option 4
    public static void printPokemonCardCount() {
        // Task 2 - Top 10 Pokemon across all stacks from CSV file
        List<String> allStacksPokemon = new LinkedList<>();
        Map<String, Integer> pokemonCountMap = new HashMap<>();

        Set<String> pokemonSet = new HashSet<>(pokemonList); // Get unique list of pokemon stack from csv

        for (String line : pokemonSet) {
            String[] oneStack = line.split(",");

            for (String string : oneStack) {
                allStacksPokemon.add(string);
            }
        }

        for (String pokemon : allStacksPokemon) {
            if (pokemonCountMap.containsKey(pokemon)) {
                pokemonCountMap.put(pokemon, pokemonCountMap.get(pokemon) + 1);
            } else {
                pokemonCountMap.put(pokemon, 1);
            }
        }

        pokemonCountMap = pokemonCountMap.entrySet().stream()
                .sorted((k1, k2) -> k2.getValue().compareTo(k1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // keyMapper
                        Map.Entry::getValue, // valueMapper
                        (e1, e2) -> e1, LinkedHashMap::new)); // mapSupplier to store in LinkedHashMap

        int index = 1;
        for (Map.Entry<String, Integer> entry : pokemonCountMap.entrySet()) {
            System.out.println("Pokemon " + index++ + " : " + entry.getKey() + ", Cards Count: " + entry.getValue());
        }
    }
}