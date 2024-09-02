import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String[][] asistentes = {
                {"Juan", "20", "General", "2", "false"},
                {"Ana", "17", "VIP", "1", "false"},
                {"Pedro", "25", "General", "1", "false"},
                {"Cristian", "25", "General", "0", "false"},
                {"Maria", "25", "General", "1", "false"},
                {"Sofia", "22", "General", "0", "false"},
                {"Lucas", "30", "General", "2", "false"},
                {"Camila", "28", "VIP", "0", "false"},
                {"Sebastian", "35", "General", "6", "false"},
                {"Valentina", "26", "General", "2", "false"}
        };

        int aforoVIP = 0;
        int aforoGENERAL = 0;
        Random random = new Random();

        int numeroVueltas = 5;
        for (int vuelta = 0; vuelta < numeroVueltas; vuelta++) {
            int randomIndex = random.nextInt(asistentes.length);
            int[] result = procesarEntrada(asistentes, randomIndex, aforoVIP, aforoGENERAL);
            aforoVIP = result[0];
            aforoGENERAL = result[1];
        }

        asistentes = eliminarPersona(asistentes, 0);
        aforoVIP = actualizarAforoDespuesDeSalida(asistentes[0], aforoVIP);
        aforoGENERAL = actualizarAforoDespuesDeSalida(asistentes[0], aforoGENERAL);

        imprimirAforo(aforoVIP, aforoGENERAL);
    }

    public static int[] procesarEntrada(String[][] asistentes, int indice, int aforoVIP, int aforoGENERAL) {
        if (permitirEntrada(asistentes, indice, aforoVIP, aforoGENERAL)) {
            System.out.println(asistentes[indice][0] + " puede entrar.");
            asistentes = ingresarPersona(asistentes, indice);
            int invitados = Integer.parseInt(asistentes[indice][3]);
            if ("VIP".equals(asistentes[indice][2])) {
                aforoVIP += invitados + 1;
            } else if ("General".equals(asistentes[indice][2])) {
                aforoGENERAL += invitados + 1;
            }
            imprimirAforo(aforoVIP, aforoGENERAL);
        } else {
            System.out.println(asistentes[indice][0] + " no puede entrar.");
        }
        return new int[] { aforoVIP, aforoGENERAL };
    }

    public static String[][] eliminarPersona(String[][] asistentes, int fila) {
        String boleto = asistentes[fila][2];
        int invitados = Integer.parseInt(asistentes[fila][3]);
        int totalPersonas = 1 + invitados;

        asistentes[fila][4] = "false";
        return asistentes;
    }

    public static int actualizarAforoDespuesDeSalida(String[] persona, int aforo) {
        String boleto = persona[2];
        int invitados = Integer.parseInt(persona[3]);
        int totalPersonas = 1 + invitados;

        if ("VIP".equals(boleto)) {
            aforo = Math.max(0, aforo - totalPersonas);
        } else if ("General".equals(boleto)) {
            aforo = Math.max(0, aforo - totalPersonas);
        }
        return aforo;
    }

    public static void imprimirAforo(int aforoVIP, int aforoGENERAL) {
        System.out.println("Aforo VIP: " + aforoVIP);
        System.out.println("Aforo General: " + aforoGENERAL);
    }

    public static boolean verificarEdad(String[][] asistentes, int fila) {
        int edad = Integer.parseInt(asistentes[fila][1]);
        return edad >= 18;
    }

    public static boolean verificarBoleto(String boleto) {
        return "VIP".equals(boleto) || "General".equals(boleto);
    }

    public static boolean verificarInvitados(String[][] asistentes, int fila) {
        int invitados = Integer.parseInt(asistentes[fila][3]);
        return invitados <= 2;
    }

    public static String[][] ingresarPersona(String[][] asistentes, int fila) {
        asistentes[fila][4] = "true";
        return asistentes;
    }

    public static boolean permitirEntrada(String[][] asistentes, int fila, int aforoVIP, int aforoGENERAL) {
        String boleto = asistentes[fila][2];
        int invitados = Integer.parseInt(asistentes[fila][3]);
        int totalPersonas = 1 + invitados;

        if (!verificarEdad(asistentes, fila) ||
                !verificarBoleto(boleto) ||
                !verificarInvitados(asistentes, fila)) {
            return false;
        }

        if ("VIP".equals(boleto)) {
            return aforoVIP + totalPersonas <= 10;
        } else if ("General".equals(boleto)) {
            return aforoGENERAL + totalPersonas <= 10;
        }

        return false;
    }
}
