package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class User implements Comparable<User> {
    private static ArrayList<User> users = new ArrayList<>();
    private static String dataPath = "src/main/resources/users/users.json";
    private static String backupData = "src/main/resources/users/backup.json";
    private String username;
    private String password;
    private int score;
    private int[] lastUpdate = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        users.add(this);
    }

    public static User getUserByName(String name) {
        for (User user : users) {
            if (user.username.equals(name)) return user;
        }
        return null;
    }

    public static void updateUsers() {
        saveUsers();
        loadSavedData();
    }

    public static void saveUsers() {
        String data = makeUsersJson();
        File file = new File(dataPath);

        try {
            if (file.exists()) file.delete();
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data);
            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void loadSavedData() {
        File file = new File(dataPath);
        File backupFile = new File(backupData);

        try {
            Scanner reader = new Scanner(file);
            String data = "";
            while (reader.hasNextLine()) {
                data = data.concat(reader.nextLine());
            }

            makeJsonUsers(data);

            //backing up saved data
            backupFile.delete();
            backupFile.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(backupFile));
            bufferedWriter.write(data);
            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println("There was a problem...");
        }
    }

    public static String makeUsersJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(users);
    }

    public static void makeJsonUsers(String save) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        Type usersType = new TypeToken<ArrayList<User>>(){}.getType();

        users = gson.fromJson(save, usersType);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static int getRank(User user) {
        Collections.sort(users);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) return i + 1;
        }
        return -1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(User o) {
        if (o.score != score) return o.score - score;
        for (int i = 0; i < 4; i++) {
            if (o.lastUpdate[i] != lastUpdate[i]) {
                return o.lastUpdate[i] - lastUpdate[i];
            }
        }
        return o.username.compareTo(username);
    }
}
