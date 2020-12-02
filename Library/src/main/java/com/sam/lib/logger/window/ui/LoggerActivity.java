package com.sam.lib.logger.window.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sam.lib.logger.window.LoggerController;
import com.sam.lib.window.R;

public class LoggerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity_logger);
        TextView tv = findViewById(R.id.tv);
        tv.setText(LoggerController.readLog());
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }
}