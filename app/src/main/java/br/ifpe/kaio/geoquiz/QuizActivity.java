package br.ifpe.kaio.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private Button mBtnVerdadeiro;
    private Button mBtnFalso;
    private ImageButton mBtnAnterior;
    private ImageButton mBtnProximo;

    private TextView mQuestoesTextView;

    private Questoes[] mBancoPerguntas = new Questoes[] {
            new Questoes(R.string.pergunta1, true),
            new Questoes(R.string.pergunta2, true),
            new Questoes(R.string.pergunta3, false),
            new Questoes(R.string.pergunta4, true),
            new Questoes(R.string.pergunta5, false),
            new Questoes(R.string.pergunta6, true),
            new Questoes(R.string.pergunta7, true),
            new Questoes(R.string.pergunta8, true),
            new Questoes(R.string.pergunta9, false),
            new Questoes(R.string.pergunta10, false)
    };

    int mCurrentIndex = 0;
    int mPontuacao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        embaralharPerguntas();
        mQuestoesTextView = (TextView) findViewById(R.id.pergunta_text_view);

        mBtnVerdadeiro = (Button) findViewById(R.id.btn_true);
        mBtnFalso = (Button) findViewById(R.id.btn_false);
        mBtnAnterior = (ImageButton) findViewById(R.id.btn_anterior);
        mBtnProximo = (ImageButton) findViewById(R.id.btn_proximo);

        mQuestoesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBancoPerguntas.length != 0) {
                    mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
                    atualizarPergunta();
                }
            }
        });

        mBtnVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarResposta(true);
                removerPerguntas();
                mostrarPontuacao();
            }
        });

        mBtnFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarResposta(false);
                removerPerguntas();
                mostrarPontuacao();
            }
        });


        mBtnProximo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
                atualizarPergunta();
            }
        });

        mBtnAnterior.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mCurrentIndex != 0) {
                    mCurrentIndex--;
                }
                atualizarPergunta();
            }
        });
    }

    private void atualizarPergunta() {
        int questao = mBancoPerguntas[mCurrentIndex].getTextoResId();
        mQuestoesTextView.setText(questao);
    }

    private void checarResposta(boolean resultadoUsuario) {
        boolean resposta = mBancoPerguntas[mCurrentIndex].isResposta();
        int mensagemResId;
        //int mensagemResId = (resposta == resultadoUsuario) ? R.string.correto_toast : R.string.incorreto_toast;
        if(resposta == resultadoUsuario) {
            mensagemResId = R.string.correto_toast;
            mPontuacao++;
        } else {
            mensagemResId = R.string.incorreto_toast;
        }
        Toast.makeText(this, mensagemResId, Toast.LENGTH_SHORT).show();
    }

    private void mostrarPontuacao() {
        if(mBancoPerguntas.length == 0) {
            String mensagem = "Sua pontuação foi "+ mPontuacao;
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
            desabilitaBotoes();
        }
    }

    private void desabilitaBotoes() {
        mBtnVerdadeiro.setEnabled(false);
        mBtnFalso.setEnabled(false);
        mBtnAnterior.setEnabled(false);
        mBtnProximo.setEnabled(false);
    }

    private void embaralharPerguntas() {
        for(int i=0; i<(mBancoPerguntas.length - 1); i++) {
            //Recebe um index aleatorio
            int j = new Random().nextInt(mBancoPerguntas.length);

            //Troca o conteudo das questões
            Questoes temporario = mBancoPerguntas[i];
            mBancoPerguntas[i] = mBancoPerguntas[j];
            mBancoPerguntas[j] = temporario;
        }
    }

    private void removerPerguntas() {
        if(mBancoPerguntas.length != 0) {
            Questoes[] arrAuxiliar = new Questoes[mBancoPerguntas.length - 1];
            int index = 0;

            for (int i = 0; i<mBancoPerguntas.length; i++) {
                if (i != mCurrentIndex) {
                    arrAuxiliar[index] = mBancoPerguntas[i];
                    index++;
                }
            }
            mBancoPerguntas = arrAuxiliar;
        }
    }

}
