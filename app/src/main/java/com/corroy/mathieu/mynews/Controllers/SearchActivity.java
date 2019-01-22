package com.corroy.mathieu.mynews.Controllers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import com.corroy.mathieu.mynews.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private DatePickerDialog.OnDateSetListener mDateListener2;
    private String sDate = "";
    private String eDate = "";
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
        imageButton.setOnClickListener(view -> startActivity());

        startDate = findViewById(R.id.search_begin_date);
        endDate = findViewById(R.id.search_end_date);

        startDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(SearchActivity.this
                    , android.R.style.Theme_Holo_Dialog_MinWidth
                    , mDateListener
                    , year, month, day);

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        mDateListener = (view, year, month, dayOfMonth) -> {
            month++;
            Log.d("Listener", "onDateListener: date" + year + "/" + month + "/" + dayOfMonth);
            String date = month + "/" + dayOfMonth + "/" + year;
            startDate.setText(date);
        };

        endDate.setOnClickListener(v -> {
            Calendar calendar2 = Calendar.getInstance();
            int year = calendar2.get(Calendar.YEAR);
            int month = calendar2.get(Calendar.MONTH);
            int day = calendar2.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(SearchActivity.this
                    , android.R.style.Theme_Holo_Dialog_MinWidth
                    , mDateListener2
                    , year, month, day);

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        mDateListener2 = (view, year, month, dayOfMonth) -> {
            month++;
            Log.d("Listener", "onDateListener: date" + year + "/" + month + "/" + dayOfMonth);
            String date = month + "/" + dayOfMonth + "/" + dayOfMonth;
            endDate.setText(date);
        };

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            checkBox.clear();
            eDate = "";
            sDate = "";

            cbArt = findViewById(R.id.search__checkbox_arts);
            cbBusiness = findViewById(R.id.search_checkbox_business);
            cbEntrepeneur = findViewById(R.id.search_checkbox_entrepreneurs);
            cbPolitics = findViewById(R.id.search_checkbox_politics);
            cbSports = findViewById(R.id.search_checkbox_sports);
            cbTravel = findViewById(R.id.search_checkbox_travel);

            checkBox.add(cbArt);
            checkBox.add(cbBusiness);
            checkBox.add(cbEntrepeneur);
            checkBox.add(cbPolitics);
            checkBox.add(cbSports);
            checkBox.add(cbTravel);

            Intent intent = new Intent(v.getContext(), SearchActivity.class);
            intent.putExtra("start_date", sDate);
            intent.putExtra("end_date", eDate);
            v.getContext().startActivity(intent);
        });
    }

    private void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }
    }