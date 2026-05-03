package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PdfExportTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Export creates a PDF file")
    void exportCreatesPdfFile() {
        PdfExport pdfExport = new PdfExport();

        List<Jogada> jogadas = List.of(
                new Jogada(1, "Jogador 1", "moveu para A3"),
                new Jogada(2, "Jogador 2", "atacou B4")
        );

        File outputFile = tempDir.resolve("jogadas.pdf").toFile();

        pdfExport.exportarJogadasParaPDF(jogadas, outputFile.getAbsolutePath());

        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    @DisplayName("Export also works with empty play list")
    void exportWorksWithEmptyList() {
        PdfExport pdfExport = new PdfExport();

        File outputFile = tempDir.resolve("vazio.pdf").toFile();

        pdfExport.exportarJogadasParaPDF(List.of(), outputFile.getAbsolutePath());

        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }
}
