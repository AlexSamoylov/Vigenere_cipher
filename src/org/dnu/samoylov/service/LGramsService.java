package org.dnu.samoylov.service;

import org.dnu.samoylov.utils.ViginereHelper;

import java.io.IOException;
import java.util.*;

import static org.dnu.samoylov.utils.ViginereHelper.*;

public class LGramsService {
    public static LGramsService newInstance() {
        return new LGramsService();
    }

    public char[][] searchLGrams(String text, int keyLength, int[]shifts) throws IOException {
        Map<Character, int[]> frequencyTable = createFrequencyTable(text, keyLength);

        int[] sumFrequencyByColl = calculateSumFrequencyByColumn(keyLength, frequencyTable);

        char[][] lGrams = new char[ALPHABET.length()][keyLength];
        for (int j = 0; j < keyLength; j++) {
            for (int i = 0; i < ALPHABET.length(); i++) {
                lGrams[i][j] = ALPHABET.charAt(i);
            }
        }


        for (int i = 1; i < keyLength; i++) {
            double MI = calculateMi(frequencyTable, sumFrequencyByColl, lGrams, i);

            int shift = 0;
            while (!((MI >= 0.053) && (MI <= 0.07))) {
                shiftBackward(lGrams, i);
                MI = calculateMi(frequencyTable, sumFrequencyByColl, lGrams, i);

                shift++;
            }
            shifts[i] = shift;
        }

        return lGrams;
    }

    private double calculateMi(Map<Character, int[]> frequencyTable, int[] sumFrequencyByColl, char[][] lGrams, int i) {
        double MI = 0;
        for (int j = 0; j < ALPHABET.length(); j++) {
             for (int l = 0; l < ALPHABET.length(); l++) {
                 if (lGrams[j][i] == ALPHABET.charAt(l)) {
                    MI += frequencyTable.get(ALPHABET.charAt(j))[0] * frequencyTable.get(ALPHABET.charAt(l))[i];
                }
            }
        }
        MI = MI / (sumFrequencyByColl[0] * sumFrequencyByColl[i]);
        return MI;
    }

    private void shiftBackward(char[][] abc_matrix, int i) {
        char[] column = getColumn(abc_matrix, i);

        for (int j = 0; j < ALPHABET.length(); j++) {
            if (j == 0) {
                abc_matrix[ALPHABET.length() - 1][i] = column[j];
            } else {
                abc_matrix[j - 1][i] = column[j];
            }
        }
    }

    private char[] getColumn(char[][] abc_matrix, int i) {
        char[] column = new char[ALPHABET.length()];
        for (int j = 0; j < ALPHABET.length(); j++) {
            column[j] = abc_matrix[j][i];
        }
        return column;
    }

    private int[] calculateSumFrequencyByColumn(int keyLength, Map<Character, int[]> frequencyTable) {
        int[] sumFrequencyByColl = new int[keyLength];

        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < ALPHABET.length(); j++) {
                sumFrequencyByColl[i] += frequencyTable.get(ALPHABET.charAt(j))[i];
            }
        }
        return sumFrequencyByColl;
    }

    private Map<Character, int[]> createFrequencyTable(String text, int keyLength) {
        Map<Character, int[]> frequencyTable = new HashMap<>(); //new int[ALPHABET.length()][keyLength + 1];
        for (int i = 0; i < ALPHABET.length(); i++) {
            frequencyTable.put(ALPHABET.charAt(i), new int[keyLength]);
        }

        char[][]dividedText = getDividedPerLength(text, keyLength);


        for (int i = 0; i < keyLength; i++) {
            Map<Character, Integer> frequencySymbolForCurrentCharPos = new HashMap<>();

            for (int j = 0; j < (text.length() / keyLength) + 1; j++) {
                final char symbol = dividedText[j][i];
                if (ALPHABET.indexOf(symbol)<0) {
                    break;
                }

                ViginereHelper.increaseCounter(frequencySymbolForCurrentCharPos, symbol);
            }

            for (char symbol : frequencySymbolForCurrentCharPos.keySet()) {
                    frequencyTable.get(symbol)[i]  = frequencySymbolForCurrentCharPos.get(symbol);
            }
        }
        
        return frequencyTable;
    }

    private char[][] getDividedPerLength(String text, int keyLength) {
        char[][] dividedText = new char[text.length()/keyLength+1][keyLength];
        
        for (int charIndexInText = 0; charIndexInText < text.length(); charIndexInText++) {
            int currentWordIndex = charIndexInText / keyLength;
            int positionInWord = charIndexInText % keyLength;

            dividedText[currentWordIndex][positionInWord] = text.charAt(charIndexInText);
        }
        
        return dividedText;
    }


}
