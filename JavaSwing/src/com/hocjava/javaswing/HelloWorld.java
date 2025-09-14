package com.hocjava.javaswing;

import javax.swing.*;
import java.awt.*;

public class HelloWorld extends JFrame {
    public HelloWorld() throws HeadlessException {
        setSize(300, 300);

    }

    public static void main(String[] args) {
        new HelloWorld().setVisible(true);
    }
}
