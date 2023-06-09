package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class User implements Comparable<User> {
    private static ArrayList<User> users = new ArrayList<>();
    private static final String dataPath = "src/main/resources/users/users.json";
    private static final String backupData = "src/main/resources/users/backup.json";
    private String username;
    private String password;
    private String avatarAddress;
    private String savedGame = null;
    private int score = 0;
    private int[] lastUpdate = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        users.add(this);
        this.updateTime();
    }

    public static User getUserByName(String name) {
        for (User user : users) {
            if (user.username.equals(name)) return user;
        }
        return null;
    }

    public String getSavedGame() {
        return savedGame;
    }

    public void setSavedGame(String savedGame) {
        this.savedGame = savedGame;
    }

    public void updateTime() {
        LocalDate localDate = java.time.LocalDate.now();
        this.getLastUpdate()[0] = localDate.getYear();
        this.getLastUpdate()[1] = localDate.getMonthValue();
        this.getLastUpdate()[2] = localDate.getDayOfMonth();
        ZonedDateTime localTime = java.time.ZonedDateTime.now();
        int minute = localTime.getMinute();
        int hour = localTime.getHour();
        this.getLastUpdate()[3] = hour;
        this.getLastUpdate()[4] = minute;
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

        Type usersType = new TypeToken<ArrayList<User>>() {
        }.getType();

        ArrayList<User> tempUsers = gson.fromJson(save, usersType);
        if (tempUsers != null) users = tempUsers;
        else users = new ArrayList<>();
    }

    public int getScore() {
        return score;
    }

    public int[] getLastUpdate() {
        return lastUpdate;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public void setLastUpdate(int[] lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public String getAvatarAddress() {
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
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
        for (int i = 0; i < 5; i++) {
            if (o.lastUpdate[i] != lastUpdate[i]) {
                return lastUpdate[i] - o.lastUpdate[i];
            }
        }
        return this.getUsername().compareTo(o.getUsername());
    }
}
