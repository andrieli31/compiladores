package utils.gals;

import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Semantico implements Constants {

	// registros semanticos
	private StringBuilder codigoObjeto = new StringBuilder();
	private Stack<String> pilhaTipos = new Stack<>();
	private Stack<String> pilhaRotulos = new Stack<>();
	private Stack<String> pilhaOperadores = new Stack<>();
	private List<String> listaIdentificadores = new ArrayList<>();
	private Map<String, String> tabelaSimbolos = new HashMap<>();
	private int contadorRotulos = 0;
	private boolean localsDeclarado = false;
	private String tipoAtual;
	private String ultimoId;

	private String novoRotulo() {
		return "R" + (contadorRotulos++);
	}

	public void executeAction(int action, Token token) throws SemanticError {
		System.out.println("Acao #" + action + ", Token: " + token);

		switch (action) {
		case 1:
			acao1();
			break;
		case 2:
			acao2();
			break;
		case 3:
			acao3();
			break;
		case 4:
			acao4();
			break;
		case 5:
			acao5(token);
			break;
		case 6:
			acao6(token);
			break;
		case 7:
			acao7();
			break;
		case 8:
			acao8();
			break;
		case 11:
			acao11();
			break;
		case 12:
			acao12();
			break;
		case 13:
			acao13();
			break;
		case 14:
			acao14();
			break;
		case 20:
			acao20();
			break;
		case 21:
			acao21();
			break;
		case 9:
			acao9(token);
			break;
		case 10:
			acao10();
			break;
		case 15:
			acao15();
			break;
		case 16:
			acao16();
			break;
		case 17:
			acao17();
			break;
		case 18:
			acao18(token);
			break;
		case 19:
			acao19(token);
			break;
		case 22:
			acao22(token);
			break;
		case 23:
			acao23();
			break;
		case 24:
			acao24(token);
			break;
		case 25:
			acao25();
			break;
		case 26:
			acao26(token);
			break;
		case 27:
			acao27();
			break;
		case 28:
			acao28();
			break;
		case 29:
			acao29();
			break;
		case 30:
			acao30();
			break;
		case 31:
			acao31(token);
			break;
		case 32:
			acao32();
			break;
		case 33:
			acao33();
			break;
		case 34:
			acao34();
			break;
		default:
			throw new SemanticError("Acao semantica nao implementada: " + action);
		}
	}

	   private void acao1() {
		      String tipo1 = pilhaTipos.pop();
		      String tipo2 = pilhaTipos.pop();
		      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
		         pilhaTipos.push("int64");
		      } else {
		         pilhaTipos.push("float64");
		      }
		      codigoObjeto.append("add\n");
		   }

		   private void acao2() {
		      String tipo1 = pilhaTipos.pop();
		      String tipo2 = pilhaTipos.pop();
		      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
		         pilhaTipos.push("int64");
		      } else {
		         pilhaTipos.push("float64");
		      }
		      codigoObjeto.append("sub\n");
		   }

		   private void acao3() {
		      String tipo1 = pilhaTipos.pop();
		      String tipo2 = pilhaTipos.pop();
		      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
		         pilhaTipos.push("int64");
		      } else {
		         pilhaTipos.push("float64");
		      }
		      codigoObjeto.append("mul\n");
		   }

		   private void acao4() {
		      String tipo1 = pilhaTipos.pop();
		      String tipo2 = pilhaTipos.pop();
		      pilhaTipos.push("float64");
		      codigoObjeto.append("div\n");
		   }

		   private void acao5(Token token) {
		     pilhaTipos.push("int64");
		     codigoObjeto.append("ldc.i8 " + token.getLexeme() + "\n");
		     codigoObjeto.append("conv.r8\n");
		   }

		   private void acao6(Token token) {
		      pilhaTipos.push("float64");
		      codigoObjeto.append("ldc.r8 " + token.getLexeme() + "\n");
		   }
		   
		   private void acao7() {
		      String tipo = pilhaTipos.pop();
		      if ("int64".equals(tipo)) {
		         pilhaTipos.push("int64");
		      } else {
		         pilhaTipos.push("float64");
		      }	  
		   }     

		   private void acao8() {
		      String tipo = pilhaTipos.pop();
		      if ("int64".equals(tipo)) {
		         pilhaTipos.push("int64");
		      } else {
		         pilhaTipos.push("float64");
		      }	
		      codigoObjeto.append("ldc.i8 -1\n");
		      codigoObjeto.append("conv.r8\n");
		      codigoObjeto.append("mul\n");
		   }
			
		   private void acao11() {
		      pilhaTipos.push("bool");
		      codigoObjeto.append("ldc.i4.1\n");
		   }

		   private void acao12() {
		      pilhaTipos.push("bool");
		      codigoObjeto.append("ldc.i4.0\n");
		   }

		   private void acao13() {
		      String tipo = pilhaTipos.pop();
		      pilhaTipos.push("bool");
		      codigoObjeto.append("ldc.i4.1\n");
		      codigoObjeto.append("xor\n");
		   }

		   private void acao14() {
		      String tipo = pilhaTipos.pop();
		      if ("int64".equals(tipo)) {
		         codigoObjeto.append("conv.i8\n");
		      }
		      codigoObjeto.append("call void [mscorlib]System.Console::Write(" + tipo + ")\n");
		    }
			
		   private void acao20() {
		      codigoObjeto.append(".assembly extern mscorlib {}\n");
		      codigoObjeto.append(".assembly _programa{}\n");
		      codigoObjeto.append(".module _programa.exe\n");
		      codigoObjeto.append("\n");
		      codigoObjeto.append(".class public _unica{\n");
		      codigoObjeto.append(".method static public void _principal(){\n");
		      codigoObjeto.append(".entrypoint\n");
		   }

		   private void acao21() {
		      codigoObjeto.append("ret\n");
		      codigoObjeto.append("}\n");
		      codigoObjeto.append("}");
		   }

		   public String getCodigoObjeto() {
		      return codigoObjeto.toString();
		   }

	private void acao9(Token token) {
		pilhaOperadores.push(token.getLexeme());
	}

	private void acao10() {

		String op = pilhaOperadores.pop();

		switch (op) {

		case "==":
			codigoObjeto.append("ceq\n");
			break;

		case "!=":
			codigoObjeto.append("ceq\n");
			codigoObjeto.append("ldc.i4.0\n");
			codigoObjeto.append("ceq\n");
			break;

		case "<":
			codigoObjeto.append("clt\n");
			break;

		case ">":
			codigoObjeto.append("cgt\n");
			break;

		case "<=":
			codigoObjeto.append("cgt\n");
			codigoObjeto.append("ldc.i4.0\n");
			codigoObjeto.append("ceq\n");
			break;

		case ">=":
			codigoObjeto.append("clt\n");
			codigoObjeto.append("ldc.i4.0\n");
			codigoObjeto.append("ceq\n");
			break;
		}

		pilhaTipos.pop();
		pilhaTipos.pop();
		pilhaTipos.push("bool");
	}

	private void acao15() {

		pilhaTipos.pop();
		pilhaTipos.pop();

		pilhaTipos.push("bool");

		codigoObjeto.append("and\n");
	}

	private void acao16() {

		pilhaTipos.pop();
		pilhaTipos.pop();

		pilhaTipos.push("bool");

		codigoObjeto.append("or\n");
	}

	private void acao17() {

		pilhaTipos.pop();
		pilhaTipos.pop();

		pilhaTipos.push("float64");

		codigoObjeto.append("call float64 [mscorlib]System.Math::Pow(float64,float64)\n");
	}

	private void acao18(Token token) {

		pilhaTipos.push("char");

		int valor = token.getLexeme().charAt(1);

		codigoObjeto.append("ldc.i4 " + valor + "\n");
	}

	private void acao19(Token token) {

		pilhaTipos.push("string");

		codigoObjeto.append("ldstr " + token.getLexeme() + "\n");
	}

	private void acao22(Token token) {

		switch (token.getId()) {

		case t_pr_int:
			tipoAtual = "int64";
			break;

		case t_pr_float:
			tipoAtual = "float64";
			break;

		case t_pr_bool:
			tipoAtual = "bool";
			break;

		case t_pr_char:
			tipoAtual = "char";
			break;

		case t_pr_string:
			tipoAtual = "string";
			break;
		}
	}

	private void acao23() {

	    if (!listaIdentificadores.isEmpty()) {

	        for (String id : listaIdentificadores) {
	            tabelaSimbolos.put(id, tipoAtual);
	        }

	        if (!localsDeclarado) {
	            codigoObjeto.append(".locals init (");

	            for (int i = 0; i < listaIdentificadores.size(); i++) {
	                String id = listaIdentificadores.get(i);

	                codigoObjeto.append(tipoAtual)
	                            .append(" ")
	                            .append(id);

	                if (i < listaIdentificadores.size() - 1) {
	                    codigoObjeto.append(", ");
	                }
	            }

	            codigoObjeto.append(")\n");

	            localsDeclarado = true;
	        }

	        listaIdentificadores.clear();
	    }
	}

	private void acao24(Token token) {
		listaIdentificadores.add(token.getLexeme());
	}

	private void acao25() {

	    String tipoExpressao = pilhaTipos.pop();

	    // conversão obrigatória
	    if ("int64".equals(tipoExpressao)) {
	        codigoObjeto.append("conv.i8\n");
	    }

	    int n = listaIdentificadores.size();

	    for (int i = 0; i < n - 1; i++) {
	        codigoObjeto.append("dup\n");
	    }

	    for (String id : listaIdentificadores) {
	        codigoObjeto.append("stloc ")
	                    .append(id)
	                    .append("\n");
	    }

	    listaIdentificadores.clear();
	}
	private void acao26(Token token) throws SemanticError {

	    String id = token.getLexeme();

	    // valida se existe na tabela de símbolos
	    if (!tabelaSimbolos.containsKey(id)) {
	        throw new SemanticError("Variável não declarada: " + id);
	    }

	    String tipo = tabelaSimbolos.get(id);

	    // REGRA OBRIGATÓRIA: bool e char não podem receber entrada
	    if ("bool".equals(tipo) || "char".equals(tipo)) {
	        throw new SemanticError(
	            id + " - identificador inválido para comando de entrada"
	        );
	    }

	    // leitura padrão
	    codigoObjeto.append("call string [mscorlib]System.Console::ReadLine()\n");

	    // conversão conforme tipo
	    if ("int64".equals(tipo)) {
	        codigoObjeto.append(
	            "call int64 [mscorlib]System.Int64::Parse(string)\n"
	        );
	    } 
	    else if ("float64".equals(tipo)) {
	        codigoObjeto.append(
	            "call float64 [mscorlib]System.Double::Parse(string)\n"
	        );
	    }

	    // armazenamento
	    codigoObjeto.append("stloc ")
	                .append(id)
	                .append("\n");
	}

	private void acao27() {

		String falso = novoRotulo();

		pilhaRotulos.push(falso);

		codigoObjeto.append("brfalse " + falso + "\n");
	}

	private void acao28() {

		String fim = novoRotulo();

		codigoObjeto.append("br " + fim + "\n");

		codigoObjeto.append(pilhaRotulos.pop() + ":\n");

		pilhaRotulos.push(fim);
	}

	private void acao29() {

		codigoObjeto.append(pilhaRotulos.pop() + ":\n");
	}

	private void acao30() {

		String falso = novoRotulo();

		pilhaRotulos.push(falso);

		codigoObjeto.append("brfalse " + falso + "\n");
	}

	private void acao31(Token token) {

	    String id = token.getLexeme();

	    String tipo = tabelaSimbolos.get(id);

	    pilhaTipos.push(tipo);

	    codigoObjeto.append("ldloc ")
	                .append(id)
	                .append("\n");

	    if ("int64".equals(tipo)) {
	        codigoObjeto.append("conv.r8\n");
	    }
	}
	
	private void acao32() {

		String inicio = novoRotulo();

		pilhaRotulos.push(inicio);

		codigoObjeto.append(inicio + ":\n");
	}

	private void acao33() {
	    pilhaTipos.pop();

	    String inicio = pilhaRotulos.peek(); // NÃO remove

	    codigoObjeto.append("brtrue ")
	            .append(inicio)
	            .append("\n");
	}

	private void acao34() {

	    pilhaTipos.pop();

	    String rotulo = pilhaRotulos.pop();

	    codigoObjeto.append("brfalse ")
	                .append(rotulo)
	                .append("\n");
	}
}