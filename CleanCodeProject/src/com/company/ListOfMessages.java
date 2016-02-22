package com.company;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListOfMessages {
    protected List<Message> listOfMessages;
    public ObjectMapper mapper;
    public static BufferedReader inp;

    public ListOfMessages() {
        listOfMessages = new ArrayList<>();
        mapper = new ObjectMapper();
        inp = new BufferedReader(new InputStreamReader(System.in));
    }

    public void readFromFile() {
        byte[] jsonData = new byte[0];
        try {
            jsonData = Files.readAllBytes(Paths.get("chat.json"));
        } catch (IOException e) {
            Logger.write("IOException");
            e.printStackTrace();
        }
        try {
            listOfMessages = mapper.readValue(jsonData, new TypeReference<ArrayList<Message>>() {
            });
        } catch (IOException e) {
            Logger.write("IOException (can not read file)");
        }
    }

    public void addMessage() {
        System.out.println("Input Username: ");
        String user = null;
        try {
            user = inp.readLine();
        } catch (IOException e) {
            Logger.write("IOException (wrong input of name) ");
        }
        System.out.println("Input Message: ");
        String userMessage = null;
        try {
            userMessage = inp.readLine();
        } catch (IOException e) {
            Logger.write("IOException (wrong input of message) ");
        }
        Message message = new Message(UUID.randomUUID().toString(), user, Date.from(Instant.now()).getTime(), userMessage);
        listOfMessages.add(message);
        Logger.write("New message  added: " + message.getMessage());
        try {
            mapper.writeValue(new File("chat.json"), listOfMessages);
        } catch (IOException e) {
            Logger.write("IOException (can not write to file)");
        }
        System.out.println("Message was sent");
    }

    public void showHistory() {
        Iterator it = listOfMessages.iterator();
        System.out.println("History:");
        while (it.hasNext()) {
            Message mess = (Message) it.next();
            DateFormat format = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]");
            Instant instant = Instant.ofEpochMilli(mess.getTimestamp());
            Date date = Date.from(instant);
            System.out.println(format.format(date) + " " + mess.getAuthor() + " " + mess.getMessage());
        }
    }

    public void deleteById() {
        System.out.println("Input ID:");
        String id = null;
        int k = 0;
        try {
            id = inp.readLine();
        } catch (IOException e) {
            Logger.write("IOException (wrong input of ID)");
        }
        for (int i = 0; i < listOfMessages.size(); i++) {
            assert id != null;
            if (id.equals(listOfMessages.get(i).getId())) {
                listOfMessages.remove(i);
                Logger.write("Message was deleted: " + listOfMessages.get(i).getMessage());
                System.out.println("Deleting is successful");
                k++;
            }
        }
        if (k == 0)
            System.out.println("ID was not found");
    }

    public void searchByAuthor() {
        System.out.println("Input name:");
        String name = null;
        try {
            name = inp.readLine();
        } catch (IOException e) {
            Logger.write("IOException (wrong input of name) ");
        }
        int count = 0;
        for (Message anArraylist : listOfMessages) {
            assert name != null;
            if (name.equals(anArraylist.getAuthor())) {
                Logger.write("Message was found by the author: " + anArraylist.getMessage());
                System.out.println(anArraylist.getMessage());
                count++;
            }
        }
        if (count == 0)
            System.out.println("No messages by this author");
    }

    public void searchByWord() {
        System.out.println("Input word");
        String word = null;
        int count = 0;
        try {
            word = inp.readLine();
        } catch (IOException e) {
            Logger.write("IOException (wrong input of word) ");
        }
        for (Message anArraylist : listOfMessages) {
            assert word != null;
            if (anArraylist.getMessage().contains(word)) {
                Logger.write("Message was found by the word: " + anArraylist.getMessage());
                System.out.println(anArraylist.getMessage());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No matches");
        }
    }

    public void searchByRegex() {
        System.out.println("Input regex");
        String str = null;
        try {
            str = inp.readLine();
        } catch (IOException e) {
            Logger.write("IOException (wrong input of regex) ");
        }

        assert str != null;
        Pattern p = Pattern.compile(str);
        for (Message anArraylist : listOfMessages) {
            Matcher m;
            m = p.matcher(anArraylist.getMessage());
            if (m.matches()) {
                Logger.write("Message was found by the regex: " + anArraylist.getMessage());
                System.out.println(anArraylist.getMessage());
            }
        }
    }

    public void searchByDate() {
        String firstAdvice = "Input the date of the beginning at the format: dd.MM.yyyy HH:mm:ss";
        Long beginDate = dateToLong(firstAdvice);
        String secondAdvice = "Input the date of the ending at the format: dd.MM.yyyy HH:mm:ss";
        Long endDate = dateToLong(secondAdvice);
        if (beginDate == -1 && endDate == -1) {
            System.out.println("Incorrect");
        } else {
            for (Message anArraylist : listOfMessages) {
                if ((anArraylist.getTimestamp() >= beginDate) && (anArraylist.getTimestamp() <= endDate)) {
                    System.out.println(anArraylist.getMessage());
                }
            }
        }
    }

    public static Long dateToLong(String output) {
        Long formatDate = null;
        System.out.println(output);
        try {
            String normalDate = inp.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            try {
                Date date = formatter.parse(normalDate);
                formatDate = date.getTime();
            } catch (ParseException e) {
                Logger.write("ParseException (wrong input) ");
                System.out.println("Wrong input of date");
            }
        } catch (IOException e) {
            Logger.write("ParseException (wrong input of date)");
            System.out.println("Wrong input");
        }
        if (formatDate == null) {
            return (long) -1;
        } else return formatDate;
    }
}
