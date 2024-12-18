Programming Project 1 :LZW Compression

The Lempel–Ziv–Welch (LZW) algorithm is a lossless data compression algorithm. LZW is an
adaptive compression algorithm that does not assume prior knowledge of the input data distribution.
This algorithm works well when the input data is sufficiently large and there is redundancy in the data.
Two examples of commonly used file formats that use LZW compression are the GIF image format
served from websites and the TIFF image format. LZW compression is also suitable for compressing
text files, and is the algorithm in the compress Unix file compression utility.
This algorithm has two steps:
1. Encoding/Compressing
2. Decoding/Decompressing

Here the encoding table, or dictionary, computed during the encoding process
does not need to be explicitly transmitted. It can be regenerated from the coded/compressed data.



Encoding Implementation

* The file name and bit length is taken from the command line arguments and maximum table size is calculated.
* To read the input strings the BufferedReader method of Java is used.
* The HashMap data structure is employed to create a mapping of characters to their corresponding ASCII values. In this mapping, the keys are of string type, representing characters, while the values are of integer type, 
representing the ASCII values associated with those characters.
* Iterating over the each character and checking if it contains in the table
  <br>if yes we are just storing in currentSymbol variable and going to next character
  <br/>if character/pattern is not present in table then we in the output array we just add the previous character/pattern(excluding current character) value to the output array and append the encoding table with current character/pattern.
*  The size of the hashmap is checked before adding a new key and value, if it is less than maximum table size,then only the new key value pair is added. 
   The output is encoded to the .lzw file by write() method and each encoded value in 'codedOutput' array is written to it.
 
Algorithm for encoding <br />
<br />MAX_TABLE_SIZE=2(bit_length)   //bit_length is number of encoding bits
<br />initialize TABLE[0 to 255] = code for individual characters
<br />STRING = null
<br />while there are still input symbols:
<br />SYMBOL = get input symbol
<br />if STRING + SYMBOL is in TABLE:
<br />STRING = STRING + SYMBOL
<br />else:
<br />output the code for STRING
<br />If TABLE.size < MAX_TABLE_SIZE: // if table is not full
<br />add STRING + SYMBOL to TABLE // STRING + SYMBOL now has a code
<br />STRING = SYMBOL
<br />output the code for STRING

Decoding Implementation

* The file name and bit length is taken from the command line arguments and maximum table size is calculated.
* To read the input strings in UTF-16BE format the BufferedReader along with InputStreamReader method is used.
* The HashMap data structure is employed to create a mapping of characters to their corresponding ASCII values. In this mapping, the keys are of integer type, representing ASCII value, while the values are of string type represent the ASCII character.
* Initialising the 'currentCode' with the first value of encoded data.
* Iterating over each character and checking if it contains in the table
  <br>if yes then the ASCII character from the table is assigned and stored in 'currentSymbol' and 'newSymbol' with respective previous  and next character.
  <br/>if coded value is not present in table then concatenating the current coded values pattern and the first character of the pattern
 *  The size of the hashmap is checked before adding a new key and value, if it is less than maximum table size,then only the new key value pair is added.
   The output is decoded to the _decoded.txt file by write() method and decoded string stored in 'output' variable is written to it.

Algorithm for Decoding <br />
<br />MAX_TABLE_SIZE=2(bit_length)
<br />initialize TABLE[0 to 255] = code for individual characters
<br />CODE = read next code from encoder
<br />STRING = TABLE[CODE]
<br />output STRING
<br />while there are still codes to receive:
<br />CODE = read next code from encoder
<br />if TABLE[CODE] is not defined: // needed because sometimes the
<br />NEW_STRING = STRING + STRING[0] // decoder may not yet have code!
<br />else:
<br />NEW_STRING = TABLE[CODE]
<br />output NEW_STRING
<br />if TABLE.size < MAX_TABLE_SIZE:
<br />add STRING + NEW_STRING[0] to TABLE
<br />STRING = NEW_STRING

Programming Language Used: JAVA
Version 21.0.1

Steps to run the programs
* The folder consists of two Java file i.e Encoder.java and Decoder.java
* The logic for Encoder and Decoder in present in Encoder.java and Decoder.java file
* Firstly both files need to be compiled
  <br /> Java command :
  <br />javac Encoder.java
  <br />javac Decoder.java

Next to run we need to give the input file name along with the bit length in command line arguments
<br /> example:  java Encoder "input1.txt" 12
            <br /> java Decoder.java "input1.lzw" 12



<br />Note: make sure while running the encoder the text file is present in same folder

Pros of LZW
* It works well when  the given input has repeated characters.
* It does not need the table to be transferred while sending the data.

Cons of LZW
If the given input does not contain the repetitive characters then there is no data compression 