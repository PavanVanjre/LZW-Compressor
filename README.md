
# Programming Project 1: LZW Compression

## Overview
The **Lempel–Ziv–Welch (LZW)** algorithm is a lossless data compression algorithm. LZW is an adaptive compression algorithm that does not assume prior knowledge of the input data distribution. This algorithm works well when the input data is sufficiently large and there is redundancy in the data.

### Examples of Use
- **GIF** image format (web-based)
- **TIFF** image format
- **Unix compress utility** for text file compression

### Key Steps
1. **Encoding/Compressing**
2. **Decoding/Decompressing**

The encoding table, or dictionary, computed during the encoding process does not need to be explicitly transmitted. It can be regenerated from the coded/compressed data.

---

## Encoding Implementation

- **Input**: The file name and bit length are provided as command-line arguments to calculate the maximum table size.
- **Input Reader**: Java's `BufferedReader` is used to read input strings.
- **Dictionary**: A `HashMap` stores:
  - **Keys**: Strings (characters or patterns)
  - **Values**: Integers (ASCII values).
- **Processing**:
  - For each character:
    - If found in the table: Add it to `currentSymbol` and continue.
    - If not found:
      - Add the previous character/pattern to the output array.
      - Update the encoding table with the new character/pattern.
  - Ensure the table size does not exceed the maximum.

- **Output**: Encoded data is written to a `.lzw` file.

### Algorithm for Encoding
```plaintext
MAX_TABLE_SIZE = 2^bit_length   // bit_length is the number of encoding bits
initialize TABLE[0 to 255] = code for individual characters
STRING = null
while there are still input symbols:
    SYMBOL = get input symbol
    if STRING + SYMBOL is in TABLE:
        STRING = STRING + SYMBOL
    else:
        output the code for STRING
        if TABLE.size < MAX_TABLE_SIZE:  // if table is not full
            add STRING + SYMBOL to TABLE  // STRING + SYMBOL now has a code
        STRING = SYMBOL
output the code for STRING
```

---

## Decoding Implementation

- **Input**: The file name and bit length are provided as command-line arguments to calculate the maximum table size.
- **Input Reader**: Input strings are read in **UTF-16BE** format using Java's `BufferedReader` and `InputStreamReader`.
- **Dictionary**: A `HashMap` stores:
  - **Keys**: Integers (ASCII values)
  - **Values**: Strings (patterns).
- **Processing**:
  - Start with the first code in the encoded data.
  - For each subsequent code:
    - If found in the table: Retrieve the corresponding string.
    - If not found: Concatenate the current pattern and its first character.
    - Update the dictionary with the new pattern.
  - Ensure the table size does not exceed the maximum.

- **Output**: Decoded data is written to a `_decoded.txt` file.

### Algorithm for Decoding
```plaintext
MAX_TABLE_SIZE = 2^bit_length
initialize TABLE[0 to 255] = code for individual characters
CODE = read next code from encoder
STRING = TABLE[CODE]
output STRING
while there are still codes to receive:
    CODE = read next code from encoder
    if TABLE[CODE] is not defined:  // sometimes the decoder may not yet have code!
        NEW_STRING = STRING + STRING[0]
    else:
        NEW_STRING = TABLE[CODE]
    output NEW_STRING
    if TABLE.size < MAX_TABLE_SIZE:
        add STRING + NEW_STRING[0] to TABLE
    STRING = NEW_STRING
```

---

## Programming Language
- **Java**
- **Version**: 21.0.1

---

## Steps to Run the Program

1. **Files Included**:
   - `Encoder.java` (contains the encoding logic)
   - `Decoder.java` (contains the decoding logic)

2. **Compilation**:
   ```bash
   javac Encoder.java
   javac Decoder.java
   ```

3. **Execution**:
   - Run the Encoder:
     ```bash
     java Encoder "input1.txt" 12
     ```
   - Run the Decoder:
     ```bash
     java Decoder "input1.lzw" 12
     ```

4. **Outputs**:
   - Encoded data: `input1.lzw`
   - Decoded data: `input1_decoded.txt`

**Note**: Ensure the input text file is in the same folder as the program files.

---

## Pros of LZW
- Effective when the input contains repeated characters.
- Does not require transmitting the dictionary for decoding.

## Cons of LZW
- Ineffective for inputs with no repeated characters, leading to no compression.
