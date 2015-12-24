package org.dnu.samoylov.service;

import org.dnu.samoylov.utils.ViginereHelper;

import static org.dnu.samoylov.utils.ViginereHelper.ALPHABET;

public class DecryptionService {
    public static DecryptionService newInstance() {
        return new DecryptionService();
    }

    public String decryptionText(String text, String key) {
        char[][] vigenereTable = ViginereHelper.getVigenereTable();
        StringBuilder textMask = createTextMask(key, text);

        StringBuilder decryptionText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            decryptionText.append(decryptionSymbol(textMask.toString(), vigenereTable, text, i));
        }
        System.out.println("Строка дешифрована!");

        return decryptionText.toString();
    }

    private StringBuilder createTextMask(String key, String text) {
        //Формирование строки, длиной шифруемой, состоящей из повторений ключа
        StringBuilder codeMask = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char symbol = key.charAt(i % key.length());
            codeMask.append(symbol);
        }
        return codeMask;
    }

    private char decryptionSymbol(String codeMask, char[][] vigenereTable, String text, int i) {

        int indexRowForCurrentSymbol = 0;
        int indexColForCurrentSymbol = 0;

        for (int k = 0; k <ALPHABET.length() ; k++) {
            if (codeMask.charAt(i) == vigenereTable[k][0]) {
                indexRowForCurrentSymbol = k;
                break;
            }
        }

        char duplicate = text.charAt(i);
        for (int k = 0; k <ALPHABET.length() ; k++) {
            if (duplicate == vigenereTable[indexRowForCurrentSymbol][k]) {
                indexColForCurrentSymbol = k;
                break;
            }
        }

        return vigenereTable[0][indexColForCurrentSymbol];
    }


}
