package org.dnu.samoylov.utils;

import java.util.Map;

public class ViginereHelper {
    public static String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя_";

    public static char[][] getVigenereTable() {
        int shift;
        char[][] tabula_recta = new char[ALPHABET.length()][ALPHABET.length()]; //Таблица Виженера

        //Формирование таблицы
        for (int i = 0; i < 33; i++)
            for (int j = 0; j < 33; j++) {
                shift = j + i;
                if (shift >= 33) shift = shift % 33;
                tabula_recta[i][j] = ALPHABET.charAt(shift);
            }
        return tabula_recta;
    }

    public static int nod(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return nod(b, a % b);
        }
    }


    public static<T> void increaseCounter(Map<T, Integer> map, T symbol) {
        int previousCount = 0;
        try{
            previousCount = map.get(symbol);
        } catch (Exception ignored) { }
        map.put(symbol, previousCount + 1);
    }
}
