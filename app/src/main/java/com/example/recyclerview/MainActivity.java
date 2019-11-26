package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String URL_Usuarios = "https://www.serverbpw.com/cm/2020-1/products.php";

    List<Usuarios> usuariosList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        usuariosList = new ArrayList<>();

        loadUsers();

    }

    private void loadUsers() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Usuarios,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject usuario = array.getJSONObject(i);

                                usuariosList.add(new Usuarios(
                                        //usuario.getInt("id"),
                                        usuario.getString("name"),
                                        usuario.getString("provider"),
                                        usuario.getString("thumbnail_url")
                                ));
                                Toast.makeText(MainActivity.this, "hola"+usuariosList, Toast.LENGTH_SHORT).show();
                            }

                            Adaptador adaptador = new Adaptador(MainActivity.this, usuariosList);
                            recyclerView.setAdapter(adaptador);

                        } catch (JSONException error) {
                            error.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);


    }
}
