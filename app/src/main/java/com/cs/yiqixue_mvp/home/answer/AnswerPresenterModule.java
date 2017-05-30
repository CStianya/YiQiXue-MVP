package com.cs.yiqixue_mvp.home.answer;

import com.cs.yiqixue_mvp.home.comment.CommentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CS on 2017/5/30.
 */

@Module
public class AnswerPresenterModule {

    private final AnswerContract.View mView;

    public AnswerPresenterModule(AnswerContract.View view) {
        mView = view;
    }

    @Provides
    AnswerContract.View provideAnswerContractView() {
        return mView;
    }
}
