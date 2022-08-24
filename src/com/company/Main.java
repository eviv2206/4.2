package com.company;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static boolean isFileOrConsole(Scanner input){
        boolean fileOrConsole;
        boolean isInCorrect;
        int temp = 0;
        System.out.println("1. Ввести данные вручную.");
        System.out.println("2. Ввести данные с файла.");
        do {
            try {
                isInCorrect = false;
                temp = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Введены неккоректные данные.");
                isInCorrect = true;
            }
            if (!(temp == 1 || temp == 2) && !isInCorrect){
                System.err.println("Введены неккоректные данные.");
                isInCorrect = true;
            }
        } while (isInCorrect);
        fileOrConsole = temp == 1;
        return fileOrConsole;
    }

    public static boolean isSaveToFile(Scanner input){
        System.out.println("Сохранить результат в файл?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        int temp = 0;
        boolean isInCorrect;
        do{
            isInCorrect = false;
            try {
                temp = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e){
                System.out.print("Введите данные корректно: ");
                isInCorrect = true;
            }
            if (!(temp == 1 || temp == 2) && !isInCorrect){
                System.out.print("Введите данные корректно: ");
            }
        } while (isInCorrect);
        return temp == 1;
    }

    public static String inputDataFromConsole(Scanner input){
        System.out.print("Введите строку: ");
        return input.nextLine();

    }

    public static String findFilePath(Scanner input){
        String filePath;
        FileReader reader = null;
        boolean isIncorrect;
        do {
            System.out.print("Введите путь к файлу: ");
            filePath = input.nextLine();
            isIncorrect = false;
            try{
                reader = new FileReader(filePath);
            } catch (FileNotFoundException e){
                System.out.println("Файл не найден.");
                isIncorrect = true;
            }
        } while (isIncorrect);
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("I/O error.");
        }
        return filePath;
    }


    public static String inputDataFromFile(Scanner input, String filePath) {
        String inputData = null;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            inputData = reader.readLine();
            reader.close();
        } catch (IOException e){
            System.err.println("Input error.");
        }
        if (Objects.isNull(inputData)){
            inputData = inputDataFromConsole(input);
        }
        return inputData;
    }

    public static String toDelete(String str, int numOfSymbols, String deletedStr){
        StringBuffer sb = new StringBuffer(str);
        int position = str.lastIndexOf(deletedStr);
        if (position != -1){
            sb.delete(position, position + numOfSymbols);
            str = String.valueOf(sb);
        }
        return str;
    }

    public static String deleteSymbols(String str){
        String temp = str;
        temp = toDelete(temp, 4, "AAAA");
        temp = toDelete(temp, 2, "BABA");
        temp = toDelete(temp,3, "ABC");
        if (!(str.equals(temp))){
            str = temp;
            temp = deleteSymbols(str);
        }
        if (temp.length() == 0){
            temp = "Пустая строка";
        }
        return temp.trim();
    }

    public static void showInfo(){
        System.out.println("""
                Эта программа преобразует строку, заданную латиницей, по правилам:\s
                а) удаляет четыре подряд идущих букв А;
                б) удаляет из последовательности ВАВА одну пару ВА;
                в) удаляет комбинацию АВС.
                """);
    }

    public static void showResult(String data){
        System.out.println("Полученная строка: " + data);
    }

    public static void writeToFile(String filePath, String data){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(data);
            writer.close();
        } catch (IOException e){
            System.err.println("Output error.");
        }
        System.out.println("Успеешно сохранено.");
    }

    public static void showInputData(String data){
        System.out.println("Введенная строка: " + data);
    }

    public static String findInputData(Scanner input, boolean fileOrConsole){
        if (fileOrConsole){
            return inputDataFromConsole(input);
        } else {
            return inputDataFromFile(input, findFilePath(input));
        }
    }

    public static void main(String[] args) {
        showInfo();
        Scanner input = new Scanner(System.in);
        boolean fileOrConsole = isFileOrConsole(input);
        String data = findInputData(input, fileOrConsole);
        showInputData(data);
        data = deleteSymbols(data);
        showResult(data);
        if (isSaveToFile(input)) {
            writeToFile(findFilePath(input), data);
        }
        input.close();
    }
}
