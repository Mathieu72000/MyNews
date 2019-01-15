package com.corroy.mathieu.mynews.Controllers;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.corroy.mathieu.mynews.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText startDate;
    private EditText endDate;
    private String sDate = "";
    private String eDate = "";
    private Date date;
    private TextInputEditText searchTerm;
    private CheckBox cbArt;
    private CheckBox cbBusiness;
    private CheckBox cbPolitics;
    private CheckBox cbEntrepeneur;
    private CheckBox cbTravel;
    private CheckBox cbSports;
    private final List<CheckBox> checkBox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton imageButton = findViewById(R.id.back_arrow);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });

        startDate = findViewById(R.id.search_begin_date);
        endDate = findViewById(R.id.search_end_date);
    }
}
