
package jogoMemorizacao;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author diver
 */
public class PintarBotoes {
 
  public static void piscarBotoes(JButton botao, int delayMillis, Runnable callback){
      piscarBotoes(new JButton[]{botao}, delayMillis, callback);
  }
    
 /**
 * Método que faz os botões piscarem em sequência, alterando entre as cores vermelha e branca
 * @param botoes vetor de JButton que serão destacados
 * @param delayMillis tempo em milissegundos entre cada operação
 * @param callback interface para executar uma ação quando a animação terminar
 */
    public static void piscarBotoes(JButton[] botoes, int delayMillis, Runnable callback) {
        // Criar uma tarefa em uma thread separada para não bloquear a interface gráfica
        new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Percorre o vetor de botões
                for (int i = 0; i < botoes.length; i++) {
                    
                    // Publica o índice atual para o método process executar as alterações visuais
                    publish(i);

                    // Aguarda pelo tempo definido entre cada passo
                    Thread.sleep(delayMillis);

                    // Aguarda o mesmo tempo para o botão ficar vermelho antes de voltar ao branco
                    Thread.sleep(delayMillis);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> indices) {
                // Obtém o índice mais recente
                int index = indices.get(indices.size() - 1);
                JButton botaoAtual = botoes[index];

                // Altera a cor do botão para vermelho
                botaoAtual.setBackground(Color.RED);

                // Programa a alteração de volta para branco após o delay
                Timer timer = new Timer(delayMillis, e -> {
                    botaoAtual.setBackground(Color.WHITE);
                    ((Timer) e.getSource()).stop(); // Para o timer após executar uma vez
                });
                timer.setRepeats(false);
                timer.start();
            }

            @Override
            protected void done() {
                // Quando terminar, executa o callback
                if (callback != null) {
                    callback.run();
                }
            }
        }.execute();
    }

}
