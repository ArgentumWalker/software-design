package ru.spbau.cli;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new CLI(System.in, System.out).run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
