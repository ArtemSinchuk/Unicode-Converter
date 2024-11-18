package com.ascii;

import java.util.Scanner;

public class AsciiConverter {
    public static String toAscii(String input) {
          StringBuilder asciiString = new StringBuilder();
          for (char c : input.toCharArray()) {
              asciiString.append(String.format("\\u%04x", (int) c));
          }
          return asciiString.toString();
      }
  
  public static void main(String... args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Введите текст: ");
      String text = scanner.nextLine();
      String asciiResult = toAscii(text);
      System.out.println("ASCII-код: " + asciiResult);
      scanner.close();
  }

}