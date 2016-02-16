package com.company;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListOfMessages {
    protected List<Message> arraylist;
    public ObjectMapper mapper;
    public BufferedReader inp;

    public ListOfMessages() {
        arraylist = new ArrayList<>();
        mapper = new ObjectMapper();
        inp = new BufferedReader(new InputStreamReader(System.in));
    }

    public void readFromFile() {
        byte[] jsonData = new byte[0];
        try {
            jsonData = Files.readAllBytes(Paths.get("chat.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            arraylist = mapper.readValue(jsonData, new TypeReference<ArrayList<Message>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage() {
        System.out.println("Input Username: ");
        String user = null;
        try {
            user = inp.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Input Message: ");
        String userMessage = null;
        try {
            userMessage = inp.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message(UUID.randomUUID().toString(), user, Date.from(Instant.now()).getTime(), userMessage);
        arraylist.add(message);
        try {
            mapper.writeValue(new File("chat.json"), arraylist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Message was sent");
    }

    public void showHistory() {
        Iterator it = arraylist.iterator();
        System.out.println("History:");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void deleteById() {
        System.out.println("Input ID:");
        String id = null;
        int k = 0;
        try {
            id = inp.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arraylist.size(); i++) {
            assert id != null;
            if (id.equals(arraylist.get(i).getId())) {
                arraylist.remove(i);
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
            e.printStackTrace();
        }
        int count = 0;
        for (Message anArraylist : arraylist) {
            assert name != null;
            if (name.equals(anArraylist.getAuthor())) {
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
            e.printStackTrace();
        }
        for (Message anArraylist : arraylist) {
            assert word != null;
            if (anArraylist.getMessage().contains(word)) {
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
            e.printStackTrace();
        }

        assert str != null;
        Pattern p = Pattern.compile(str);
        for (Message anArraylist : arraylist) {
            Matcher m;
            m = p.matcher(anArraylist.getMessage());
            if (m.matches()) {
                System.out.println(anArraylist.getMessage());
            }
        }
    }

    public void searchByDate() {
        String firstAdvice = "Input the date of the beginning at the format: dd.MM.yyyy HH:mm:ss";
        Long beginDate = dateToLong(firstAdvice);
        String secondAdvice = "Input the date of the ending at the format: dd.MM.yyyy HH:mm:ss";
        Long endDate = dateToLong(secondAdvice);
        for (Message anArraylist : arraylist) {
            if ((anArraylist.getTimestamp() >= beginDate) && (anArraylist.getTimestamp() <= endDate)) {
                System.out.println(anArraylist.getMessage());
            }
        }
    }

    public static Long dateToLong(String output) {
        Long formatDate = null;
        System.out.println(output);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String normalDate = reader.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            try {
                Date date = formatter.parse(normalDate);
                formatDate = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatDate;
    }
}


