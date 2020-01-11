package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    ListView lst;
    ArrayList<modelclass> liste;
    public String kars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle extras = getIntent().getExtras();
        final String marka = extras.getString("marka_id");
        lst = findViewById(R.id.Listview2);
        liste = new ArrayList<modelclass>();
        RequestQueue istek = Volley.newRequestQueue(getApplicationContext());

        String adres = "http://192.168.56.1/bt/index.php/Modeller.php";
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, adres, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray _jsonarray =
                                    new JSONArray(response.toString());
                            for (int i = 0; i < _jsonarray.length(); i++) {
                                JSONObject _obj = _jsonarray.getJSONObject(i);
                                kars = _obj.get("MARKA_ID").toString();
                               if (kars.equals(marka)) {
                                    liste.add(new modelclass(_obj.get("MODEL_ID").toString(),
                                            _obj.get("MARKA_ID").toString(),
                                            _obj.get("MODEL_AD").toString(),
                                            _obj.get("MODEL_LOGO").toString(),
                                            _obj.get("MODEL_DESC").toString()));

                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Main3Activity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                        CustomAdapter2 adapter2 =
                                new CustomAdapter2(getApplicationContext(), R.layout.listviewlayout, liste);
                        lst.setAdapter(adapter2);

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        istek.add(stringRequest);
    }
}