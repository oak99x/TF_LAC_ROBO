import java.util.*;

public class NFAtoDFA {
	
	/*
	* Usa-se Maps para criar o nfs e o dfa, já nosso trabalho será muito semelhante a grafos
	* NFA : estado -> (character -> [proximos estados])
	* DFA : estado -> (character -> [proximo estado])
	* A diferença entre o NFA e o DFA, é que o NFA pode ter várias transições diferentes de estado para o character
	*/

	//variaveis para o NFA
	Map<String, Map<String, List<String>>> nfaTransicoes;
	List<String> estadosNFA;
	String isInicialNFA;
	List<String> isFinalNFA;

	//variaveis para o DFA
	Map<String, Map<String,String>> dfaTransicoes;
	List<String> estadosDFA;
	String isInicialDFA;
	List<String> isFinalDFA;

	//variaveis de controle
	List<String> alfabeto;
	List<String> palavras;

	// a partir da Leitura do arquivo "entrada.txt" inicialmente cria-se um NFA
	NFAtoDFA() {
		nfaTransicoes = new TreeMap<String, Map<String, List<String>>>(); 
		estadosNFA = new ArrayList<>();
        isInicialNFA = null;
        isFinalNFA = new ArrayList<>();

		alfabeto = new ArrayList<>();
        palavras = new ArrayList<>();      
	}

    public void addEstados(String s) {
        estadosNFA.add(s);
    }

    public void addAlfabeto(String s) {
        alfabeto.add(s);
    }

    public void addIsInicial(String s) {
        isInicialNFA = s;
    }

    public void addIsFinal(String s) {
        isFinalNFA.add(s);
    }

    public void add_NFA_transicoes(String estado_saida, String simbolo, String estado_entrada, int t) {
        // montamos o map com as relações
		if (!nfaTransicoes.containsKey(estado_saida)) {
			nfaTransicoes.put(estado_saida, new TreeMap<String, List<String>>());
		}

		for (int j = 2; j < t; j++) {
			// diferença entre o NFA e o DFA:
			// O NFA lista os próximos estados
			if (!nfaTransicoes.get(estado_saida).containsKey(simbolo)) {
				nfaTransicoes.get(estado_saida).put(simbolo, new ArrayList<String>());
			}
			nfaTransicoes.get(estado_saida).get(simbolo).add(estado_entrada);

		}
    }

    public void addPalavras(String p) {
        palavras.add(p);
    }

//=================================================================================================

