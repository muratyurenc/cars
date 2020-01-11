package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class Main2Activity extends AppCompatActivity {
ListView lst;
ArrayList<arabaClass> liste;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lst = findViewById(R.id.list1);
        liste = new ArrayList<arabaClass>();

        RequestQueue istek = Volley.newRequestQueue(getApplicationContext());
        String Adres = "http://192.168.56.1/bt/index.php/Markalar.php";
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, Adres, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray _jsonarray =
                                    new JSONArray(response.toString());
                            for (int i = 0; i < _jsonarray.length(); i++) {
                                JSONObject _obj =
                                        _jsonarray.getJSONObject(i);
                                liste.add(new arabaClass(
                                        _obj.get("MARKA_ID").toString(),
                                        _obj.get("MARKA_AD").toString(),
                                        _obj.get("MARKA_LOGO").toString(),
                                        _obj.get("MARKA_DESC").toString()));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Main2Activity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                        Customadapter adapter = new Customadapter(getApplicationContext(),
                                                                R.layout.listviewlayout,liste);
                        lst.setAdapter(adapter);
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String marka_id = liste.get(position).getMarka_id().toString();
                                Intent i = new Intent(getApplicationContext(),Main3Activity.class);
                                i.putExtra("marka_id",marka_id);
                                startActivity(i);
                            }
                        });

                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        istek.add(stringRequest);
    }
}
