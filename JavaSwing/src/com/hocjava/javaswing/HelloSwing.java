package com.hocjava.javaswing;

import javax.swing.*;

public class HelloSwing {
    public HelloSwing() {
        JFrame frame = new JFrame("Hello Swing");
        frame.setSize(300, 300); // set kich thuoc
        frame.setVisible(true); // set có thể thấy với người dùng
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // dong cua so

    }

    public static void main(String[] args) {
        new HelloSwing();
    }
}
