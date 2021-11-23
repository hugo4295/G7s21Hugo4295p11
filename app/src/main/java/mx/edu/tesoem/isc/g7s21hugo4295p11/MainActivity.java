package mx.edu.tesoem.isc.g7s21hugo4295p11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtnombre,txtedad,txtcorreo;
    GridView gvdatos;
    ArrayList<String> datos = new ArrayList<String>();
    ArrayList<String> listagv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtnombre = findViewById(R.id.txtnombrec);
        txtedad = findViewById(R.id.txtedad);
        txtcorreo = findViewById(R.id.txtcorreo);

        gvdatos = findViewById(R.id.gvdatos);

        checa_existe();

        gvdatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                if ((posicion > 0) && ((posicion%3)==0)){
                    int linea = posicion /3;
                    String cadena = datos.get(linea);
                    Toast.makeText(MainActivity.this, "valor posicion " + linea, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AccionesActivity.class);
                    intent.putExtra("Cadena",cadena);
                    intent.putExtra("posicion",linea);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void graba(){
        Almacen almacen = new Almacen();

        datos.add(fprmatgson(txtnombre.getText().toString(),txtedad.getText().toString(),txtcorreo.getText().toString()));
        if (almacen.grabar(this, datos)){
            Toast.makeText(this, "Se grabo la info correctamente", Toast.LENGTH_SHORT).show();
            txtnombre.setText("");
            txtedad.setText("");
            txtcorreo.setText("");
            lee();
        } else {
            Toast.makeText(this, "Error al escribir", Toast.LENGTH_SHORT).show();
        }
    }

    private void lee(){
        Almacen almacen = new Almacen();
        if (almacen.leer(this)){
            datos = almacen.getContenido();
            mostrargv();
        }
    }

    private void checa_existe(){
        Almacen almacen = new Almacen();
        if (almacen.verifica_arch(this)){
            lee();
        }
    }

    public void acciones(View v){
        switch (v.getId()){
            case R.id.btngrabar: graba();
                break;
        }
    }

    private void mostrargv(){
        listagv = new ArrayList<String>();
        DatosInfo datosInfo;
        ArrayAdapter<String> adapter;
        for (String lgson: datos) {
            datosInfo = new DatosInfo();
            datosInfo = formatclass(lgson);
            listagv.add(datosInfo.getNombre());
            listagv.add(datosInfo.getEdad());
            listagv.add(datosInfo.getCorreo());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listagv);
        gvdatos.setAdapter(adapter);
    }

    private String fprmatgson(String nombre, String edad, String correo){
        DatosInfo datosInfo = new DatosInfo(nombre, edad, correo);
        Gson gson = new Gson();
        return gson.toJson(datosInfo );
    }

    private DatosInfo formatclass(String cadenagson){
        Gson gson = new Gson();
        DatosInfo datosInfo = gson.fromJson(cadenagson, DatosInfo.class);
        return datosInfo;
    }
}