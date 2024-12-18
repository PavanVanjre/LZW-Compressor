import java.io.*;
import java.util.*;

public class Encoder {
    public static void main(String[] args) {
        String fileName = args[0];
        int bitLength = Integer.parseInt(args[1]);
        int maxEncodingTableSize = (int) Math.pow(2, bitLength); //calculating the max limit for the encoding table size

        try {
            //Using BufferedReader method of Java to open the file with give name
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {  //iterating each line to read every character and store in string
                fileContent.append(line).append(System.lineSeparator()); // lineSeparator method is used to preserve the newline special character
            }

            Map<String, Integer> symbolTable = createEncodingTable(); //calling the method to initialise the encoding table with ascii values
            List<Integer> codedOutput = new ArrayList<>(); // array to store the coded values


            reader.close();  // Close the reader

            String currentSymbol = "";
            String inputString = fileContent.toString(); //converting from StringBuilder to string
            for (char charItem : inputString.toCharArray()) {
                String newSymbol = currentSymbol + charItem;
                if (symbolTable.containsKey(newSymbol)) { //checks if given input is present in encodingTable
                    currentSymbol = newSymbol;
                } else {
                    //encodingTable does not have the given characters
                    codedOutput.add(symbolTable.get(currentSymbol));  //adds the character value as per table to output array
                    if (symbolTable.size() < maxEncodingTableSize) { //condition to check if the table is not greater than 2^(bit length)
                        symbolTable.put(newSymbol, symbolTable.size()); //adds the characters to the encodingTable
                    }
                    currentSymbol = String.valueOf(charItem); //assigning the current character to the currentSymbol variable
                }
            }

            if (!currentSymbol.isEmpty()) {
                codedOutput.add(symbolTable.get(currentSymbol));
            }

            String outputFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".lzw"; //extacting the input filename and appending ".lzw" to it.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-16BE")); //creating the file with above output file name and opening it to write in mentioned format.

            for (int value : codedOutput) { //iterating over the array to store each coded value to write to output file.
                writer.write(Integer.toString(value));
                writer.newLine();
            }

            // Close the writer
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create the initial encoding table
    public static Map<String, Integer> createEncodingTable() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            String asciiValue = String.valueOf((char) i);
            map.put(asciiValue, i);
        }
        return map;
    }
}