	public void convert_NFAtoDFA(){

		//Constrói o DFA a partir das matrizes, conforme especificado no cabeçalho geral
		dfaTransicoes = new TreeMap<String, Map<String,String>>();
		estadosDFA = new ArrayList<>();
		isFinalDFA = new ArrayList<>();
		isInicialDFA = isInicialNFA; 

		/* 
		Para simular a tabela de equivalencia precisamos de uma lista de estados atuais
		Pois o proximo estado gerado para o DFA pode conter mais de um estado do NFA
		Exemplo:    Sigma |   a    | b
		              q0  |  q0q2  | q0
		             q0q2 | q1q2q3 | q0
		*/
		List<String> estadoAtual = new ArrayList<>();

		// A fila nos auxilia a ter controle sobre os estados novos que vão surgindo
		Queue<List<String>> fila = new LinkedList<>();

		// Adiciona-se o estado inicial 
		estadoAtual.add(isInicialDFA);
		fila.add(estadoAtual);
		estadosDFA.add(isInicialDFA);

		//Estrutura para simular a tabela de equivalenca estudada em aula
		while(!fila.isEmpty()){

			List<String> novoEstado = new ArrayList<>();
			novoEstado = fila.peek();

			// a cada estado percorre-se todo o alfabeto para saber com que
			// simbolos ele tem relações e a quais estados elas nos levam
			for(int i = 0; i < alfabeto.size(); i++){

				String simbolo = alfabeto.get(i);

				// a lista de estados abaixo se refere aos proximos estados que
				// o estadoatual se relaciona
				List<String> estados = new ArrayList<>();

				// para todo n (estado) na lista de novoEstado, busca-se seus proximos estados
				// adiciona-se na lista de estados, todos os "proximos estados" encontrados
				for(String n : novoEstado){

					// se o estado não existir no NFA ou o estado junto do simbolo não levar a nada
					// pula-se esse estado e segue para o próximo
					if(nfaTransicoes.get(n) == null || nfaTransicoes.get(n).get(simbolo)==null){
						continue;
					}

					// um estado junto de um simbolo pode ir para mais de um estado
					// então vai_para recebe todos esses proximos estados, servindo 
					// como auxliar na tarefa de adiciona-los na lista de estados
					List<String> vai_para = nfaTransicoes.get(n).get(simbolo);

					for(String v : vai_para){
						if(!estados.contains(v)){ // só adiciona se o estado ainda não está na lista
							estados.add(v);
						}
						
					}	
				}

				// se a lista de estados for igual a 0 pula-se para o proximo simbolo do alfabeto
				if(estados.size()==0){
					continue;
				}

				/*
				Os estados encontram-se cada um em uma posição do array
				aqui, vamos unificar seus nomes em uma só string a fim de formar um "novo nome"
				idendificando aquele grupo de estados
				*/
				//novo nome estado aual
				String atualEstado = "";
				for(String b : novoEstado){
					atualEstado+=b;
				}
				//novo nome para o proximo Estado
				String proximoEstado = "";
				for(String s : estados){
					proximoEstado+=s;	
				}

				// Transicoes DFA
				// montamos o map com as relações
				if (!dfaTransicoes.containsKey(atualEstado)){
					dfaTransicoes.put(atualEstado, new TreeMap<String,String>());
					dfaTransicoes.get(atualEstado).put(simbolo, proximoEstado);
				}else{
					dfaTransicoes.get(atualEstado).put(simbolo, proximoEstado);
				}			
				

				//se o estado que surgiu ainda nao estiver na "tabela" adiciona ele na fila
				Set<String> chaves = dfaTransicoes.keySet();
				if(!chaves.contains(proximoEstado)){
					fila.add(estados);
					estadosDFA.add(proximoEstado);
				 }
				
			}

			// Apos verificar todo o alfabeto para tal estado, como já foi usado, remove-se ele da fila
			fila.remove();

		}

		// verifica-se quais estados do DFA contém os estados finais do NFA
		// os estados encontados formam os estados finais do DFA
		for(String s : estadosDFA){
			for(String f : isFinalNFA){
				if( s.toLowerCase().contains(f.toLowerCase())){
					isFinalDFA.add(s);
				}
			}
		}
	
	}

//=================================================================================================


	/*
	* Retorna se o DFA aceita ou não a string 
	* Segue as transições de acordo com seus caracteres, chegando a um estado final no final da string 
	*/
	public String match(String palavra) {
		String curr = isInicialDFA; // estado atual

		// loop usado para a verificação dos simbolos presentes na palavras
		// e a validando para a proximo etapa de verificação.
		for (int i=0; i<palavra.length(); i++) {
			//simbolo atual
			String c = Character.toString(palavra.charAt(i));

			// se o DFA não contém o estado, então rejeitada-se logo no inicio
			if (dfaTransicoes.get(curr) == null){ 
				return "Rejeitada";
			}

			// se o DFA contém o estado, mas a relação não contém tal simbolo 
			// então rejeitada-se
			if (!dfaTransicoes.get(curr).containsKey(c)) { 
				return  "Rejeitada";
			}
		 
			// passando pelas verificações, da-se um passo de acordo com o simbolo atual (c)
			curr = dfaTransicoes.get(curr).get(c);
		}

		// acabamos em um dos estados finais desejados?
		// verifica-se algum dos estados finais do DFA é igual ao estado atual
		// se tudo estiver ok a palavra vai ser validada como "Aceita", caso contrario "Rejeitada"
		if (isFinalDFA.contains(curr)) {
			return "Aceita";
		}
		return "Rejeitada";
	}

	public void test() {

		// primeiro, converte-se o NFA para DFA
		convert_NFAtoDFA();

		System.out.println("NFA: " + nfaTransicoes+"\n");
		System.out.println("DFA: " + dfaTransicoes+"\n");
		System.out.println("----------------------------------------------");

		// apos conversão, testa-se cada palavra da lista de palavras
		// e imprimindo na tela sua resposta.
		System.out.println("*** LISTA DE PALAVRAS ***");
		for (String p : palavras) {
			System.out.println(p + " -> " + match(p));
		}
	}
}
