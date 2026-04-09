package compilador;

import utils.gals.*;

public class Compilador {

	public String compilar(String texto) {
	    StringBuilder saida = new StringBuilder();

	    try {
	        Lexico lexico = new Lexico(texto);
	        Token token;
	        

	        saida.append(String.format("%-10s %-25s %-15s\n", "linha", "classe", "lexema"));

	        while ((token = lexico.nextToken()) != null) {
	            int linha = getLinha(texto, token.getPosition());
	            String classe = getClasseToken(token);

	            saida.append(String.format("%-10d %-25s %-15s\n", linha, classe, token.getLexeme()));
	        }

	        saida.append("\nprograma compilado com sucesso");

	    } catch (LexicalError e) {
	        int linha = getLinha(texto, e.getPosition());
	        return formatarErro(e, linha, texto);
	    }

	    return saida.toString();
	}

    // =========================
    // LINHA
    // =========================
    private int getLinha(String texto, int posicao) {
        int linha = 1;
        for (int i = 0; i < posicao && i < texto.length(); i++) {
            if (texto.charAt(i) == '\n') linha++;
        }
        return linha;
    }

    // =========================
    // CLASSE TOKEN
    // =========================
    private String getClasseToken(Token token) {
        int id = token.getId();

        if (id >= 7 && id <= 24) return "palavra reservada";

        if (id == 2) return "identificador";
        if (id == 3) return "constante_int";
        if (id == 4) return "constante_float";
        if (id == 5) return "constante_char";
        if (id == 6) return "constante_string";

        return "símbolo especial";
    }

    private String formatarErro(LexicalError e, int linha, String texto) {

        String msg = e.getMessage();

        char simbolo = '?';
        if (e.getPosition() < texto.length()) {
            simbolo = texto.charAt(e.getPosition());
        }

        if (msg.contains("símbolo")) {
            return "programa apresenta erro\nErro na linha " + linha +
                   ": símbolo inválido (" + simbolo + ")";
        }

        if (msg.contains("identificador")) {
            return "programa apresenta erro\nErro na linha " + linha +
                   ": identificador inválido";
        }

        if (msg.contains("float")) {
            return "programa apresenta erro\nErro na linha " + linha +
                   ": constante_float inválida";
        }

        if (msg.contains("char")) {
            return "programa apresenta erro\nErro na linha " + linha +
                   ": constante_char inválida";
        }

        if (msg.contains("string")) {
            return "programa apresenta erro\nErro na linha " + linha +
                   ": constante_string inválida";
        }

        if (msg.contains("comentário")) {
            return "programa apresenta erro\nErro na linha " + linha +
                   ": comentário inválido ou não finalizado";
        }

        return "programa apresenta erro\nErro na linha " + linha + ": " + msg;
    }
}