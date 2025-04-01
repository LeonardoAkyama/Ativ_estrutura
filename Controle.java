package jogoMemorizacao;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Controle {
    
    private String jogador;
    private int pontuacao = 0;
    private int rodada;
    private String dificuldade;
    private Tela telaPrincipal;
    private JButton[] listaBotoes;
    private JButton botaoIniciar;    
    private boolean sequenciaAutomaticaIniciada;
    private boolean rodadaIniciada = false;
    private boolean jogoIniciado;
    private int qtdSequencia = 3;
    private JButton[] sequenciaBotao;
    private boolean sequenciaAutomatica = false;
    private int indiceUsuario = 0;
    private int delay = 500;
    private List<String> rankingJogadores = new ArrayList<>();
    
    public Controle(Tela telaPrincipal) {        
        this.telaPrincipal=telaPrincipal;
        this.listaBotoes=telaPrincipal.obterInstanciaBotoesAcao();
        this.botaoIniciar=telaPrincipal.obterInstaciaBotaoIniciar();
        
        botaoIniciar.addActionListener(e -> iniciarNovoJogo(jogador, dificuldade));
    }
    public void iniciarNovoJogo(String jogador, String dificuldade){
        this.dificuldade= dificuldade;
        this.jogador = jogador;
        this.pontuacao = 0;
        this.rodada = 0;
        this.qtdSequencia = 3;
        JOptionPane.showMessageDialog(null, "novo jogo iniciado");
        iniciarRodada(qtdSequencia);
    }
    public void iniciarRodada(int qtdSequencia){
        rodadaIniciada = true;
        sequenciaAutomatica = true;
        indiceUsuario = 0;
        sequenciaBotao = gerarSequenciaBotoes(gerarSequenciaNumerica(qtdSequencia));
        botaoIniciar.setEnabled(false);
        pintarBotoesSequencia(sequenciaBotao);
        telaPrincipal.atualizarRodada(String.valueOf(rodada));
        

    }
    public void acaoClick(java.awt.event.MouseEvent evt){
        if(sequenciaAutomatica || !rodadaIniciada){
           return;
        }
        JButton botaoClicado = (JButton) evt.getSource();
        PintarBotoes.piscarBotoes(botaoClicado, delay, this::callbackAcaoClick);
        
        if(validaSequencia(botaoClicado)){
            indiceUsuario++;
            if(indiceUsuario == sequenciaBotao.length){
                sequenciaValida();
            }
        }else{
           sequenciaInvalida();
        }
    }    
    private void callbackAcaoClick(){
        
    }
    private void pintarBotoesSequencia(JButton[] sequencia){
        PintarBotoes.piscarBotoes(sequencia, delay, this::callbackPintarBotoesSequencia);
    }
    private void callbackPintarBotoesSequencia(){
        sequenciaAutomatica = false;
        JOptionPane.showMessageDialog(null, "Agora é sua vez!");
    }
    private boolean validaSequencia(JButton botaoClicado){
        return sequenciaBotao != null && botaoClicado.equals(sequenciaBotao[indiceUsuario]);

    }
    private void sequenciaValida(){
       pontuacao++;
       JOptionPane.showMessageDialog(null, "Acertou a sequencia  Pontuacao: " + pontuacao);
       telaPrincipal.atualizarPontuacao(String.valueOf(pontuacao));
       qtdSequencia++;
       iniciarRodada(qtdSequencia);
    }
    private void sequenciaInvalida(){
        JOptionPane.showMessageDialog(null, "GameOver: Pontuacao final: " +pontuacao);
        rodadaIniciada = false;
        sequenciaAutomatica = false;
        pontuacao = 0;
        botaoIniciar.setEnabled(true);
        telaPrincipal.atualizarPontuacao("0");
        telaPrincipal.atualizarRodada("0");
        rankingJogadores.add(jogador);
        telaPrincipal.atualizarRanking(new String[]{jogador});
        
    }
    
    private JButton[] gerarSequenciaBotoes(int[] sequenciaNumerica){
        JButton[] sequenciaBotao = new JButton[sequenciaNumerica.length]; 
        for(int i = 0; i<sequenciaNumerica.length; i++){
            sequenciaBotao[i] = listaBotoes[sequenciaNumerica[i]];
        }
        return sequenciaBotao;
    }
    
    private int[] gerarSequenciaNumerica(int qtdSequencias){
        Random aleatorio = new Random();
        int[] sequencia = new int[qtdSequencias];
        for(int i = 0; i<qtdSequencias; i++){
            sequencia[i] = aleatorio.nextInt(listaBotoes.length);
        }
        return sequencia;
    }
    
    
    
    
    
}
