package uta.fisei.consumerwebservice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button btnExit;
    ListView lstCategorias;
    List<String> listaDatos = new ArrayList<String>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btnRF);
        btnExit = (Button)findViewById(R.id.btnExit);
        lstCategorias = (ListView)findViewById(R.id.lstCategorias);
        new GestionarCategoria(MainActivity.this,"select",lstCategorias).execute();
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new GestionarCategoria(MainActivity.this,"select",lstCategorias).execute();
             }
         });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        lstCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                String itemSeleccionado =  lstCategorias.getAdapter().getItem(position).toString();
                //Toast.makeText(MainActivity.this,"Datos"+itemSeleccionado,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,ActualizarCategoria.class);
                intent.putExtra("longname",itemSeleccionado);
                startActivity(intent);
            }
        });
    }
}
