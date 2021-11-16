import java.util.*;

public class NFAtoDFA {
	
	Map<String, Map<String, List<String>>> nfaTransicoes;
	List<String> estadosNFA;
	String isInicialNFA;
	List<String> isFinalNFA;

	Map<String, Map<String,String>> dfaTransicoes;
	List<String> estadosDFA;
	String isInicialDFA;
	List<String> isFinalDFA;

	List<String> alfabeto;
	List<String> palavras;

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
        // fazer um map - ex q0->B->q1
		if (!nfaTransicoes.containsKey(estado_saida)) {
			nfaTransicoes.put(estado_saida, new TreeMap<String, List<String>>());
		}

		for (int j = 2; j < t; j++) {
			// difference from DFA: list of next states
			if (!nfaTransicoes.get(estado_saida).containsKey(simbolo)) {
				nfaTransicoes.get(estado_saida).put(simbolo, new ArrayList<String>());
			}
			nfaTransicoes.get(estado_saida).get(simbolo).add(estado_entrada);

		}
    }

    public void addPalavras(String p) {
        palavras.add(p);
    }


	public void convet_NFAtoDFA(){
		
		dfaTransicoes = new TreeMap<String, Map<String,String>>();
		estadosDFA = new ArrayList<>();
		isFinalDFA = new ArrayList<>();
		isInicialDFA = isInicialNFA;

		List<String> inicio = new ArrayList<>();
		Queue<List<String>> pilha = new LinkedList<>();

		inicio.add(isInicialNFA);
		pilha.add(inicio);
		estadosDFA.add(isInicialNFA);

		//equivale a de tabela de transição 
		while(!pilha.isEmpty()){

			//int verifica = 0;

			List<String> novoEstado = new ArrayList<>();
			novoEstado = pilha.peek();

			//System.out.println(pilha);
			for(int i = 0; i < alfabeto.size(); i++){

				List<String> estados = new ArrayList<>();
				String simbolo = alfabeto.get(i);

				for(String n : novoEstado){

					if(nfaTransicoes.get(n) == null || nfaTransicoes.get(n).get(simbolo)==null){
						continue;
					}

					List<String> vai_para = nfaTransicoes.get(n).get(simbolo);

					for(String v : vai_para){
						if(!estados.contains(v)){
							estados.add(v);
						}
						
					}	
				}

				if(estados.size()==0){
					continue;
				}

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


				if (!dfaTransicoes.containsKey(atualEstado)){
					dfaTransicoes.put(atualEstado, new TreeMap<String,String>());
					dfaTransicoes.get(atualEstado).put(simbolo, proximoEstado);
				}else{
					dfaTransicoes.get(atualEstado).put(simbolo, proximoEstado);
				}			
				

				//se o estado que surgiu ainda nao estiver na tabela adiciona ele
				Set<String> chaves = dfaTransicoes.keySet();
				if(!chaves.contains(proximoEstado)){
					pilha.add(estados);
					estadosDFA.add(proximoEstado);
				 }
				 //else{
				// 	verifica++;
				// }		
			}

			pilha.remove();

			//se os tamanhos forem iguais então nenhum novo estado surgiu
			//encerra a conversao
			//  if(verifica >= alfabeto.size()){
			//  	break;
			//  }
		}

		for(String s : estadosDFA){
			for(String f : isFinalNFA){
				if( s.toLowerCase().contains(f.toLowerCase())){
					isFinalDFA.add(s);
				}
			}
		}

		//System.out.println(dfaTransicoes);		
	}

	public String match(String palavra) {
		String curr = isInicialDFA; // where we are now

		for (int i=0; i<palavra.length(); i++) {
			String c = Character.toString(palavra.charAt(i));

			if (dfaTransicoes.get(curr) == null){
				//System.out.println(estado + "-> vazio");
				return "Rejeitada";
			}

			if (!dfaTransicoes.get(curr).containsKey(c)) {
				return  "Rejeitada";
			}
			curr = dfaTransicoes.get(curr).get(c); // take a step according to c
		}

		if (isFinalDFA.contains(curr)) {
			return "Aceita";
		}
		return "Rejeitada"; // did we end up in one of the desired final states?
	}

	public void test() {

		convet_NFAtoDFA();

		System.out.println("*** LISTA DE PALAVRAS ***");
		for (String p : palavras) {
			System.out.println(p + " -> " + match(p));
		}
	}
}
