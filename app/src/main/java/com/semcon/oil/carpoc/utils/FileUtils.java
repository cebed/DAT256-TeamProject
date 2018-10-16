package com.semcon.oil.carpoc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public final class FileUtils {

    // checks base directory for the score file, creates one if it doesn't exist
    // returns the int value that is written in this file
    public static int loadScore(File directory) {
        File scoreFile;
        try {
            String fileName = "/scoreFile.txt";
            //scoreFile = new File(getFilesDir(), fileName);
            scoreFile = new File(directory, fileName);
            FileReader read = new FileReader(scoreFile);
            BufferedReader buffRead = new BufferedReader(read);
            String line = buffRead.readLine();

            read.close();
            buffRead.close();

            line.trim();
            return Integer.parseInt(line);
        }
        catch(Exception ex) {
            System.out.println("Error in MainActivity.loadScore():" + ex.getLocalizedMessage());
            createFile(directory);
            return 0;
        }
    }

    // writes the total score to the scoreFile (should be invoked upon closing)
    public static void saveScore(File directory, int score) {
        //int score = count + totalScore;
        File scoreFile;
        try {
            String fileName = "/scoreFile.txt";
            //scoreFile = new File(getFilesDir(), fileName);
            scoreFile = new File(directory, fileName);
            String scoreString = Integer.toString(score);

            FileWriter fw = new FileWriter(scoreFile);
            BufferedWriter out = new BufferedWriter(fw);

            out.write(scoreString);

            out.flush();
            out.close();
        }
        catch(Exception ex) {
            System.out.println("Error in MainActivity.saveScore():" + ex.getLocalizedMessage());
            return;
        }
    }

    // creates a file
    public static void createFile(File directory) {
        try {
            String fileName = "/scoreFile.txt";
            //File newFile = new File(getFilesDir(), fileName);
            File newFile = new File(directory, fileName);

            //System.out.println(getFilesDir().getAbsolutePath()); // printar path till filen
            System.out.println(directory.getAbsolutePath()); // printar path till filen

            if (newFile.createNewFile()) {
                System.out.println("createFile(): file created");
            }
            else {
                System.out.println("createFile(): file already exists");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("createFile(): error:" + e.getLocalizedMessage());
            e.printStackTrace();
            return;
        }
    }

}
