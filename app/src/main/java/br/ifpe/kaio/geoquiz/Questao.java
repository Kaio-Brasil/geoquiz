package br.ifpe.kaio.geoquiz;

/**
 * Created by Kaio on 25/04/2018.
 */

public class Questao {
    private int mTextoResId;
    private boolean mResposta;

    public Questao(int mTextoResId, boolean mResposta) {
        this.mTextoResId = mTextoResId;
        this.mResposta = mResposta;
    }

    public int getTextoResId() {
        return mTextoResId;
    }

    public void setTextoResId(int mTextoResId) {
        this.mTextoResId = mTextoResId;
    }

    public boolean isResposta() {
        return mResposta;
    }

    public void setResposta(boolean mResposta) {
        this.mResposta = mResposta;
    }

}
