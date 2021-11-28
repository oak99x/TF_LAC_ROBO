import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Leitura {

    private NFAtoDFA nfa;

    public Leitura() {
        nfa = new NFAtoDFA();
    }

    public void leituraNFA() {

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

                if (i == 0) {
                    aux = linha.split("[{}]"); // "[\\W]"
                    String aux2[] = aux[1].split(",");
                    String aux3[] = aux[3].split(",");
                    String aux4[] = aux[4].split(",");
                    String aux5[] = aux[5].split(",");

                    // estados
                    for (String s : aux2) {
                        nfa.addEstados(s);
                    }
                    // alfabeto
                    for (String s : aux3) {
                        nfa.addAlfabeto(s);
                    }
                    // estado inicial
                    nfa.addIsInicial(aux4[1]);
                    // estados finais
                    for (String s : aux5) {
                        nfa.addIsFinal(s);
                    }
                }

                // definindo as relações (< qi >,< si >) = < qj >
                // descreve a função programa aplicada a um estado qi e um 
                // símbolo de entrada si que leva a computação a um estado qj.
                if (i >= 2) {

                    aux = linha.split("[()]");
                    String aux2[] = aux[1].split(",");
                    String estado_saida = aux2[0];
                    String simbolo = aux2[1];

                    String aux3[] = aux[2].split(" ");
                    String estado_entrada = aux3[2];

                    //apos todos os dados separados da-se inicio a  montagem do NFA
                    nfa.add_NFA_transicoes(estado_saida, simbolo, estado_entrada, aux.length);
                }

            }

        } catch (

        IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

    }

    public void leituraPalavras() {

        // leitura das palavras
        Path path1 = Paths.get("palavras.txt");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                nfa.addPalavras(line);
            }

            //apos a leitura de todas a palavras  no arquivo, da-se inicio
            //ao teste de validação.
            nfa.test();

        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

    }

}
