package com.example.calculator2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText inputNumberEditText;
    private Button calculateButton;
    private ListView primeNumbersListView;
    private TextView currentNumberTextView;
    private TextView totalTimeTextView;

    private ArrayList<Integer> primeNumbers;
    private ArrayAdapter<Integer> adapter;
    private PrimeAsyncTask primeAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);

        inputNumberEditText = findViewById(R.id.inputNumberEditText);
        calculateButton = findViewById(R.id.calculateButton);
        primeNumbersListView = findViewById(R.id.primeNumbersListView);
        currentNumberTextView = findViewById(R.id.currentNumberTextView);
        totalTimeTextView = findViewById(R.id.totalTimeTextView);
        primeNumbers = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, primeNumbers);
        primeNumbersListView.setAdapter(adapter);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int inputNumber = Integer.parseInt(inputNumberEditText.getText().toString());
                    inputNumberEditText.setText("");
                    adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
                    primeNumbersListView.setAdapter(adapter);
                    primeAsyncTask = new PrimeAsyncTask(
                            MainActivity.this,
                            primeNumbers,
                            adapter,
                            currentNumberTextView,
                            totalTimeTextView
                    );
                    primeAsyncTask.execute(inputNumber);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}