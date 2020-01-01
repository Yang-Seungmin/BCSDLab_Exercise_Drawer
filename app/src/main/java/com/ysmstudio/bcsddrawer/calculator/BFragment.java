package com.ysmstudio.bcsddrawer.calculator;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ysmstudio.bcsddrawer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends Fragment implements CalculatorView{

    private Unbinder unbinder;

    @BindView(R.id.edit_text_calc) EditText editText;

    private CalculatorPresenter calculatorPresenter;

    public BFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        unbinder = ButterKnife.bind(this, view);
        editText.setTextIsSelectable(true);
        calculatorPresenter = new CalculatorPresenter(this);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.button_opening_brace, R.id.button_closing_brace,
            R.id.button_add, R.id.button_sub, R.id.button_mul, R.id.button_div, R.id.button_dot,
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9})
    void appendText(View v) {
        editText.setText(editText.getText().toString() + ((Button) v).getText());
    }

    @OnClick(R.id.button_equal)
    void calc() {
        calculatorPresenter.calculate(editText.getText().toString());
    }

    @Override
    public void showCalculatedResult(String result) {
        editText.setText(result);
    }

    @Override
    @OnClick(R.id.button_clear)
    @OnLongClick(R.id.button_backspace)
    public void clearInput() {
        editText.setText("");
    }

    @Override
    @OnClick(R.id.button_backspace)
    public void backspace() {
        String expr = editText.getText().toString();
        if (editText.getText().toString().length() > 0)
            editText.setText(expr.substring(0, expr.length() - 1));
    }
}