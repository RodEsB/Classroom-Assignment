public class Main {
    public static void main(String[] args) {
        ABB abb = new ABB();

        abb.insert(30, "Edificio A - Aula 101", "Ingeniería", new String[]{"Proyector", "Computadora"});
        abb.insert(40, "Edificio A - Aula 102", "Ciencias", new String[]{"Proyector"});
        abb.insert(50, "Edificio B - Aula 201", "Ingeniería", new String[]{"Pizarra"});

        Aula aulaAsignada = abb.findAndReduce(70, "Ingeniería", new String[]{"Pizarra"});



        if (aulaAsignada != null) {
            System.out.println("Aula asignada: " + aulaAsignada.detalles);
        } else {
            System.out.println("No se encontró un aula adecuada.");
        }
    }
}
