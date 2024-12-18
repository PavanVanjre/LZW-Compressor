import java.io.*;
import java.util.*;

public class Decoder {
    public static void main(String[] args) {
        String fileName = args[0];
        int bitLength = Integer.parseInt(args[1]);
        int maxTableSize = (int) Math.pow(2, bitLength);
        try {

            //Using BufferedReader method of Java to open the file with give name and reading the file with UTF-16BE format
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), "UTF-16BE"));

           List<Integer> inputContent = new ArrayList<Integer>(); //array to store the integer coded value fromm file
            String line;
            while ((line = reader.readLine()) != null) { //iterating each line to read every coded value
                String[] tokens = line.split("\\s+"); // regex \\s+ is used to detect the whitespaces and separate the values
                for (String token : tokens) {
                    inputContent.add(Integer.parseInt(token));
                }
            }
            reader.close(); //closing the reader
            Map<Integer, String> symbolTable = createDecodingTable(); //calling the method to initialise the Decoding table with ascii values

            int currentCode = inputContent.get(0);
            String output = symbolTable.get(currentCode); //string that stores the decoded values being initialised with first character of input content
            String currentSymbol;
            String newSymbol;

            for (int i = 1; i < inputContent.size(); i++) {
                int nextCode = inputContent.get(i);
                if (symbolTable.containsKey(nextCode)) { //checks if given input is present in encodingTable
                    currentSymbol = symbolTable.get(currentCode); //assigns the value of currentCode from decoding table
                    newSymbol = symbolTable.get(nextCode); //assigning the next value to decode
                } else {
                    //encodingTable does not have the given characters
                    currentSymbol = symbolTable.get(currentCode); //assigns the value of currentCode from decoding table
                    newSymbol = currentSymbol + currentSymbol.charAt(0); //concatenate the first character of string with current symbol
                }

                output += newSymbol; //string concatenation is done to append the decoded value to output variable
                if (symbolTable.size() < maxTableSize) {  //condition to check if the table is not greater than 2^(bit length)
                    symbolTable.put(symbolTable.size(), currentSymbol + newSymbol.charAt(0)); //adds the characters to the encodingTable
                }

                currentCode = nextCode;
            }
            String outputFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_decoded.txt"; //extracting the input filename and appending "_decoded.txt" to it.
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName)); //creating the file with specified name and opening it to write the decoded value.
            writer.write(output);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create the initial encoding table
    public static Map<Integer, String> createDecodingTable(){
        Map<Integer, String> map = new HashMap<Integer, String>();
        for(int i =0;i<256;i++){
            String a =String.valueOf((char) i);
            map.put(i,a);
        }
        return map;
    }
}
