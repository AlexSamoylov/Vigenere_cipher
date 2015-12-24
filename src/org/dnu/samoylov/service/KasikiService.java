package org.dnu.samoylov.service;

import org.dnu.samoylov.utils.ViginereHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.dnu.samoylov.utils.ViginereHelper.nod;

public class KasikiService {
    public static int DIGRAM_LENGHT = 3;//количество символов, которое должно совпадать
    public static KasikiService newInstance() {
        return new KasikiService();
    }

    public int processKasiskiAlgorithm(String text) throws IOException {
        List<Integer> countRepeatDigram = new LinkedList<>();//массив, который содержит все длины
        //заполняем этот массив, ища расстояние между одинаковыми триграммами
        for (int i = 0; i < text.length() - DIGRAM_LENGHT + 1; i++) {
            String temp = text.substring(i, i+ DIGRAM_LENGHT);
            for (int j = i + 1; j < text.length() - DIGRAM_LENGHT + 1; j++) {
                String temp2 = text.substring(j, j + DIGRAM_LENGHT);
                if (temp.equals(temp2)) {
                    countRepeatDigram.add(j - i);
                }
            }
        }
        Map<Integer, Integer> nods = new HashMap<>();

        //В случае если НОД двух расстояний равен q, то увеличваем nods[q] на 1
        for (int i = 0; i < countRepeatDigram.size(); ++i)
            for (int j = i + 1; j < countRepeatDigram.size(); ++j) {
                final int nod = nod(countRepeatDigram.get(i), countRepeatDigram.get(j));
                ViginereHelper.increaseCounter(nods, nod);
            }


        final int mostPossibleLengthWord = nods.entrySet().stream()
                .sorted((o1, o2) -> -Integer.compare(o1.getValue(), o2.getValue())).findFirst()
                .get().getKey();
        return mostPossibleLengthWord;
    }


}
