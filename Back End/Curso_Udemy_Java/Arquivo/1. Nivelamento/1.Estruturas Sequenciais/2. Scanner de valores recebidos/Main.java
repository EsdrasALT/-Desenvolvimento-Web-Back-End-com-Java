import java.util.Locale;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner read = new Scanner(System.in);
		
		String nome,sobrenome,apelido;
		
		nome = read.nextLine();
		sobrenome = read.nextLine();
		apelido = read.nextLine();
		
		System.out.println("Dados Digitados: ");
		System.out.println(nome);
		System.out.println(sobrenome);
		System.out.println(apelido);
		
		read.close();		
	}

}
