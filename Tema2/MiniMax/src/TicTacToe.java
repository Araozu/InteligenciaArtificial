import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Scanner;

class TicTacToe {

    private Casilla[][] state = {
        { Casilla.VACIO, Casilla.VACIO, Casilla.VACIO },
        { Casilla.VACIO, Casilla.VACIO, Casilla.VACIO },
        { Casilla.VACIO, Casilla.VACIO, Casilla.VACIO }
    };

    private boolean esTurnoUsuario = Math.random() < 0.5;
    private int casillasDisponibles = 9;

    private TicTacToe(Casilla[][] state, boolean esTurnoUsuario, int casillasDisponibles) {
        this.state = state;
        this.esTurnoUsuario = esTurnoUsuario;
        this.casillasDisponibles = casillasDisponibles;
    }

    public TicTacToe() {}


    public ArrayList<Par<Integer, Integer>> obtenerCeldasDisponibles() {
        ArrayList<Par<Integer, Integer>> celdas = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == Casilla.VACIO) {
                    celdas.add(new Par<>(i, j));
                }
            }
        }

        return celdas;
    }

    public TicTacToe clonar() {
        Casilla[][] arr = new Casilla[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = state[i][j];
            }
        }
        return new TicTacToe(arr, esTurnoUsuario, casillasDisponibles);
    }

    private int sumarCasillas(int px1, int py1, int px2, int py2, int px3, int py3) {
        return state[px1][py1].valor() + state[px2][py2].valor() + state[px3][py3].valor();
    }

    public int valorTablero() {
        int valorFinal = 0;

        // Columnas y filas
        for (int i = 0; i < 3; i++) {
            valorFinal += sumarCasillas(i, 0, i, 1, i, 2);
            valorFinal += sumarCasillas(0, i, 1, i, 2, i);
        }

        // Diagonales
        valorFinal += sumarCasillas(0, 0, 1, 1, 2, 2);
        valorFinal += sumarCasillas(0, 2, 1, 1, 2, 0);

        return valorFinal;
    }

    private Casilla sonCasillasGanadoras(int px1, int py1, int px2, int py2, int px3, int py3) {
        Casilla retorno = Casilla.VACIO;

        if (state[px1][py1] == state[px2][py2] && state[px2][py2] == state[px3][py3]) {
            retorno = state[px1][py1];
        }

        return retorno;
    }

    public Casilla obtenerGanador() {
        Casilla cr;
        for (int i = 0; i < 3; i++) {
            cr = sonCasillasGanadoras(i, 0, i, 1, i, 2);
            if (cr != Casilla.VACIO) return cr;
            cr = sonCasillasGanadoras(0, i, 1, i, 2, i);
            if (cr != Casilla.VACIO) return cr;
        }

        // Diagonales
        cr = sonCasillasGanadoras(0, 0, 1, 1, 2, 2);
        if (cr != Casilla.VACIO) return cr;
        cr = sonCasillasGanadoras(0, 2, 1, 1, 2, 0);
        if (cr != Casilla.VACIO) return cr;

        return Casilla.VACIO;
    }

    public void mover(Par<Integer, Integer> par) {
        mover(par.x, par.y);
    }

    public void mover(int x, int y) {
        if (state[x][y] == Casilla.VACIO) {
            state[x][y] = esTurnoUsuario? Casilla.X: Casilla.O;
            casillasDisponibles--;
            esTurnoUsuario = !esTurnoUsuario;
        } else {
            throw new RuntimeException("Se intento agregar una movida a una casilla ocupada.");
        }
    }

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        System.out.println(this);

        while (casillasDisponibles > 0 && obtenerGanador() == Casilla.VACIO) {
            if (esTurnoUsuario) {
                System.out.print("Ingresa la coordenada 1 (0-2): ");
                int x = sc.nextInt();
                System.out.print("Ingresa la coordenada 2 (0-2): ");
                int y = sc.nextInt();
                mover(x, y);
            } else {
                Par<Integer, Integer> resultado = MiniMax.miniMax(this, false);
                System.out.println("Se analizaron " + resultado.y + " tableros.\n");
            }
            System.out.println(this);
        }

        Casilla casillaGanadora = obtenerGanador();
        switch (casillaGanadora) {
            case O: {
                System.out.println("El computador gana");
                break;
            }
            case X: {
                System.out.println("El usuario gana");
                break;
            }
            default: {
                System.out.println("Empate");
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder strRes = new StringBuilder();
        boolean esPrimerFila = true;
        for (Casilla[] arrCasilla: state) {
            boolean esPrimer = true;
            if (!esPrimerFila) strRes.append("\n-----------\n");
            for (Casilla casilla: arrCasilla) {
                if (!esPrimer) {
                    strRes.append("|");
                }
                strRes.append(casilla);
                esPrimer = false;
            }
            esPrimerFila = false;
        }
        strRes.append("\n\n");
        return strRes.toString();
    }

}
