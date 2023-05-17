package controller.Enums;

public enum SuccessfulResponses {
    REGISTER("Successfully registered."),
    LOGIN("Successfully logged in."),
    CHANGE_USERNAME("Your username successfully changed."),
    CHANGE_PASSWORD("Your password successfully changed."),
    ;
    private String output;
    SuccessfulResponses (String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
