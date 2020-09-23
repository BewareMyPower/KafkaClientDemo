package io.github.bewaremypower;

import io.github.bewaremypower.wrapper.ConfiguredProducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleProducer extends ConfiguredProducer {
    public void run() {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String line = stdin.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("Send line: " + line);
                // TODO: parse key from line
                send(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read: " + e.getMessage());
        }
        flush();
        System.out.println("Producer flushed");
    }
}
