package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkWithUser {
    private String number;

    public WorkWithUser() {

        number = null;

    }

    public void work() {
        ListOfMessages messages = new ListOfMessages();
        messages.readFromFile();
        boolean flag = false;
        while (!flag) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            interaction();
            try {
                number = input.readLine();
                switch (number) {
                    case "1":
                        messages.addMessage();
                        break;
                    case "2":
                        messages.showHistory();
                        break;
                    case "3":
                        messages.deleteById();
                        break;
                    case "4":
                        messages.searchByAuthor();
                        break;
                    case "5":
                        messages.searchByWord();
                        break;
                    case "6":
                        messages.searchByRegex();
                        break;
                    case "7":
                        messages.searchByDate();
                        break;
                    case "8":
                        flag = true;
                        break;
                    default:
                        System.out.println("Try again");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void interaction() {
        System.out.println("1.Send message");
        System.out.println("2.Show history of messages");
        System.out.println("3.Delete the message by ID");
        System.out.println("4.Search by author");
        System.out.println("5.Search by word");
        System.out.println("6.Search by regex");
        System.out.println("7.Search by a certain period");
        System.out.println("8.Finish work");
        System.out.println("Choose number");
    }
}
