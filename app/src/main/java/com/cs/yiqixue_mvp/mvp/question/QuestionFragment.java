package com.cs.yiqixue_mvp.mvp.question;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cs.yiqixue_mvp.R;
import com.cs.yiqixue_mvp.bean.Question;

import java.util.List;

/**
 * Created by CSLaker on 2017/3/24.
 */

public class QuestionFragment extends Fragment implements View.OnClickListener, QuestionContrat.View {

    private static QuestionFragment fragment;
    private QuestionContrat.Presenter mPresenter;
    private List<Question> mQuestionList;
    private QuestionAdapter mQuestionAdapter;

    private RecyclerView mRecyclerView;
    private ImageButton mNewQuestion;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mQuestionList == null) {
            mQuestionList = mPresenter.initQuestionData();
        }
        if (mQuestionAdapter == null) {
            mQuestionAdapter = new QuestionAdapter(mQuestionList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_frag, container, false);
        initView(view);
        setListener();
        return view;
    }

    public void initView(View view) {
        mNewQuestion = (ImageButton) view.findViewById(R.id.fab_new_question);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mQuestionAdapter);

        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    public void setListener() {
        mNewQuestion.setOnClickListener(this);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshQuestionData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_new_question: {
            }
        }
    }

    public void setPresenter(QuestionContrat.Presenter presenter) {
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