package com.looptracker.looptracker.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    public static String removeAccent(String s) {
        String normalizedString = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
    }
}
