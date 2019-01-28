package com.corroy.mathieu.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.corroy.mathieu.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.back_arrow_notification) ImageButton backArrow;
    @BindView(R.id.notification_switch) Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        backArrow.setOnClickListener(v -> startActivity());

        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){

            } else {
                mSwitch.setChecked(false);

            }
        });
        }

      private void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
      }
    }

