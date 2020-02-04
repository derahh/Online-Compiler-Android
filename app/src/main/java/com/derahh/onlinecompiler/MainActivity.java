package com.derahh.onlinecompiler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    EditText etInput;
    Button btnSubmit;

    APIClient api = APIClient.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult  = findViewById(R.id.tv_result);
        etInput   = findViewById(R.id.et_input);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    public void runCompile(View view) {
        Call<String> execute = api.getAPI().execute(new PostData(etInput.getText().toString()));

        tvResult.setText("Loading...");

        execute.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                tvResult.setText("");

                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String output = jsonObject.getString("output");
                        tvResult.setText(output);
                    } else {
                        showSnackBar(response.errorBody().toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showSnackBar("Gagal Parsing JSON : " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                tvResult.setText("Failed");
                showSnackBar("Gagal : " + t.getMessage());
            }
        });
    }

    private void showSnackBar(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
