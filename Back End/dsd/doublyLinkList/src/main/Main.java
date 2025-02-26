package main;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import lista.ListaLigada;
import thread.Multithreading;

public class Main {
    public static void main(String[] args) {
        ListaLigada minhaLista = new ListaLigada();
        minhaLista.preencherLista(1000); 

        Random random = new Random();
        int valorProcurado = random.nextInt(1000); 

        System.out.println("Procurando pelo valor: " + valorProcurado);
        
        Multithreading buscaInicio = new Multithreading(minhaLista.getLista(), valorProcurado, true);
        Multithreading buscaFim = new Multithreading(minhaLista.getLista(), valorProcurado, false);

        buscaInicio.setOutraThread(buscaFim);
        buscaFim.setOutraThread(buscaInicio);

        buscaFim.start();
        buscaInicio.start();


        try {
            buscaInicio.join();
            buscaFim.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread foi interrompida.");
        }

        System.out.println("Busca finalizada.");
    }
}