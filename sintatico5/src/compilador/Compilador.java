package compilador;

import utils.gals.*;

public class Compilador {

	private Token ultimoToken = null;
	private Semantico ultimoSemantico = null;
	private boolean sucesso = false;

	public String compilar(String texto) {
		StringBuilder saida = new StringBuilder();
		ultimoToken = null;
		ultimoSemantico = null;
		sucesso = false;

		Lexico lexico = new Lexico();
		Sintatico sintatico = new Sintatico();
		Semantico semantico = new Semantico();
		lexico.setInput(texto);

		ultimoSemantico = semantico;

		Lexico lexicoInterceptado = new Lexico(texto) {
			@Override
			public Token nextToken() throws LexicalError {
				Token t = super.nextToken();
				if (t != null)
					ultimoToken = t;
				return t;
			}
		};

		try {
		    sintatico.parse(lexicoInterceptado, semantico);
		    saida.append("programa compilado com sucesso");
		    sucesso = true;

		} catch (LexicalError e) {
		    int linha = getLinha(texto, e.getPosition());
		    saida.append("linha " + linha + ": erro léxico");

		} catch (SyntaticError e) {
		    int linha = getLinha(texto, e.getPosition());
		    saida.append("linha " + linha + ": erro sintático");
			//		    int linha = getLinha(texto, e.getPosition());
//
//		    String encontrado;
//		    if (e.getPosition() >= texto.trim().length()) {
//		        encontrado = "EOF";
//		    } else {
//		        encontrado = getEncontrado(ultimoToken, texto);
//		    }
//
//		    saida.append("linha " + linha + ": encontrado " + encontrado + " " + e.getMessage());

		} catch (SemanticError e) {
			  int linha = getLinha(texto, e.getPosition());
			    saida.append("linha " + linha + ": " + e.getMessage());		}
		return saida.toString();
	}

	// =========================
	// ACESSO AO RESULTADO DA COMPILAÇÃO
	// =========================
	public boolean isSucesso() {
		return sucesso;
	}

	public String getCodigoObjeto() {
		return ultimoSemantico != null ? ultimoSemantico.getCodigoObjeto() : "";
	}

	// =========================
	// LINHA
	// =========================
	private int getLinha(String texto, int posicao) {
		int linha = 1;
		for (int i = 0; i < posicao && i < texto.length(); i++) {
			if (texto.charAt(i) == '\n')
				linha++;
		}
		return linha;
	}

	// =========================
	// ENCONTRADO (lexema exibido no erro sintático)
	// =========================
	private String getEncontrado(Token token, String texto) {
	    // Verifica se o restante do texto (após o último token) é só espaço/quebra
	    if (token != null) {
	        String resto = texto.substring(
	            Math.min(token.getLexeme() != null ?
	                texto.indexOf(token.getLexeme()) + token.getLexeme().length() : 0,
	                texto.length())
	        ).trim();
	        if (resto.isEmpty()) return "EOF";
	    } else {
	        return "EOF";
	    }

	    int id = token.getId();
	    String lexema = token.getLexeme();

	    if (lexema == null || lexema.isEmpty()) return "EOF";
	    if (id == 6) return "constante_string";
	    return lexema;
	}

	// =========================
	// FORMATAR ERRO LÉXICO
	// =========================
	private String formatarErroLexico(LexicalError e, int linha, String texto) {
	    String msg = e.getMessage();

	    char simbolo = '?';
	    if (e.getPosition() >= 0 && e.getPosition() < texto.length()) {
	        simbolo = texto.charAt(e.getPosition());
	    }

	    if (msg.contains("cstring")) {
	        return "linha " + linha + ": constante_string inválida";
	    }
	    if (msg.contains("cte_char")) {
	        return "linha " + linha + ": constante_char inválida";
	    }
	    if (msg.contains("cte_float")) {
	        return "linha " + linha + ": constante_float inválida";
	    }
	    if (msg.contains("id_a")) {
	        return "linha " + linha + ": identificador inválido";
	    }
	    if (msg.contains("ignorar")) {
	        return "linha " + linha + ": comentário inválido ou não finalizado";
	    }
	    return "linha " + linha + ": " + simbolo + " símbolo inválido";
	}
}