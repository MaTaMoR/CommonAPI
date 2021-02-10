package me.matamor.commonapi.economy;

import java.util.Random;
import java.util.Scanner;

public class Porras {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        final int PUNTOS_MAXIMOS = 11; //El maximo de puntos
        final int PORRAS_MAXIMAS = 5; //El maximo de porras

        final int PORRAS_POR_VICTORIA = 1; //Las porras que se ganan en una victoria normal
        final int PORRAS_POR_VICTORIA_PERFECTA = 2; //Las porras que se ganan en una victoria perfecta

        final int ESPERA_ENTRE_TURNOS = 2500; //Tiempo de spera

        int porrasJugador = 0;
        int porrasMaquina = 0;

        while (porrasJugador < PORRAS_MAXIMAS && porrasMaquina < PORRAS_MAXIMAS) {
            boolean turnoJugador = true; //Controla el turno del jugador
            boolean turnoMaquina = false; //Controla el turno de la maquina

            int puntuacionJugador = 0; //Puntuacion del jugador
            int puntuacionMaquina = 0; //Puntuacion de la maquina

            while (turnoJugador) {
                int aleatorio = random.nextInt(6) + 1; //Tiramos el dado
                puntuacionJugador += aleatorio; //Sumamos el dado al total de puntos del jugador

                //Mostramos la información del dado que hemos tirado
                print("Tiras el dado y sacas un: " + aleatorio, "Total de puntos: " + puntuacionJugador);

                if (puntuacionJugador == PUNTOS_MAXIMOS) {
                    //El jugador ha sacado 11 puntos, por lo tanto gana de manera perfecta!

                    porrasJugador += PORRAS_POR_VICTORIA_PERFECTA; //Sumamos las porras de vitoria perfecta
                    puntuacionJugador = 0; //Reiniciamos los puntos del jugador para el siguiente turno
                    turnoJugador = porrasJugador < PORRAS_MAXIMAS; //Comprobamos que la maquina no haya ganado

                    //Mostramos por pantalla el mensaje de victoria y el total de porras
                    print("Tienes " + PUNTOS_MAXIMOS + " puntos... Ganas el turno!", "Porras del jugador: " + porrasJugador + " (+" + PORRAS_POR_VICTORIA_PERFECTA + ")");

                    wait(ESPERA_ENTRE_TURNOS); //Esperar antes de empezar el siguiente turno!
                } else if (puntuacionJugador > PUNTOS_MAXIMOS) {
                    //El jugador ha sacado más de 11 puntos, por lo tanto pierde!

                    porrasMaquina += PORRAS_POR_VICTORIA; //Sumamos a la maquina las porras por victoria
                    puntuacionJugador = 0; //Reiniciamos los puntos del jugador para el siguiente turno
                    turnoJugador = porrasMaquina < PORRAS_MAXIMAS; //Comprobamos que el jugador no haya ganado

                    //Mostramos por pantalla el mensaje de victoria y el total de porras
                    print("Tienes más de " + PUNTOS_MAXIMOS + " puntos... Pierdes el turno!", "Porras de la máquina: " + porrasMaquina + " (+" + PORRAS_POR_VICTORIA + ")");

                    wait(ESPERA_ENTRE_TURNOS); //Esperar antes de empezar el siguiente turno!
                } else {
                    //El jugador ha sacado menos de 11 puntos, por lo tanto puede seguir jugando o retirarse!

                    boolean respuestaValida = false;

                    while (!respuestaValida) {
                        print("¿ Deseas seguir tirando dados ? (si/no)");

                        String input = scanner.nextLine();

                        if (input.equalsIgnoreCase("si")) {
                            respuestaValida = true;
                            turnoJugador = true;
                        } else if (input.equalsIgnoreCase("no")) {
                            respuestaValida = true;
                            turnoJugador = false;
                        }
                    }

                    turnoMaquina = !turnoJugador;

                    if (!turnoJugador) {
                        print("Te retiras con " + puntuacionJugador + " puntos...", "Empieza el turno de la máquina");
                        wait(ESPERA_ENTRE_TURNOS); //Esperar antes de empezar el siguiente turno!
                    }
                }
            }

            while (turnoMaquina) {
                int aleatorio = random.nextInt(6) + 1; //Tiramos los dados para la máquina
                puntuacionMaquina += aleatorio; //Sumamos los puntos a la maquina

                print("La maquina tira el dado y saca un: " + aleatorio, "Total de puntos: " + puntuacionMaquina);

                if (puntuacionMaquina == PUNTOS_MAXIMOS) {
                    //La máquina ha conseguido una puntuación perfecta, gana más puntos!
                    porrasMaquina += PORRAS_POR_VICTORIA_PERFECTA;
                    turnoMaquina = false;

                    print("La máquina tiene " + PUNTOS_MAXIMOS + " puntos... La máquina gana la ronda!", "Porras de la maquina: " + porrasMaquina + " (+" + PORRAS_POR_VICTORIA_PERFECTA + ")");
                } else {
                    if (puntuacionMaquina > PUNTOS_MAXIMOS) {
                        //La maquina ha sacado mas de 11 puntos, por lo cual pierde!

                        porrasJugador += PORRAS_POR_VICTORIA;
                        turnoMaquina = false;

                        print("La máquina ha sacado más de " + PUNTOS_MAXIMOS + " puntos... La máquina pierde la ronda!", "Porras del jugador: " + porrasJugador + " (+" + PORRAS_POR_VICTORIA + ")");
                    } else if (puntuacionMaquina >= puntuacionJugador) {
                        //La maquina ha sacado mas o ha igualado los puntos del jugador, gana la ronda!

                        porrasMaquina += PORRAS_POR_VICTORIA;
                        turnoMaquina = false;

                        print("La máquina ha igualado o superado los puntos del jugador... La máquina gana la ronda!", "Porras de la máquina: " + porrasMaquina + " (+" + PORRAS_POR_VICTORIA + ")");
                    }
                }

                wait(ESPERA_ENTRE_TURNOS); //Esperar antes de volver a tirar el dado!
            }
        }

        if (porrasMaquina > porrasJugador) {
            print("La máquina gana con " + porrasMaquina + " porras!");
        } else {
            print("El jugador gana con " + porrasJugador + " porras!");
        }
    }

    private static void print(String... strings) {
        int maxLength = 60;

        for (String string : strings) {
            int length = string.length();

            if (length > maxLength) {
                maxLength = length;
            }
        }

        //Java > 8 String.repeat(int)
        String lines = repeat("-", maxLength);

        System.out.println(lines);
        System.out.println();
        for (String string : strings) {
            int length = string.length();
            int spaces = (maxLength - length) / 2;

            System.out.println(repeat(" ", spaces) + string);
        }

        System.out.println();
        System.out.println(lines);
    }

    private static String repeat(String string, int times) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; times > i; i++) {
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

    private static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}