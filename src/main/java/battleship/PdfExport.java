package battleship;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;


public class PdfExport {
    public void exportarJogadasParaPDF(List<Jogada> jogadas, String nomeFicheiro) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Historico de Jogadas");
            contentStream.endText();

            float y = 720;

            for (Jogada jogada : jogadas) {
                String linha = jogada.getNumero() + " - " + jogada.getJogador() + ": " + jogada.getDescricao();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, y);
                contentStream.showText(linha);
                contentStream.endText();

                y -= 20;
            }

            contentStream.close();
            document.save(nomeFicheiro);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
