package controller.Enums;

public enum SuccessfulResponses {
    REGISTER("Successfully registered."),
    LOGIN("Successfully logged in."),
    ;
    private String output;
    SuccessfulResponses (String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
