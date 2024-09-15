import java.util.Locale;

public class Main {

	public static void main(String[] args) {

		System.out.println("Esse é um texto Com quebra de linha");
		System.out.print("Esse é um texto Sem quebra de linha");
		System.out.print("\n\n"); // para quebra de linha use \n

		String stn = "teste";

		System.out.print("Isso é um " + stn + " de impressão");

		// Exercicio de Fixação

		String product1 = "Computer";
		String product2 = "Office desk";

		byte age = 30;
		int code = 5290;
		char gender = 'F';

		double price1 = 2100.0;
		double price2 = 650.50;
		double measure = 53.234567;

		/*
		 * Em seguida, usando os valores das variáveis, produza a seguinte saída na tela
		 * do console: Products: Computer, which price is $ 2100,00 Office desk, which
		 * price is $ 650,50
		 * 
		 * Record: 30 years old, code 5290 and gender: F
		 * 
		 * Measue with eight decimal places: 53,23456700 Rouded (three decimal places):
		 * 53,235 US decimal point: 53.235
		 */
		System.out.println("\n\nProducts:");
		System.out.printf("%s, which price is $ %.2f\n", product1, price1);
		System.out.printf("%s, which price is $ %.2f\n\n", product2, price2);
		System.out.printf("Record: %d years oldd, code %d and gender: %c\n\n", age, code, gender);
		System.out.printf("Measue with eight decima places: %.8f\n", measure);
		System.out.printf("Rouded (three decimal places): %.3f\n", measure);
		Locale.setDefault(Locale.US);
		System.out.printf("US decimal point: %.3f\n", measure);

	}

}
