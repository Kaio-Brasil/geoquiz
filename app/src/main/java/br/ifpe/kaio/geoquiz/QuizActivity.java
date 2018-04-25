package br.ifpe.kaio.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mBtnVerdadeiro;
    private Button mBtnFalso;
    private Button mBtnAvancar;

    private TextView mQuestoesTextView;

    private Questoes[] mBancoPerguntas = new Questoes[] {
            new Questoes(R.string.pergunta1, true),
            new Questoes(R.string.pergunta2, true),
            new Questoes(R.string.pergunta3, false),
            new Questoes(R.string.pergunta4, true),
            new Questoes(R.string.pergunta5, false),
    };

    int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestoesTextView = (TextView) findViewById(R.id.pergunta_text_view);

        int questao = mBancoPerguntas[mCurrentIndex].getTextoResId();
        mQuestoesTextView.setText(questao);

        mBtnVerdadeiro = (Button) findViewById(R.id.btn_true);
        mBtnFalso = (Button) findViewById(R.id.btn_false);
        mBtnAvancar = (Button) findViewById(R.id.btn_proximo);

        mBtnVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarResposta(true);
            }
        });

        mBtnFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarResposta(true);
            }
        });


        mBtnAvancar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
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
        int messagemResId = (resposta == resultadoUsuario) ? R.string.correto_toast : R.string.incorreto_toast;
        Toast.makeText(this, messagemResId, Toast.LENGTH_SHORT).show();
    }

}
