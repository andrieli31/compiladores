package interfaceCompilador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import interfaceCompilador.NumberedBorder;

public class Interface extends JFrame {
	 /// ==============================
    /// PONTOS DO TRABALHO JA FEITOS
    /// ==============================
		///  1
		///  2,
		///  3,
		///  4,
		///  5,
		///  6,
		///  7,
		///  8,
		///  9 (parcial,falta implementar os atalhos),
		///  11
	
	
	
	
    private JPanel contentPane;
    private JTextArea editor;
    private JTextArea mensagens;
    private JLabel status;

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

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);

        // ==============================
        // Barra de Ferramentas
        // ==============================
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(1500, 60));
        contentPane.add(toolBar, BorderLayout.NORTH);

        
        Dimension tamanhoBotao = new Dimension(140,50);
        
        

        // ==============================
        // Botões da barra de ferramentas
        // ==============================
        
        JButton btnNovo = criarBotao("novo [ctrl-n]", "/icons/page_white_add.png", tamanhoBotao);
        JButton btnAbrir = criarBotao("abrir [ctrl-o]", "/icons/add.png", tamanhoBotao);
        JButton btnSalvar = criarBotao("salvar [ctrl-s]", "/icons/page_save.png", tamanhoBotao);

        JButton btnCopiar = criarBotao("copiar ", "/icons/page_copy.png", tamanhoBotao);
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

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollEditor,
                scrollMensagens
        );

        splitPane.setResizeWeight(0.8); 
        splitPane.setDividerLocation(550);
        contentPane.add(splitPane, BorderLayout.CENTER);

        // ==============================
        // BARRA DE STATUS
        // ==============================
        status = new JLabel(" Pronto");
        status.setPreferredSize(new Dimension(1500, 25));
        contentPane.add(status, BorderLayout.SOUTH);
    }
    
    
    
    
    private JButton criarBotao(String texto, String caminhoIcone, Dimension tamanho) {
    	JButton botao = new JButton(texto);
    	
    	//try {
    		botao.setIcon(new ImageIcon(getClass().getResource(caminhoIcone)));
    	//} catch (Exception e) {
    	//	/// tratar caso não tenha icone ainda
    	//}
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
            status.setText(arquivo.getAbsolutePath());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao abrir arquivo",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }  
    
}