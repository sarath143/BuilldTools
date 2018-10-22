package com.hp.buildtools.practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class ReadingFile {

    public static void main(String args[]){
      /*  try {
            Scanner scanner = new Scanner(new File("C:\\Users\\C52816A\\Desktop\\ftest.txt"));
            scanner.useDelimiter("\\.java");
            while(scanner.hasNext()){
                System.out.println(scanner.next().trim());
                System.out.println("*********************************************");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        System.out.println("fgf");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            Files.
//                    lines(Paths.get("C:\\Users\\C52816A\\Desktop\\ftest.txt"))
//        .peek(x->{
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        })
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
