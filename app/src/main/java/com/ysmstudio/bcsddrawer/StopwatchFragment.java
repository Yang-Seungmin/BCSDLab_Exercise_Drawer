package com.ysmstudio.bcsddrawer;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private TextView textViewStopwatch;
    private Button buttonStart, buttonStop, buttonPause;

    private ListView listViewRecords;

    private StopwatchTask stopwatchTask;
    private boolean isStopwatchRunning = false;
    private boolean executed = false;

    public StopwatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        init(view);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        return view;
    }

    private void init(View view) {
        textViewStopwatch = view.findViewById(R.id.text_view_stopwatch);
        buttonStart = view.findViewById(R.id.button_start);
        buttonStop = view.findViewById(R.id.button_stop);
        buttonPause = view.findViewById(R.id.button_pause);
        listViewRecords = view.findViewById(R.id.list_view_records);

        stopwatchTask = new StopwatchTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                isStopwatchRunning = true;
                if (!executed) {
                    stopwatchTask = new StopwatchTask();
                    stopwatchTask.execute();
                    executed = true;
                }
                break;
            case R.id.button_stop:
                isStopwatchRunning = false;
                executed = false;
                stopwatchTask.cancel(true);
                break;
            case R.id.button_pause:
                isStopwatchRunning = false;
                break;
        }
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
                    Thread.sleep(10);
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopwatchTask.cancel(true);
        executed = false;
    }
}