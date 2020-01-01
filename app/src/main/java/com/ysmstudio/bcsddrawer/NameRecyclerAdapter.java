package com.ysmstudio.bcsddrawer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NameRecyclerAdapter extends RecyclerView.Adapter<NameRecyclerAdapter.ViewHolder> {

    private ArrayList<String> arrayList;

    public NameRecyclerAdapter() {
        arrayList = new ArrayList<>();
    }

    public NameRecyclerAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public String getArrayListItem(int position) {
        return arrayList.get(position);
    }

    public void addArrayListItem(String item) {
        arrayList.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NameRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameRecyclerAdapter.ViewHolder holder, int position) {
        holder.textView.setText(getArrayListItem(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_text_view_name) TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
