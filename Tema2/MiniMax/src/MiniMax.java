import java.util.ArrayList;

public class MiniMax {

    public static Par<Integer, Integer> miniMax(TicTacToe tableroActual, boolean maximizar) {

        Casilla casillaGanadora = tableroActual.obtenerGanador();
        if (casillaGanadora != Casilla.VACIO) {
            return new Par<>(tableroActual.valorTablero(), 1);
        }

        ArrayList<Par<Integer, Integer>> celdasDisponibles = tableroActual.obtenerCeldasDisponibles();
        if (celdasDisponibles.size() == 0) {
            return new Par<>(tableroActual.valorTablero(), 1);
        }

        // ((x, y), valorTablero)
        Par<Par<Integer, Integer>, Integer> valorAEscoger = null;
        int tablerosRevisados = 0;

        for (Par<Integer, Integer> par: celdasDisponibles) {
            TicTacToe nuevoTablero = tableroActual.clonar();
            nuevoTablero.mover(par);

            Par<Integer, Integer> valorR = MiniMax.miniMax(nuevoTablero, !maximizar);
            int valor = valorR.x;
            tablerosRevisados += valorR.y;

            if (valorAEscoger == null) {
                valorAEscoger = new Par<>(par, valor);
            } else if (maximizar) {
                if (valor > valorAEscoger.y) {
                    valorAEscoger = new Par<>(par, valor);
                }
            } else {
                if (valor < valorAEscoger.y) {
                    valorAEscoger = new Par<>(par, valor);
                }
            }

        }

        tableroActual.mover(valorAEscoger.x);

        return new Par<>(valorAEscoger.y, tablerosRevisados);
    }

}
