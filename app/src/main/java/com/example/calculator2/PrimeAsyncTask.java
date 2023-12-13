package com.example.calculator2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PrimeAsyncTask extends AsyncTask<Integer, Integer, ArrayList<Integer>> {

    private WeakReference<Context> contextReference;
    private ArrayList<Integer> primeNumbers;
    private ArrayAdapter<Integer> adapter;
    private TextView currentNumberTextView;
    private TextView totalTimeTextView;

    public PrimeAsyncTask(Context context, ArrayList<Integer> primeNumbers, ArrayAdapter<Integer> adapter,
                          TextView currentNumberTextView, TextView totalTimeTextView) {
        this.contextReference = new WeakReference<>(context);
        this.primeNumbers = primeNumbers;
        this.adapter = adapter;
        this.currentNumberTextView = currentNumberTextView;
        this.totalTimeTextView = totalTimeTextView;
    }
    private long startTime;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected ArrayList<Integer> doInBackground(Integer... params) {
        int n = params[0];
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                publishProgress(i);
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Context context = contextReference.get();
        if (context != null) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentNumberTextView.setText("Current number: " + values[0]);
                    adapter.add(values[0]);  // Ajouter à l'adaptateur dans onProgressUpdate
                }
            });
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Integer> result) {
        super.onPostExecute(result);
        Context context = contextReference.get();
        if (context != null) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    totalTimeTextView.setText("Total time: " + totalTime + " ms");
                }
            });
        }
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
