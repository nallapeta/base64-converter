# Base64 Converter (Java)

A simple Java application that encodes and decodes files using the **Base64 algorithm**.

This project manually implements Base64 encoding and decoding using **bitwise operations**, without relying on Java’s built-in Base64 utilities. It is designed as a learning exercise to understand how Base64 works internally.

---

## 🚀 Features

- Encode any file into Base64 format (`.b64`)
- Decode Base64 files back to their original binary form
- Manual Base64 implementation (no `java.util.Base64`)
- FileInputStream / FileOutputStream usage
- Lightweight and dependency-free

---

## 🎯 Project Purpose

This project was created to practice:

- Bitwise manipulation (shifting & masking)
- Understanding Base64 encoding/decoding logic
- Java file I/O streams
- CLI-style interaction

---

## ⚙️ How It Works

The program reads a filename from standard input:

- If the filename **ends with `.b64`** → the file is **decoded**
- Otherwise → the file is **encoded** into Base64

---

## 🛠️ Compilation & Execution

### Compile

javac Base64Conv.java

### Run

java Base64Conv

When prompted, enter the filename.

Example:

example.png

Output:

example.png.b64

---

## 🧠 Implementation Details

### Encoding

- Reads file in 3-byte chunks (24 bits)
- Splits into 4 groups of 6 bits
- Maps values to Base64 character table
- Adds padding (`=`) when necessary

### Decoding

- Reads Base64 characters in 4-byte chunks
- Converts Base64 characters back to 6-bit values
- Reconstructs original 8-bit bytes
- Handles padding

---

## 📚 Base64 Character Set

```
ABCDEFGHIJKLMNOPQRSTUVWXYZ
abcdefghijklmnopqrstuvwxyz
0123456789
+ /
```

---

## ⚠️ Limitations

This is an educational implementation:

- Minimal validation of malformed Base64 input
- Whitespace/newline handling may be limited
- Not optimized for performance

For production use, prefer:

java.util.Base64

---

## ✅ Why Manual Implementation?

To better understand:

✔ Binary-to-text encoding  
✔ Bit manipulation  
✔ Padding mechanics  
✔ Data transformation  

---

## 🧾 License

This project is open-source and available under the MIT License.
