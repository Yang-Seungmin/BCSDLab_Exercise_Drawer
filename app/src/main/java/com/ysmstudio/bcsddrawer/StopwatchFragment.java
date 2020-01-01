package com.ysmstudio.bcsddrawer;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment {

    private Unbinder unbinder;

    private LinkedList<String> recordArrayList = new LinkedList<>();
    private ArrayAdapter<String> arrayAdapter;

    private StopwatchTask stopwatchTask;
    private boolean isStopwatchRunning = false;
    private boolean executed = false;

    @BindView(R.id.text_view_stopwatch) TextView textViewStopwatch;
    @BindView(R.id.list_view_records) ListView listViewRecords;

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        unbinder = ButterKnife.bind(this, view);
        stopwatchTask = new StopwatchTask();

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, recordArrayList);
        listViewRecords.setAdapter(arrayAdapter);
        return view;
    }

    @OnClick(R.id.button_start)
    void start() {
        isStopwatchRunning = true;
        if (!executed) {
            stopwatchTask = new StopwatchTask();
            stopwatchTask.execute();
            executed = true;
        }
    }

    @OnClick(R.id.button_stop)
    void stop() {
        isStopwatchRunning = false;
        executed = false;
        stopwatchTask.cancel(true);
    }

    @OnClick(R.id.button_pause)
    void pause() {
        isStopwatchRunning = false;
    }

    @OnClick(R.id.button_record)
    void record() {
        if(isStopwatchRunning && stopwatchTask != null) stopwatchTask.record();
    }

    private class StopwatchTask extends AsyncTask<Void, Long, Integer> {
        long ms = 0, s = 0, m = 0;
        long startedTimelong;
        long elapsedTime = 0;
        long elapsedPausedTime = 0;
        @Override
        protected Integer doInBackground(Void... voids) {
            startedTimelong = System.currentTimeMillis();
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isStopwatchRunning) elapsedPausedTime = System.currentTimeMillis() - startedTimelong - elapsedTime;
                elapsedTime = System.currentTimeMillis() - startedTimelong - elapsedPausedTime;
                //Log.d("TAG", elapsedTime + ", " + elapsedPausedTime);

                ms = (elapsedTime % 1000) / 10;
                s = (elapsedTime / 1000) % 60;
                m = elapsedTime / 60000;

                publishProgress(m, s, ms);
                if (isCancelled()) break;
            }
            return null;
        }

        public void record() {
            recordArrayList.addFirst(String.format("%02d : %02d : %02d", m, s, ms));
            arrayAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            textViewStopwatch.setText(String.format("%02d : %02d : %02d", values[0], values[1], values[2]));
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            textViewStopwatch.setText(String.format("%02d : %02d : %02d", 0, 0, 0));
            recordArrayList.clear();
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        stopwatchTask.cancel(true);
        executed = false;
        unbinder.unbind();
        super.onDestroy();
    }
}