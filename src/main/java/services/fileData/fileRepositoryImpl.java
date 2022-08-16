package services.fileData;

import services.FileRepository;
import domain.Data;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;


public class fileRepositoryImpl implements FileRepository {

    @Override
    public Data readFromFile(String filePath) {
        ArrayList<String[]> nodeNames = new ArrayList<>();
        Data temp = new Data(nodeNames);
        try {
            String currentReadValues = "";
            File fileToRead = new File(filePath);
            Scanner scan = new Scanner(fileToRead);

            while (scan.hasNextLine()) {
                currentReadValues = scan.nextLine();
                temp.getData().add(handleHowToReadFile(currentReadValues));
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find the file, therefore inputting example manually.\n");
            inputDataManually(temp);
        }
        return temp;
    }

    private String[] handleHowToReadFile(String currentReadValues) {
        return currentReadValues.split(" ");
    }

    private void inputDataManually(Data temp) {
        temp.getData().add(new String[]{"A", "B", "C", "D", "E", "F"});
        temp.getData().add(new String[]{"(A,B)", "(A,C)", "(B,D)", "(C,D)", "(D,E)", "(E,F)"});
    }
}
