package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.Toast;
import com.corroy.mathieu.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private DatePickerDialog.OnDateSetListener mDateListener2;
    private TextInputEditText searchTerm;
    private Date date;
    private String sDate = "";
    private String eDate = "";
    private CheckBox cbArt;
    private CheckBox cbBusiness;
    private CheckBox cbPolitics;
    private CheckBox cbEntrepeneur;
    private CheckBox cbTravel;
    private CheckBox cbSports;
    private String section = "type_of_material:News";
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
            String selectedDay = String.valueOf(dayOfMonth);
            String selectedMonth = String.valueOf(month);
            month++;
            Log.d("Listener", "onDateListener: date" + year + "/" + month + "/" + "0" +dayOfMonth);
            if(month < 10){
                selectedMonth = "0" + month;
            }
            if (dayOfMonth < 10) {
                selectedDay = "0" + dayOfMonth;
            }
            String date = selectedMonth + "/" + selectedDay + "/" + year;
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
            String selectedDay = String.valueOf(dayOfMonth);
            String selectedMonth = String.valueOf(month);
            month++;
            Log.d("Listener", "onDateListener: date" + year + "/" + month + "/" + dayOfMonth);
            if(month < 10){
                selectedMonth = "0" + month;
            }
            if (dayOfMonth < 10) {
                selectedDay = "0" + dayOfMonth;
            }
                String date = selectedMonth + "/" + selectedDay + "/" + year;
                endDate.setText(date);
        };

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            checkBox.clear();
            eDate = "";
            sDate = "";
            section = "type_of_material:News";

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

            String inputPattern = "dd/MM/yyyy";
            String outputPattern = "yyyyMMdd";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            searchTerm = findViewById(R.id.search_query_term);

            int count = 0;

            for(int i = 0; i < checkBox.size(); i++) {
                if (checkBox.get(i).isChecked()) {
                    if (count == 0) {
                       section = section + " AND news_desk:(" + checkBox.get(i).getText().toString();
                       count++;
                    }
                    else if (count > 0){
                        section = section + " OR " + checkBox.get(i).getText().toString();
                        count++;
                    }
                }
            }

            if(count > 0) section = section + ")";

            if (count == 0){
                Toast.makeText(getApplicationContext(), "Please specify at least one category to search", Toast.LENGTH_LONG).show();
            }
            else if (searchTerm.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please select at least one keyword to search", Toast.LENGTH_LONG).show();
            } else {
                try {
                    date = inputFormat.parse(startDate.getText().toString());
                    sDate = outputFormat.format(date);
                } catch (ParseException e){
                    e.printStackTrace();
                }

                try {
                    date = inputFormat.parse(endDate.getText().toString());
                    eDate = outputFormat.format(date);
                } catch (ParseException e){
                    e.printStackTrace();
                }

                    Intent intent = new Intent(v.getContext(), SearchResult.class);
                    intent.putExtra("query", searchTerm.getText().toString());
                    intent.putExtra("start_date", sDate);
                    intent.putExtra("end_date", eDate);
                    intent.putExtra("section", section);
                    v.getContext().startActivity(intent);
                }
        });
    }

    private void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }
    }