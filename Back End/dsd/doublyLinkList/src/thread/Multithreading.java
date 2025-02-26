package thread;

import java.util.List;

public class Multithreading extends Thread {
    private List<Integer> lista;
    private int valorProcurado;
    private boolean doInicio;
    private Thread outraThread;

    public Multithreading(List<Integer> lista, int valorProcurado, boolean doInicio) {
        this.lista = lista;
        this.valorProcurado = valorProcurado;
        this.doInicio = doInicio;
    }

    public void setOutraThread(Thread outraThread) {
        this.outraThread = outraThread;
    }

    @Override
    public void run() {
        if (doInicio) {
            buscarDoInicio();
        } else {
            buscarDoFim();
        }
    }

    private void buscarDoInicio() {
        for (int i = 0; i < lista.size(); i++) {
        	System.out.println("Buscar do Inicio: " + i);
            if (isInterrupted()) {
                System.out.println("Thread de busca do início foi interrompida.");
                return;
            }

            if (lista.get(i) == valorProcurado) {
                System.out.println("Valor " + valorProcurado + " encontrado no índice " + i + " (busca do início).");
                
                if (outraThread != null) {
                    outraThread.interrupt();
                }
                return;
            }
        }
        System.out.println("Valor " + valorProcurado + " não encontrado (busca do início).");
    }

    private void buscarDoFim() {
        for (int i = lista.size() - 1; i >= 0; i--) {
        	System.out.println("Buscar do Fim: " + i);
            if (isInterrupted()) {
                System.out.println("Thread de busca do fim foi interrompida.");
                return;
            }

            if (lista.get(i) == valorProcurado) {
                System.out.println("Valor " + valorProcurado + " encontrado no índice " + i + " (busca do fim).");

                // Interrompe a outra thread
                if (outraThread != null) {
                    outraThread.interrupt();
                }
                return;
            }
        }
        System.out.println("Valor " + valorProcurado + " não encontrado (busca do fim).");
    }
}