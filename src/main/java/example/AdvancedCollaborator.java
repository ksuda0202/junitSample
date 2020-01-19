package main.java.example;

public class AdvancedCollaborator {
    int i;
    private int privateField = 5;

    public AdvancedCollaborator(String string) throws Exception {
        i = string.length();
    }

    public String methodThatCallsPrivateMethod(int i) {
        return privateMethod() + i;
    }
    public int methodThatReturnsThePrivateField() {
        return privateField;
    }
    private String privateMethod() {
        return "default:";
    }
}