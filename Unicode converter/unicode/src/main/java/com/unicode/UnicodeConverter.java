package com.unicode;

import java.util.Scanner;

public class UnicodeConverter {
    public static String toUnicode(String input) {
          StringBuilder unicodeString = new StringBuilder();
          for (char c : input.toCharArray()) {
              unicodeString.append(String.format("\\u%04x", (int) c));
          }
          return unicodeString.toString();
      }
  
  public static void main(String... args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Введите текст: ");
      String text = scanner.nextLine();
      String unicodeResult = toUnicode(text);
      System.out.println("Unicode: " + unicodeResult);
      scanner.close();
  }

}