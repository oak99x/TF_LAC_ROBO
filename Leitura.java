import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Leitura {

    List<String> transicoes;
    List<String> alfabeto;
    List<String> palavras;

    public Leitura() {
        transicoes = new ArrayList<>();
        alfabeto = new ArrayList<>();
        palavras = new ArrayList<>();
    }

    public void LeituraEntrada() {
        // leitura dos arquivos
        String aux[];
        List<String> linhas = new ArrayList<>();
        Path path1 = Paths.get("entrada.txt");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;

            while ((line = reader.readLine()) != null) {

                aux = line.split("[\\W]");

            for (String i : aux) {
                System.out.println(i + " / ");
            }
               
            linhas.add(line);

            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
        // --------------------------------------------------------------------------------------------
       
        // separar a entrada em linhas
        // pegar cada linha e dar split
        // ir qiebrando até ter a lista do alfabeto e as transições
       // for (int i = 0; i < linhas.get(i).length() - 1; i++) {


    }

    public void LeituraPalavras() {

        // leitura dos arquivos
        // String aux[];
        Path path1 = Paths.get("entrada.txt");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                // aux = line.split();
                palavras.add(line);
            }

        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
    }

}
