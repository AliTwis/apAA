package view;

public enum Output {
    USERNAME("username", "یوزرنیم"),
    PASSWORD("password", "پسوورد"),
    LOGIN("login", "لاگین"),
    REGISTER("register", "ساخت"),
    GUEST("guest", "مهمان"),
    NEW_GAME("New Game", "بازی جدید"),
    LOAD_GAME("Continue previous game", "ادامه بازی فبلی"),
    SCOREBOARD("Scoreboard", "جدول امتیازات"),
    SETTING("Setting", "تنظیمات"),
    EXIT("Exit", "خروج"),
    PICK_LEVEL("pick your level:", ":لول را انتخاب کن"),
    BALLS_AMOUNT("how many balls?", "؟چند توپ"),
    FIRST_PLAYER_SHOOT("first player shoot", "شوت نفر اول"),
    SECOND_PLAYER_SHOOT("second player shoot", "شوت نفر دوم"),
    FREEZE("freeze", "یخ زدن"),
    MONSTER("monster:", ":هیولا"),
    MAP("map:", ":نقشه"),
    MUTE("mute", "بی صدا"),
    BACK("back", "بازگشت"),
    CHANGE_USERNAME("change username", "تغییر نام کاربری"),
    CHANGE_PASSWORD("change password", "تغییر پسوورد"),
    CHANGE_AVATAR("change avatar", "تغییر آواتار"),
    LOGOUT("logout", "خروج از حساب کاربری"),
    DELETE_ACCOUNT("delete account", "حذف حساب کاربری"),
    ;
    private final String english;
    private final String persian;
    private static boolean isEnglish = true;

    Output(String english, String persian) {
        this.english = english;
        this.persian = persian;
    }

    public static void setIsEnglish(boolean isEnglish) {
        Output.isEnglish = isEnglish;
    }

    public static boolean isIsEnglish() {
        return isEnglish;
    }

    public String getOutput() {
        if (isEnglish) return english;
        return persian;
    }
}
