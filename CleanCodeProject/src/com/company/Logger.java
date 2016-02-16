package com.company;

import java.io.*;

/**
 * Created by Карина on 16.02.2016.
 */
public class Logger {
    private static final String filename = "logfile.txt";
    private static BufferedWriter bw;

    static {
        try {
            bw = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String message) {
        try {
            bw.write(message);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
