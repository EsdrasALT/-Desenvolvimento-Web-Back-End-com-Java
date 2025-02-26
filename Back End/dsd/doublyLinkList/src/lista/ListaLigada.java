package lista;

import java.util.LinkedList;

public class ListaLigada {
    private LinkedList<Integer> lista;

    public ListaLigada() {
        lista = new LinkedList<>();
    }

    public void preencherLista(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            lista.add(i);
        }
    }

    public boolean contem(int valor) {
        return lista.contains(valor);
    }

    public LinkedList<Integer> getLista() {
        return lista;
    }
}

