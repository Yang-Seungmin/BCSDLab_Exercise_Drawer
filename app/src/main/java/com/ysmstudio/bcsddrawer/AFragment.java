package com.ysmstudio.bcsddrawer;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {

    private EditText editText;
    private NameRecyclerAdapter adapter = new NameRecyclerAdapter();

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(editText.getText().toString().length() > 0) {
                if(adapter != null) {
                    adapter.addArrayListItem(editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        }
    };

    public AFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        editText = view.findViewById(R.id.edit_text_name);
        Button button = view.findViewById(R.id.button_add);

        button.setOnClickListener(buttonClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_names);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

}
