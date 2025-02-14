package se.yitingchang.moo;

public interface TextIO {
    String getString();
    void addString(String s);
    boolean yesNo(String prompt);
    void clear();
    void exit();
}
