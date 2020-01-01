package com.ysmstudio.bcsddrawer;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.edit_text_name) EditText editText;
    @BindView(R.id.recycler_view_names) RecyclerView recyclerView;

    private NameRecyclerAdapter adapter = new NameRecyclerAdapter();

    @OnClick(R.id.button_add)
    void buttonOnClick() {
        if(editText.getText().toString().length() > 0) {
            if(adapter != null) {
                adapter.addArrayListItem(editText.getText().toString());
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        }
    }

    public AFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        unbinder = ButterKnife.bind(this,view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
