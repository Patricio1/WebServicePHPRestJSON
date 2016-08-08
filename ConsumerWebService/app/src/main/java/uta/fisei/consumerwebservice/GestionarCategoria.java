package uta.fisei.consumerwebservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lio on 08/08/2016.
 */
public class GestionarCategoria extends AsyncTask<String,String,String>{
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    String categoryid="";
    String shortname="";
    String longname="";
    String mensaje="";
    String request="";
    String mensajeStatus="";
    private Context context;
    private ListView lstDatos;
    private EditText txtCodigo;
    private EditText txtShort;
    private EditText txtLong;
    private  List<String> listaValores = null;
    // url para acceder al web service
    private String url_request_get= "http://prueba2.preuniversitariofids.ec/PHPWebService/webservicepostgresql/categoryservice.php";
    private String url_request_insert= "http://prueba2.preuniversitariofids.ec/PHPWebService/webservicepostgresql/categoryserviceinsert.php";
    private String url_request_update= "http://prueba2.preuniversitariofids.ec/PHPWebService/webservicepostgresql/categoryserviceupdate.php";
    private String url_request_delete= "http://prueba2.preuniversitariofids.ec/PHPWebService/webservicepostgresql/categoryservicedelete.php";
    private String url_request_get_byid= "http://prueba2.preuniversitariofids.ec/PHPWebService/webservicepostgresql/getCategory.php";
    private String url_request_get_bylongname= "http://prueba2.preuniversitariofids.ec/PHPWebService/webservicepostgresql/getCategorybylongname.php";
    private static String url_gestion_category;


    private static final String TAG_SUCCESS = "success";
    public GestionarCategoria(Context context, String categoryid, String shortname, String longname,String request){
        this.context = context;
        this.categoryid = categoryid;
        this.shortname = shortname;
        this.longname = longname;
        this.request= request;
        if(this.request.equals("insert")){
            mensaje="Registrando  Categoria..";
            url_gestion_category = url_request_insert;
        }
        else if(this.request.equals("update")){
            mensaje="Actualizando  Categoria..";
            url_gestion_category = url_request_update;
        }
        else {
            mensaje="Eliminando  Categorias..";
            url_gestion_category = url_request_delete;
        }

    }
    public GestionarCategoria(Context context,String request,ListView lstDatos){
        this.context = context;
        this.request= request;
        this.lstDatos = lstDatos;
        mensaje="Consultando  Categorias..";
        url_gestion_category = url_request_get;
    }
    public GestionarCategoria(Context context, String request, EditText txtCodigo, EditText txtShort, EditText txtLong, String categoryid){
        this.context = context;
        this.request= request;
        if(this.request.equals("selectById")){
            this.categoryid = categoryid;
            url_gestion_category = url_request_get_byid;
        }
        else {
            this.longname = txtLong.getText().toString();
            url_gestion_category = url_request_get_bylongname;
        }
        mensaje="Consultando  Categoria..";
        this.txtCodigo = txtCodigo;
        this.txtShort = txtShort;
        this.txtLong = txtLong;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(mensaje);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        switch (request){
            case "insert":
                params.add(new BasicNameValuePair("categoryid", categoryid));
                params.add(new BasicNameValuePair("shortname", shortname));
                params.add(new BasicNameValuePair("longname", longname));
                mensajeStatus="Categoria creada correctamente";
                break;
            case "update":
                params.add(new BasicNameValuePair("categoryid", categoryid));
                params.add(new BasicNameValuePair("shortname", shortname));
                params.add(new BasicNameValuePair("longname", longname));
                mensajeStatus="Categoria actualizada correctamente";
                break;
            case "delete":
                params.add(new BasicNameValuePair("categoryid", categoryid));
                mensajeStatus="Categoria eliminada correctamente";
                break;
            case "select":
                getRestFul();
                break;
            case "selectById":
                params.add(new BasicNameValuePair("categoryid", categoryid));
                mensajeStatus="Categoria consultada correctamente";
                break;
            case "selectByLongName":
                params.add(new BasicNameValuePair("longname", longname));
                mensajeStatus="Categoria consultada correctamente";
                break;
        }


        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_gestion_category,"POST", params);
        if(request.equals("selectById")){
            JSONArray array = null;
            try {
                array = new JSONArray(json.getString("Registros"));

                //Recorre cada registro y concatena el resultado
                JSONObject row = array.getJSONObject(0);
                categoryid = row.getString("categoryid");
                shortname = row.getString("shortname");
                longname = row.getString("longname");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(request.equals("selectByLongName")){
            JSONArray array = null;
            try {
                array = new JSONArray(json.getString("Registros"));

                //Recorre cada registro y concatena el resultado
                JSONObject row = array.getJSONObject(0);
                categoryid = row.getString("categoryid");
                shortname = row.getString("shortname");
                longname = row.getString("longname");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // check log cat fro response
        Log.d("Create Response", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                String successack = mensajeStatus;
                Log.d("Respuesta Satisfactoria", successack );
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        if(request.equals("select")){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listaValores);
            lstDatos.setAdapter(adapter);
        }
        if(request.equals("selectById")|| request.equals("selectByLongName") ){
            txtCodigo.setText(categoryid);
            txtShort.setText(shortname);
            txtLong.setText(longname);
        }
        pDialog.dismiss();
    }

    public String getRestFul(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url_request_get);
        String strResultado = "NaN";
        //lista para almacenar las categorias

        try{
            listaValores = new ArrayList<String>();
            //ejecuta
            HttpResponse response = httpClient.execute(httpPost);
            //obtiene la respuesta del servidor
            String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            JSONObject object = new JSONObject(jsonResult);
            //Obtiene el estado
            String status= object.getString("status");
            if(status.equals("200"))
            {
                strResultado="";

                //Extrae los registros
                JSONArray array = new JSONArray(object.getString("Registros"));
                for (int i=0;i< array.length();i++){
                    //Recorre cada registro y concatena el resultado
                    JSONObject row = array.getJSONObject(i);
                    String categoryid = row.getString("categoryid");
                    String shortname = row.getString("shortname");
                    String longname = row.getString("longname");
                    //strResultado+= categoryid+" "+shortname+" "+longname+"\n";
                    listaValores.add(longname);
                }
                return strResultado;
            }
        }catch (ClientProtocolException e){
            strResultado = e.getMessage();
            e.printStackTrace();
        }catch (IOException e){
            strResultado = e.getMessage();
            e.printStackTrace();
        }catch (JSONException e){
            strResultado = e.getMessage();
            e.printStackTrace();
        }
        return strResultado;
    }
    //Transforma el InputStream en un String
    private StringBuilder inputStreamToString(InputStream is)
    {
        String line="";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try
        {
            while((line = rd.readLine()) != null)
            {
                stringBuilder.append(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return  stringBuilder;
    }


}
