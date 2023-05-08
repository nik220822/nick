package org.nikCode;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static Random random = new Random(); // Можно сделать один объект random, вместо двух ?
    static AtomicInteger niceThreeLettersWord = new AtomicInteger(0);
    static AtomicInteger niceFourLettersWord = new AtomicInteger(0);
    static AtomicInteger niceFiveLettersWord = new AtomicInteger(0);
    static int numberOfNicknames = 100_000;
    static int minNicknameLength = 3;
    static int numberOfTheAdditionalLettersForNicknamePlusOne = 3;
    static String lettersForTheTextGenerator = "abc";
    static String[] texts = new String[numberOfNicknames];

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText(lettersForTheTextGenerator, minNicknameLength + random.nextInt(numberOfTheAdditionalLettersForNicknamePlusOne));
            System.out.println(texts[i]);
        }
        Thread isPalindrome = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i]) & !areAllTheLettersTheSame(texts[i])) {
                    niceWordCounterPlusPlus(texts[i]);
                    System.out.println("палиндром");
                }
            }
        });
        isPalindrome.start();
        Thread areAllTheLettersTheSame = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (areAllTheLettersTheSame(texts[i])) {
                    niceWordCounterPlusPlus(texts[i]);
                    System.out.println("все буквы одинаковые");
                }
            }
        });
        areAllTheLettersTheSame.start();
        Thread AreAllTheLettersInTheRightOrder = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (AreAllTheLettersInTheRightOrder(texts[i]) & !areAllTheLettersTheSame(texts[i])) {
                    niceWordCounterPlusPlus(texts[i]);
                    System.out.println("порядок букв правильный");
                }
            }
        });
        AreAllTheLettersInTheRightOrder.start();
        areAllTheLettersTheSame.join();
        AreAllTheLettersInTheRightOrder.join();
        isPalindrome.join();
        System.out.println("Количество красивых слов из трёх букв: " + niceThreeLettersWord);
        System.out.println("Количество красивых слов из четырёх букв: " + niceFourLettersWord);
        System.out.println("Количество красивых слов из пяти букв: " + niceFiveLettersWord);
    }

    static void niceWordCounterPlusPlus(String string) {
        int l = string.length();
        switch (string.length()) {
            case (5):
                niceFiveLettersWord.getAndIncrement();
                break;
            case (4):
                niceFourLettersWord.getAndIncrement();
                break;
            case (3):
                niceThreeLettersWord.getAndIncrement();
                break;
            default:
        }
    }

    static boolean isPalindrome(String nickName) {
        if (nickName.equals(new StringBuilder(nickName).reverse().toString())) {
            return true;
        }
        return false;
    }

    static boolean areAllTheLettersTheSame(String nickName) {
        for (int i = 1; i < nickName.length(); i++) {
            if (nickName.charAt(i) != nickName.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    static boolean AreAllTheLettersInTheRightOrder(String nickName) {
        for (int i = 1; i < nickName.length(); i++) {
            if (nickName.charAt(i) < nickName.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static String generateText(String letters, int length) {
//        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}