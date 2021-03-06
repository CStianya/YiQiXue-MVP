package com.cs.yiqixue_mvp.mvp.home.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cs.yiqixue_mvp.R;
import com.cs.yiqixue_mvp.base.BaseFragment;
import com.cs.yiqixue_mvp.api.bean.Question;
import com.cs.yiqixue_mvp.mvp.home.addquestion.AddQuestionActivity;
import com.cs.yiqixue_mvp.utils.LogUtil;

import java.util.List;

/**
 * Created by CSLaker on 2017/3/24.
 */

public class QuestionFragment extends BaseFragment implements QuestionContract.View {

    private static QuestionFragment fragment;
    private QuestionContract.Presenter mPresenter;
    private QuestionAdapter mQuestionAdapter;
    private List<Question> mQuestionList;

    private RecyclerView mRecyclerView;
    private ImageButton mAddQuestion;
    private SwipeRefreshLayout mSwipeRefresh;

    public QuestionFragment(){}

    public static QuestionFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        if (fragment == null) {
            fragment = new QuestionFragment();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle parms) {
        if (mQuestionList == null) {
            mQuestionList = mPresenter.initQuestionData();
        }
        if (mQuestionAdapter == null) {
            mQuestionAdapter = new QuestionAdapter(mQuestionList);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.question_frag;
    }

    @Override
    public void initView(View view) {
        mAddQuestion = (ImageButton) view.findViewById(R.id.fab_new_question);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mQuestionAdapter);

        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void setListener() {
        mAddQuestion.setOnClickListener(this);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshQuestionData();
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.fab_new_question: {
                Intent intent = new Intent(getActivity(), AddQuestionActivity.class);
                startActivity(intent);
                break;
            }
            default:break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void setPresenter(QuestionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setQuestionList(List<Question> questionList) {
        mQuestionList = questionList;
    }

    @Override
    public void showRefreshedData() {
        mQuestionAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
        mSwipeRefresh.setRefreshing(false);
    }

}