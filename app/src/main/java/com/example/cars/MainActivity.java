package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText edt1;
    EditText edt2;
    boolean bosluk;
    String text1;
    String text2;
    String Adres = "http://192.168.56.1/bt/index.php/Login.php";
    boolean main2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        edt1 = findViewById(R.id.editText);
        edt2 = findViewById(R.id.editText2);
        text1 = edt1.getText().toString();
        text2 = edt2.getText().toString();

        final RequestQueue istek = Volley.newRequestQueue(getApplicationContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();

                if (bosluk == true) {
                    login();
                }

            }
        });
    }
    public void Check()
    {
        final String text1 = edt1.getText().toString();
        final String text2 = edt2.getText().toString();
        if (TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2)){
            bosluk = false;
            Toast.makeText(this, "Kullanıcı Adı veya Şifre Boş", Toast.LENGTH_SHORT).show();
        }
        else {
            bosluk = true;
        }
    }
    public void login()
    {
        text1 = edt1.getText().toString();
        text2 = edt2.getText().toString();
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, Adres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray _jsonarray =
                            new JSONArray(response.toString());
                    for (int i = 0; i < _jsonarray.length(); i++) {
                        JSONObject _obj =
                                _jsonarray.getJSONObject(i);

                        String user = new String(_obj.get("USER_NAME").toString());
                        String pass = new String(_obj.get("USER_PASS").toString());
                        if (text1.equals(user) && text2.equals(pass)) {
                            main2 = true;
                            Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(i2);
                        }

                    }
                    if (main2 = false){
                        Toast.makeText(MainActivity.this, "HATA", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue istek = Volley.newRequestQueue(getApplicationContext());
        istek.add(stringRequest);
    }
    }








