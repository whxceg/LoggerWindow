package com.sam.lib.logger.window.ui;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.sam.lib.logger.window.LoggerController;
import com.sam.lib.window.R;

public class LoggerSwitchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity_logger_switch);
        SwitchCompat switchCompat = findViewById(R.id.switcher);
        switchCompat.setChecked(LoggerController.getWriteAble());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    LoggerWindow.getInstance(view.getContext()).show();
                } else {
                    LoggerWindow.getInstance(view.getContext()).hide();
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }
}