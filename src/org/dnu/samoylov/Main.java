package org.dnu.samoylov;

import org.dnu.samoylov.service.DecryptionService;
import org.dnu.samoylov.service.KasikiService;
import org.dnu.samoylov.service.LGramsService;
import org.dnu.samoylov.utils.FileHelper;

import java.io.*;

import static org.dnu.samoylov.utils.ViginereHelper.ALPHABET;

public class Main {
    private final static String SOURCE_FILE_NAME = "text.txt";
    private final static String RESULTED_FILE_NAME = "result.txt";


    public static void main(String... arg) throws IOException {
        KasikiService kasikiService = KasikiService.newInstance();
        LGramsService lGramsService = LGramsService.newInstance();
        DecryptionService decryptionService = DecryptionService.newInstance();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String text = FileHelper.getFileText(SOURCE_FILE_NAME);



        final int mostPossibleLengthWord = kasikiService.processKasiskiAlgorithm(text);
        System.out.println("Предполагаемый размер ключа: " + mostPossibleLengthWord);

        int[] shifts = new int[3];
        final char[][] lGrams = lGramsService.searchLGrams(text, mostPossibleLengthWord, shifts);
        //System.out.println("Сдвиги: " + Arrays.toString(shifts));
        print(lGrams);

        System.out.println("Выбреите и введите ключ шифра:");


        String key = in.readLine();


        final String descrText = decryptionService.decryptionText(text, key);

        System.out.println(descrText);
        FileHelper.writeToFile(RESULTED_FILE_NAME, descrText);

    }


    private static void print(char[][] lGrams) {
        for (int i = 0; i < ALPHABET.length(); i++) {
            for (int j = 0; j < lGrams[i].length; j++) {
                System.out.print(lGrams[i][j]);
            }
            System.out.println();
        }
    }

}
