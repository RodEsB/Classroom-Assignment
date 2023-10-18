class Aula {
    int capacidad;
    String detalles;
    boolean disponible;
    String facultad;
    String[] complementos;

    Aula(int capacidad, String detalles, String facultad, String[] complementos) {
        this.capacidad = capacidad;
        this.detalles = detalles;
        this.disponible = true;
        this.facultad = facultad;
        this.complementos = complementos;
    }

    boolean hasComplementos(String[] complementosNecesarios) {
        for (String complemento : complementosNecesarios) {
            boolean encontrado = false;
            for (String complementoAula : this.complementos) {
                if (complemento.equals(complementoAula)) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                return false;
            }
        }
        return true;
    }

}
