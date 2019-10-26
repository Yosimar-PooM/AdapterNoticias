package com.example.adapternoticias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {
    //Datasource
    public Noticias[] noticias ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LLamado al WebService
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://api.androidhive.info/contacts/", datos, MainActivity.this, MainActivity.this);
        ws.execute("");
        AdaptadorNoticias adaptadornoticias = new AdaptadorNoticias(this, noticias);
        ListView lstOpciones = (ListView)findViewById(R.id.lst_opciones);
        lstOpciones.setAdapter(adaptadornoticias);
        View header =getLayoutInflater().inflate(R.layout.ly_cabecera,null);
        lstOpciones.addHeaderView(header);
    }

    public ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish",result);
        //Leer el JSON
        JSONObject jsonObj = new JSONObject(result);
        JSONArray contacts = jsonObj.getJSONArray("contacts");
        HashMap<String, String> contactos = new HashMap<String, String>();
        for (int i=0; i<contacts.length();i++){
            JSONObject obj= contacts.getJSONObject(i);
            String name=obj.getString("name");
            String email=obj.getString("email");
            contactos.put("name",name);
            contactos.put("email",email);
            contactList.add(contactos);
        }
        String lista="";
        for(int j=0;j<contactList.size();j++){
            HashMap<String, String> valor=contactList.get(j);
            for(Map.Entry<String, String> entry:valor.entrySet()){
                 lista = lista+""+entry.getKey()+": "+ entry.getValue()+ "\n";

            }
            lista=lista+"\n";
            noticias=new Noticias[]{
                    new Noticias("", lista)};
        }
    }
}
