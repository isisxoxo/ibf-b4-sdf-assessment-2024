import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public List<String> ReadCSV(String fullPathFilename) throws IOException {
        // Task 1 - Read CSV file (Option 3)
        FileReader fr = new FileReader(fullPathFilename);
        BufferedReader br = new BufferedReader(fr);

        String line;
        List<String> pokemonList = new ArrayList<>();

        while (((line = br.readLine()) != null)) {
            if (!line.isEmpty()) {
                pokemonList.add(line);
            } else {
                continue;
            }
        }

        br.close();
        fr.close();
        return pokemonList;
    }

    public void writeAsCSV(String pokemons, String fullPathFilename) throws IOException {
        // Task 1 - Write to CSV file (Option 3)

        File inputFile = new File(fullPathFilename);
        FileWriter fw = null;
        BufferedWriter bw = null;

        if (!inputFile.exists()) {
            fw = new FileWriter(fullPathFilename);
            bw = new BufferedWriter(fw);
            inputFile.createNewFile();
            bw.write(pokemons);
            bw.flush();
        } else {
            fw = new FileWriter(fullPathFilename, true);
            bw = new BufferedWriter(fw);
            bw.write("\n" + pokemons); // Append to existing file
            bw.flush();
        }
        bw.close();
        fw.close();
    }
}
