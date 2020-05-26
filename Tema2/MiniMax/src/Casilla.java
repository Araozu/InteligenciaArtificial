enum Casilla {
    VACIO,
    X, // Celda del jugador
    O; // Celda del computador

    public int valor() {
        switch (this) {
            case X: {
                return 10;
            }
            case O: {
                return -10;
            }
            default: {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case X: {
                return " X ";
            }
            case O: {
                return " O ";
            }
            default: {
                return "   ";
            }
        }
    }
}
