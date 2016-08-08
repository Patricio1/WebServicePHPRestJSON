package uta.fisei.consumerwebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActualizarCategoria extends AppCompatActivity {
    private Button btnUpdate;
    private EditText txtCategoryUp;
    private EditText txtShortNameUp;
    private EditText txtLongNameUp;
    private  Button btnDelete;
    private Button btnLimpiar;
    private Button btnNuevo;
    private Button btnBuscar;
    private Button btnVerTodos;
    private Button btnSalir;
    private ProgressDialog pDialog;
    String categoryid="";
    String shortname="";
    String longname="";
    JSONParser jsonParser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_categoria);

        txtCategoryUp = (EditText)findViewById(R.id.txtcodigoUp);
        txtShortNameUp = (EditText)findViewById(R.id.txtshortnameUp);
        txtLongNameUp = (EditText)findViewById(R.id.txtlongnameUp);

        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnNuevo = (Button)findViewById(R.id.btnNuevo);
        btnDelete = (Button)findViewById(R.id.btnEliminar);
        btnLimpiar = (Button)findViewById(R.id.btnLimpiar);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnVerTodos = (Button)findViewById(R.id.btnVerTodos);
        btnSalir = (Button)findViewById(R.id.btnSalir);

        //mostrar valores que vienen de un activity anterior
        Bundle bundle = getIntent().getExtras();
        longname = bundle.getString("longname");
        txtLongNameUp.setText(longname);
        new GestionarCategoria(ActualizarCategoria.this,"selectByLongName",txtCategoryUp,txtShortNameUp,txtLongNameUp,"").execute();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryid = txtCategoryUp.getText().toString();
                shortname = txtShortNameUp.getText().toString();
                longname = txtLongNameUp.getText().toString();
                if(categoryid.trim().length()>0 && shortname.trim().length()>0 && longname.trim().length()>0){
                    new GestionarCategoria(ActualizarCategoria.this,categoryid,shortname,longname,"update").execute();
                }
                else{
                    Toast.makeText(ActualizarCategoria.this,"Ingrese todos los datos",Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryid = txtCategoryUp.getText().toString();
                shortname = txtShortNameUp.getText().toString();
                longname = txtLongNameUp.getText().toString();
                if(categoryid.trim().length()>0 && shortname.trim().length()>0 && longname.trim().length()>0){
                    new GestionarCategoria(ActualizarCategoria.this,categoryid,shortname,longname,"insert").execute();
                    clean();
                }
                else{
                    Toast.makeText(ActualizarCategoria.this,"Ingrese todos los datos",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryid = txtCategoryUp.getText().toString();
                if(categoryid.trim().length()>0){
                    new GestionarCategoria(ActualizarCategoria.this,categoryid,"","","delete").execute();
                    clean();
                }
                else
                    Toast.makeText(ActualizarCategoria.this,"Ingrese un valor para el codigo",Toast.LENGTH_SHORT).show();

            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clean();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryid = txtCategoryUp.getText().toString();
                if(categoryid.trim().length()>0){
                    new GestionarCategoria(ActualizarCategoria.this,"selectById",txtCategoryUp,txtShortNameUp,txtLongNameUp,categoryid).execute();
                }
                else  Toast.makeText(ActualizarCategoria.this,"Ingrese un valor para la busqueda",Toast.LENGTH_SHORT).show();

            }
        });
        btnVerTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActualizarCategoria.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
private void clean(){
    txtCategoryUp.setText("");
    txtShortNameUp.setText("");
    txtLongNameUp.setText("");
}



}
