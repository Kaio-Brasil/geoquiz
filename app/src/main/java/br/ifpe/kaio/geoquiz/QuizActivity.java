package br.ifpe.kaio.geoquiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int REQUEST_CHEAT_CODE = 0;

    private Button mBtnVerdadeiro;
    private Button mBtnFalso;
    private ImageButton mBtnAnterior;
    private ImageButton mBtnProximo;
    private Button mBtnCheat;

    private TextView mPerguntasQuantidade;
    private TextView mQuestoesTextView;

    private Questao[] mBancoPerguntas = new Questao[] {
            new Questao(R.string.pergunta1, true),
            new Questao(R.string.pergunta2, true),
            new Questao(R.string.pergunta3, false),
            new Questao(R.string.pergunta4, true),
            new Questao(R.string.pergunta5, false),
            new Questao(R.string.pergunta6, true),
            new Questao(R.string.pergunta7, true),
            new Questao(R.string.pergunta8, true),
            new Questao(R.string.pergunta9, false),
            new Questao(R.string.pergunta10, false)
    };

    private boolean mCheater;

    private int mCurrentIndex = 0;
    private int mPontuacao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Collections.shuffle(Arrays.asList(mBancoPerguntas));
        mPerguntasQuantidade = (TextView) findViewById(R.id.pergunta_quantidade);

        mQuestoesTextView = (TextView) findViewById(R.id.pergunta_text_view);
        mQuestoesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBancoPerguntas.length != 0) {
                    mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
                    atualizarPergunta();
                }
            }
        });

        mBtnVerdadeiro = (Button) findViewById(R.id.btn_true);
        mBtnVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarResposta(true);
                removerPerguntas();
                if(mBancoPerguntas.length != 0) {
                    mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
                    atualizarPergunta();
                }
                mostrarPontuacao();
            }
        });

        mBtnFalso = (Button) findViewById(R.id.btn_false);
        mBtnFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarResposta(false);
                removerPerguntas();
                if(mBancoPerguntas.length != 0) {
                    mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
                    atualizarPergunta();
                }
                mostrarPontuacao();
            }
        });

        mBtnProximo = (ImageButton) findViewById(R.id.btn_proximo);
        mBtnProximo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mBancoPerguntas.length;
                mCheater = false;
                atualizarPergunta();
            }
        });

        mBtnAnterior = (ImageButton) findViewById(R.id.btn_anterior);
        mBtnAnterior.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mCurrentIndex == 0) {
                    mCurrentIndex = mBancoPerguntas.length - 1;
                } else {
                    mCurrentIndex--;
                }
                mCheater = false;
                atualizarPergunta();
            }
        });

        mBtnCheat = (Button) findViewById(R.id.btn_cheat);
        mBtnCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean resposta = mBancoPerguntas[mCurrentIndex].isResposta();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, resposta);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
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

        if(mCheater) {
            mensagemResId = R.string.jugamento_toast;
            mPontuacao++;
        } else {
            if (resposta == resultadoUsuario) {
                mensagemResId = R.string.correto_toast;
                mPontuacao++;
            } else {
                mensagemResId = R.string.incorreto_toast;
            }
        }
        Toast.makeText(this, mensagemResId, Toast.LENGTH_SHORT).show();
    }

    private void mostrarPontuacao() {
        if(mBancoPerguntas.length == 0) {
            AlertDialog alerta = new AlertDialog.Builder(this).create();
            alerta.setTitle(R.string.pontuacao);
            alerta.setMessage("Você acertou "+ mPontuacao +" perguntas.");
            alerta.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            alerta.show();
        }
    }

    private void removerPerguntas() {
        if(mBancoPerguntas.length != 0) {
            Questao[] arrAuxiliar = new Questao[mBancoPerguntas.length - 1];
            int index = 0;

            for (int i = 0; i<mBancoPerguntas.length; i++) {
                if (i != mCurrentIndex) {
                    arrAuxiliar[index] = mBancoPerguntas[i];
                    index++;
                }
            }
            mBancoPerguntas = arrAuxiliar;
        }
        mPerguntasQuantidade.setText(String.valueOf(mBancoPerguntas.length));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CHEAT_CODE) {
            if(dados == null) {
                return;
            }
            mCheater = CheatActivity.eRespostaMostrada(dados);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
        atualizarPergunta();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
