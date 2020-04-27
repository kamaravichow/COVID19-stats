package com.aravi.covid;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    MaterialToolbar toolbar;

    List<String> places;

    TextView newCase, totalCase, newDeaths, totalDeaths, newRecovered, totalRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        places = new ArrayList<>();

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        newCase = findViewById(R.id.text_new_cases);
        totalCase = findViewById(R.id.text_total_cases);

        newDeaths = findViewById(R.id.text_new_deaths);
        totalDeaths = findViewById(R.id.text_total_deaths);

        newRecovered = findViewById(R.id.text_new_recovered);
        totalRecovered = findViewById(R.id.text_total_recovered);


        getDataGlobal();
    }


    private void getDataGlobal() {

        final Call<API> data = CovidAPI.getService().getData();
        data.enqueue(new Callback<API>() {
            @Override
            public void onResponse(Call<API> call, Response<API> response) {
                if (response.isSuccessful()){
                    Log.i(TAG, "Response Message" + response.message());

                    API data_fetched = response.body();
                    List<Country> countries = data_fetched.getCountries();

                    DecimalFormat formatter = new DecimalFormat("#,###,###");

                    newCase.setText(formatter.format(data_fetched.getGlobal().getNewConfirmed().intValue()));
                    totalCase.setText(formatter.format(data_fetched.getGlobal().getTotalConfirmed().intValue()));

                    newDeaths.setText(formatter.format(data_fetched.getGlobal().getNewDeaths().intValue()));
                    totalDeaths.setText(formatter.format(data_fetched.getGlobal().getTotalDeaths().intValue()));

                    newRecovered.setText(formatter.format(data_fetched.getGlobal().getNewRecovered().intValue()));
                    totalRecovered.setText(formatter.format(data_fetched.getGlobal().getTotalRecovered().intValue()));

                    for (Country country: countries){
                        if (country.getCountry().equals("India")){



                        }

                    }
                }
                else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<API> call, Throwable t) {
                Log.e(TAG, t.getMessage());

            }
        });
    }
}
