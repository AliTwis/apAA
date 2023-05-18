package model;

import javafx.animation.Transition;

import java.util.ArrayList;

public abstract class GameTransitions extends Transition{
    private static ArrayList<Transition> transitions = new ArrayList<>();

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public static ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public static void stopTransitions() {
        for (Transition transition : transitions) {
            transition.stop();
        }
    }

    public static void startTransitions() {
        for (Transition transition : transitions) {
            transition.play();
        }
    }
}
