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
import java.util.*;

public class Leitura {

    List<String> transicoes;
    List<String> estados;
    List<String> alfabeto;
    List<String> palavras;
    String isInicial;
    List<String> isFinal;
    Map<String, Map<Character, List<String>>> prog;
    

    public Leitura() {
        transicoes = new ArrayList<>();
        alfabeto = new ArrayList<>();
        palavras = new ArrayList<>();
        isInicial = "";
        isFinal = new ArrayList<>();
        prog = new TreeMap<String, Map<Character, List<String>>>();
        estados = new ArrayList<>();
    }

    public void LeituraEntrada() {
        // leitura dos arquivos

        List<String> linhas = new ArrayList<>();
        Path path1 = Paths.get("entrada.txt");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;

            while ((line = reader.readLine()) != null) {

                linhas.add(line);

            }

            for (int i = 0; i < linhas.size(); i++) {
                String aux[];
                String linha = linhas.get(i);
                // aux = linha.split("[{}()=, ]"); // "[\\W]"
                // if (s != "") {

                if (i == 0) {
                    aux = linha.split("[{}]"); // "[\\W]"
                    String aux2[] = aux[1].split(",");
                    String aux3[] = aux[3].split(",");
                    String aux4[] = aux[4].split(",");
                    String aux5[] = aux[5].split(",");

                    // transicoes
                    for (String s : aux2) {
                        estados.add(s);
                    }
                    // alfabeto
                    for (String s : aux3) {
                        alfabeto.add(s);
                    }
                    // estado inicil
                    isInicial = aux4[1];
                    // estados finais
                    for (String s : aux5) {
                        isFinal.add(s);
                    }
                }
                if (i >= 2) {

                    aux = linha.split("[()]"); // "[\\W]"
                    String aux2[] = aux[1].split(",");
                    String sai_do_Estado = aux2[0];
                    Character simbolo = aux2[1].charAt(0);

                    String aux3[] = aux[2].split(" ");
                    String vai_para_Estado = aux3[2];

                    // fazer um map
                    // Transitions
                    if (!prog.containsKey(sai_do_Estado))
                        prog.put(sai_do_Estado, new TreeMap<Character, List<String>>());

                    for (int j = 2; j < aux.length; j++) {
                        // difference from DFA: list of next states
                        if (!prog.get(sai_do_Estado).containsKey(simbolo))
                            prog.get(sai_do_Estado).put(simbolo, new ArrayList<String>());
                            prog.get(sai_do_Estado).get(simbolo).add(vai_para_Estado);
                    }
                }

            }

            System.out.println("estados  " + estados);
            System.out.println("alfabeto  " + alfabeto);
            System.out.println("estado inicial  " + isInicial);
            System.out.println("estados finais  " + isFinal);
            System.out.println("----------------------------------------------");
            System.out.println("Transicoes " + prog);

        } catch (

        IOException e) {
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
