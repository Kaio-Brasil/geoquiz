package br.ifpe.kaio.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mBtnVerdadeiro;
    private Button mBtnFalso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mBtnVerdadeiro = (Button) findViewById(R.id.btn_true);
        mBtnFalso = (Button) findViewById(R.id.btn_false);

        mBtnVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, R.string.correto_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mBtnFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, R.string.incorreto_toast,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
