package me.matamor.commonapi.nms.v1_8_R3.scoreboard;

import java.util.Random;

public class Test {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        final int PARTIDOS = 15;

        String[] resultados = new String[PARTIDOS];

        for (int i = 0; PARTIDOS > i; i++) {
            resultados[i] = crearScore();
        }

        for (int i = 0; PARTIDOS > i; i++) {
            String resultado = resultados[i];

            System.out.printf("%-12s %s\n", "Partido " + (i + 1) + ":", resultado);
        }
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static String crearScore() {
        int random = random(1, 3);
        return (random == 3 ? "X" : String.valueOf(random));
    }
}