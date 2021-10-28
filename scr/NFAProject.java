import java.util.*;

public class NFAProject {
	List<String> estados;
	List<String> alfabeto;
	List<String> palavras;
	String isInicial;
	List<String> isFinal;
	Map<String, Map<Character, List<String>>> transicoes;

	Leitura le;

	NFAProject() {
		le = new Leitura();
		le.LeituraEntrada();
        le.LeituraPalavras();

		this.estados = le.getEstados();
		this.alfabeto = le.getAlfabeto();
		this.isInicial = le.getIsInicial();
		this.isFinal = le.getIsFinal();
		this.transicoes = le.getTransicoes();

		this.palavras = le.getPalavras();

	}

	/**
	 * Returns whether or not the DFA accepts the string -- follows transitions
	 * according to its characters, landing in an end state at the end of the string
	 */
	public String conveteNFA_DFA(String palavra) {
		// difference from DFA: multiple current states
		Set<String> currStates = new TreeSet<String>();
		currStates.add(isInicial);

		for (int i = 0; i < palavra.length(); i++) {
			char s = palavra.charAt(i);

			Set<String> novoEstado = new TreeSet<String>();

			// transition from each current state to each of its next states
			for (String estado : currStates) {
				if (transicoes.get(estado) == null){
					break;
				}
				
				if (transicoes.get(estado).containsKey(s))
					novoEstado.addAll(transicoes.get(estado).get(s));
			}

			if (novoEstado.isEmpty()) {
				return "Rejeitada"; // no way forward for this input
			}

			currStates = novoEstado;
		}
		// end up in multiple states -- accept if any is an end state
		for (String estado : currStates) {
			// for(String f : isFinal)
			if (isFinal.contains(estado)) {
				return "Aceita";
			}
		}
		return "Rejeitada";
	}

	public void test() {
		System.out.println("*** LISTA DE PALAVRAS ***");

		for (String p : palavras) {
			System.out.println(p + " -> " + conveteNFA_DFA(p));
		}
	}
}
