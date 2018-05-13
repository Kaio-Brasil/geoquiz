package br.ifpe.kaio.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private static final String EXTRA_PERGUNTA_VERDADEIRA = "br.ifpe.kaio.geoquiz.pergunta_verdadeira";
    private static final String EXTRA_ANSWER_SHOWN = "br.ifpe.kaio.geoquiz.mostrando_resposta";

    private boolean mPerguntaVerdadeira;

    private TextView mRespostaTextView;
    private Button mBtnMostrarResposta;

    private void setMostrandoResultadoResposta(boolean eMostrandaResposta) {
        Intent dados = new Intent();
        dados.putExtra(EXTRA_ANSWER_SHOWN, eMostrandaResposta);
        Log.d(TAG, "setMostrandoResultadoResposta: "+eMostrandaResposta);
        setResult(RESULT_OK, dados);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_cheat);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPerguntaVerdadeira = getIntent().getBooleanExtra(EXTRA_PERGUNTA_VERDADEIRA,false);

        mRespostaTextView = (TextView) findViewById(R.id.resposta);

        mBtnMostrarResposta = (Button) findViewById(R.id.btn_mostrar_resposta);
        mBtnMostrarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPerguntaVerdadeira) {
                    mRespostaTextView.setText(R.string.btn_verdadeiro);
                } else {
                    mRespostaTextView.setText(R.string.btn_falso);
                }
                setMostrandoResultadoResposta(true);
            }
        });

    }

    public static boolean eRespostaMostrada(Intent resultado) {
        return resultado.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static Intent newIntent(Context packageContext, boolean perguntaVerdadeira) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_PERGUNTA_VERDADEIRA, perguntaVerdadeira);
        return intent;
    }

}
