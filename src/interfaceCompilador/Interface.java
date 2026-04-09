package interfaceCompilador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import compilador.Compilador;
import interfaceCompilador.NumberedBorder;
import utils.gals.LexicalError;
import utils.gals.Lexico;
import utils.gals.ScannerConstants;
import utils.gals.Token;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextArea editor;
	private JTextArea mensagens;
	private JLabel status;
	private File arquivoAtual;

	
	
	//TODO: Revisar código da classe Compilador.java, verificar as entradas e saídas no analisador
	//se estão ok com o trabalho 2 e revisar o arquivo especificacoes_v2.gals na parte dos tokens (usar como base a correção do trabalho N1 feito na sala)
	//Boa sorte best
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Interface frame = new Interface();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Interface() {

		setTitle("Compilador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 800);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// ==============================
		// Barra de Ferramentas
		// ==============================
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setPreferredSize(new Dimension(1500, 60));
		contentPane.add(toolBar, BorderLayout.NORTH);

		Dimension tamanhoBotao = new Dimension(140, 50);

		// ==============================
		// Botões da barra de ferramentas
		// ==============================

		JButton btnNovo = criarBotao("novo [ctrl-n]", "/icons/page_white_add.png", tamanhoBotao);
		JButton btnAbrir = criarBotao("abrir [ctrl-o]", "/icons/add.png", tamanhoBotao);
		JButton btnSalvar = criarBotao("salvar [ctrl-s]", "/icons/page_save.png", tamanhoBotao);

		JButton btnCopiar = criarBotao("copiar [ctrl-c]", "/icons/page_copy.png", tamanhoBotao);
		JButton btnColar = criarBotao("colar [ctrl-v]", "/icons/page_paste.png", tamanhoBotao);
		JButton btnRecortar = criarBotao("recortar [ctrl-x]", "/icons/cut.png", tamanhoBotao);

		JButton btnCompilar = criarBotao("compilar [F7]", "/icons/control_play.png", tamanhoBotao);
		JButton btnEquipe = criarBotao("equipe [F1]", "/icons/user_add.png", tamanhoBotao);

		toolBar.add(btnNovo);
		toolBar.add(btnAbrir);
		toolBar.add(btnSalvar);
		toolBar.addSeparator();

		toolBar.add(btnCopiar);
		toolBar.add(btnColar);
		toolBar.add(btnRecortar);
		toolBar.addSeparator();

		toolBar.add(btnCompilar);
		toolBar.addSeparator();

		toolBar.add(btnEquipe);

		/// FUNÇÕES DE ABERTURA DOS BOTÕES
		btnAbrir.addActionListener(e -> abrirArquivo());
		btnNovo.addActionListener(e -> novoArquivo());
		btnSalvar.addActionListener(e -> salvarArquivo());

		btnCopiar.addActionListener(e -> editor.copy());
		btnColar.addActionListener(e -> editor.paste());
		btnRecortar.addActionListener(e -> editor.cut());

		btnCompilar.addActionListener(e -> compilar());
		btnEquipe.addActionListener(e -> mostrarEquipe());

		editor = new JTextArea();
		editor.setBorder(new NumberedBorder());

		editor.setLineWrap(false);

		JScrollPane scrollEditor = new JScrollPane(editor);
		scrollEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// ==============================
		// ÁREA DE MENSAGENS
		// ==============================
		mensagens = new JTextArea();
		mensagens.setEditable(false);
		JScrollPane scrollMensagens = new JScrollPane(mensagens);
		mensagens.setEditable(false);
		mensagens.setLineWrap(false);

		scrollMensagens.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMensagens.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, scrollMensagens);

		splitPane.setResizeWeight(0.8);
		splitPane.setDividerLocation(550);
		contentPane.add(splitPane, BorderLayout.CENTER);

		// ==============================
		// BARRA DE STATUS
		// ==============================
		status = new JLabel(" Pronto");
		status.setPreferredSize(new Dimension(1500, 25));
		contentPane.add(status, BorderLayout.SOUTH);

		configurarAtalhos();

	}

	private JButton criarBotao(String texto, String caminhoIcone, Dimension tamanho) {
		JButton botao = new JButton(texto);

		// try {
		botao.setIcon(new ImageIcon(getClass().getResource(caminhoIcone)));
		// } catch (Exception e) {
		// /// tratar caso não tenha icone ainda
		// }
		botao.setHorizontalTextPosition(SwingConstants.CENTER);
		botao.setVerticalTextPosition(SwingConstants.BOTTOM);

		botao.setPreferredSize(tamanho);
		botao.setFocusable(false);
		return botao;
	}

	/// Método relacionado a abertura de arquivo no botão da barra superior

	private void abrirArquivo() {

		JFileChooser chooser = new JFileChooser();

		// Filtro apenas .txt
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos Texto (*.txt)", "txt");
		chooser.setFileFilter(filtro);

		int resultado = chooser.showOpenDialog(this);

		// Se usuário cancelar → NÃO faz nada
		if (resultado != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File arquivo = chooser.getSelectedFile();

		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

			editor.read(br, null);
			mensagens.setText("");
			status.setText(" " + arquivo.getAbsolutePath());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao abrir arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void novoArquivo() {
		editor.setText("");
		mensagens.setText("");
		status.setText("");
		arquivoAtual = null;
	}

	private void configurarAtalhos() {

		// CTRL + N (novo)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control N"), "novo");

		getRootPane().getActionMap().put("novo", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				novoArquivo();
			}
		});

		// CTRL + O (abrir)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control O"), "abrir");

		getRootPane().getActionMap().put("abrir", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				abrirArquivo();
			}
		});

		// CTRL + S (salvar)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control S"), "salvar");

		getRootPane().getActionMap().put("salvar", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				salvarArquivo();
			}
		});

		// CTRL + C (copiar)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control C"), "copiar");

		getRootPane().getActionMap().put("copiar", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				editor.copy();
			}
		});

		// CTRL + V (colar)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control V"), "colar");

		getRootPane().getActionMap().put("colar", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				editor.paste();
			}
		});

		// CTRL + X (recortar)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control X"), "recortar");

		getRootPane().getActionMap().put("recortar", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				editor.cut();
			}
		});

		// F7 (compilar)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F7"), "compilar");

		getRootPane().getActionMap().put("compilar", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				compilar();
			}
		});

		// F1 (equipe)
		getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "equipe");

		getRootPane().getActionMap().put("equipe", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				mostrarEquipe();
			}
		});
	}

	private void mostrarEquipe() {
		mensagens.setText("Equipe de desenvolvimento:\n\n" + "Andrieli Mendes\n" + "Patrícia Oliveira Cordeiro\n");
	}

	
	
	
	/// Método responsável pela compilação
	private void compilar() {
		mensagens.setText("");
		Compilador compilador = new Compilador();
		String resultado = compilador.compilar(editor.getText());

		mensagens.setText(resultado);
	}

	private void salvarArquivo() {

		try {

			// se ainda não existe arquivo
			if (arquivoAtual == null) {

				JFileChooser chooser = new JFileChooser();

				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos Texto (*.txt)", "txt");

				chooser.setFileFilter(filtro);

				int resultado = chooser.showSaveDialog(this);

				if (resultado != JFileChooser.APPROVE_OPTION) {
					return;
				}

				arquivoAtual = chooser.getSelectedFile();
			}

			java.io.FileWriter writer = new java.io.FileWriter(arquivoAtual);

			editor.write(writer);

			writer.close();

			mensagens.setText("");
			status.setText(" " + arquivoAtual.getAbsolutePath());

		} catch (IOException e) {

			JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}