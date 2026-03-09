/**
 * 
 */
package battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Main.
 *
 * @author britoeabreu
 * @author adrianolopes
 * @author miguelgoulao
 */
public class Main
{
	/**
	 * Main.
	 *
	 * @param args the args
	 */
	public static void main(String[] args)
    {

		List<Jogada> jogadas = new ArrayList<>();

		jogadas.add(new Jogada(1, "Jogador 1", "moveu para A3"));
		jogadas.add(new Jogada(2, "Jogador 2", "atacou B4"));
		jogadas.add(new Jogada(3, "Jogador 1", "defendeu C2"));

		PdfExport pdfExportService = new PdfExport();

		pdfExportService.exportarJogadasParaPDF(jogadas, "jogadas.pdf");

		System.out.println("PDF gerado com sucesso.");
		System.out.println("***  Battleship  ***");

		Tasks.menu();
    }
}
