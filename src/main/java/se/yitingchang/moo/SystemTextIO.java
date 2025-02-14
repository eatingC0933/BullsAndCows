package se.yitingchang.moo;

import java.util.Scanner;

public class SystemTextIO implements TextIO {
    private final Scanner scanner;

    public SystemTextIO() {
        scanner = new Scanner(System.in);
    }


    @Override
    public String getString() {
        return scanner.nextLine();
    }

    @Override
    public void addString(String s) {
        System.out.println(s);;
    }

    @Override
    public boolean yesNo(String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    @Override
    public void clear() {
        System.out.println("Sudda sudda");
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
