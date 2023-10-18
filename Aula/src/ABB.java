class ABB {
    Nodo raiz;

    void insert(int capacidad, String detalles, String facultad, String[] complementos) {
        raiz = insertRec(raiz, capacidad, detalles, facultad, complementos);
    }

    Nodo insertRec(Nodo raiz, int capacidad, String detalles, String facultad, String[] complementos) {
        if (raiz == null) {
            raiz = new Nodo(new Aula(capacidad, detalles, facultad, complementos));
            return raiz;
        }
        if (capacidad < raiz.aula.capacidad) {
            raiz.izquierda = insertRec(raiz.izquierda, capacidad, detalles, facultad, complementos);
        } else {
            raiz.derecha = insertRec(raiz.derecha, capacidad, detalles, facultad, complementos);
        }
        return raiz;
    }

    Aula findAndReduce(int capacidad, String facultad, String[] complementosNecesarios) {
        Nodo nodoAula = encontrarRec(raiz, capacidad, facultad, complementosNecesarios, null);
        if (nodoAula != null) {
            // Verificar si hay un aula disponible con capacidad exacta
            if (nodoAula.aula.capacidad == capacidad && nodoAula.aula.disponible) {
                nodoAula.aula.capacidad -= capacidad;
                if (nodoAula.aula.capacidad == 0) {
                    nodoAula.aula.disponible = false;
                }
                return nodoAula.aula;
            } else {
                // Buscar la capacidad más cercana
                Aula aulaCercana = encontrarCapacidadCercana(raiz, capacidad, facultad, complementosNecesarios);
                if (aulaCercana != null) {
                    aulaCercana.capacidad -= capacidad;
                    if (aulaCercana.capacidad == 0) {
                        aulaCercana.disponible = false;
                    }
                    return aulaCercana;
                }
            }
        }
        return null;
    }

    Aula encontrarCapacidadCercana(Nodo raiz, int capacidad, String facultad, String[] complementosNecesarios) {
        Aula aulaCercana = null;
        int menorDiferencia = Integer.MAX_VALUE;

        while (raiz != null) {
            int diferencia = Math.abs(raiz.aula.capacidad - capacidad);

            if (diferencia < menorDiferencia && raiz.aula.capacidad >= capacidad
                    && raiz.aula.facultad.equals(facultad) && raiz.aula.disponible
                    && raiz.aula.hasComplementos(complementosNecesarios)) {
                aulaCercana = raiz.aula;
                menorDiferencia = diferencia;
            }

            if (raiz.aula.capacidad < capacidad) {
                raiz = raiz.derecha;
            } else if (raiz.aula.capacidad > capacidad) {
                raiz = raiz.izquierda;
            } else {
                break;
            }
        }

        return aulaCercana;
    }

    Nodo encontrarRec(Nodo raiz, int capacidad, String facultad, String[] complementosNecesarios, Nodo mejorNodo) {
        if (raiz == null) {
            return mejorNodo;
        }

        if (raiz.aula.capacidad >= capacidad && raiz.aula.facultad.equals(facultad)
                && raiz.aula.disponible) {
            if (raiz.aula.hasComplementos(complementosNecesarios)) {
                if (mejorNodo == null || esMejorAula(raiz.aula, mejorNodo.aula)) {
                    mejorNodo = raiz;
                }
            }
        }

        mejorNodo = encontrarRec(raiz.izquierda, capacidad, facultad, complementosNecesarios, mejorNodo);
        mejorNodo = encontrarRec(raiz.derecha, capacidad, facultad, complementosNecesarios, mejorNodo);

        return mejorNodo;
    }

    boolean esMejorAula(Aula aula1, Aula aula2) {
        return aula1.capacidad > aula2.capacidad;
    }
}
