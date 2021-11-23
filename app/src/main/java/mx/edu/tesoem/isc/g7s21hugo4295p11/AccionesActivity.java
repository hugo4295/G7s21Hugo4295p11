package mx.edu.tesoem.isc.g7s21hugo4295p11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class AccionesActivity extends AppCompatActivity {

    EditText txtnombrec, txtedadc, txtcorreoc;
    ArrayList<String> datos = new ArrayList<String>();
    int linea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);

        txtnombrec = findViewById(R.id.txtnombrec);
        txtedadc = findViewById(R.id.txtedadc);
        txtcorreoc = findViewById(R.id.txtcorreoc);

        Bundle valores = getIntent().getExtras();
        String cadena = valores.getString("Cadena");
        linea = valores.getInt("posicion");
        convertir(cadena);
        Almacen almacen = new Almacen();
        if (almacen.leer(this)){
            datos = almacen.getContenido();
        }
    }

    private void convertir(String cadena){
        Gson gson = new Gson();
        DatosInfo datos = gson.fromJson(cadena, DatosInfo.class);
        txtnombrec.setText(datos.getNombre());
        txtedadc.setText(datos.getEdad());
        txtcorreoc.setText(datos.getCorreo());
    }

    public void presionaboton(View v){
        switch (v.getId()){
            case R.id.btnactualiza: actualizamos();
            break;
            case R.id.btnelimina: borrar();
            break;
        }
    }

    private void actualizamos(){
        Gson gson = new Gson();
        DatosInfo info = new DatosInfo();
        Almacen almacen = new Almacen();
        String cadena="";
        info.setNombre(txtnombrec.getText().toString());
        info.setEdad(txtedadc.getText().toString());
        info.setCorreo(txtcorreoc.getText().toString());
        cadena = gson.toJson(info);
        datos.set(linea,cadena);
        datos.remove(0);
        if (almacen.grabar(this,datos)){
            Toast.makeText(this, "Se grabo correctamente...", Toast.LENGTH_SHORT).show();
            lanzainicio();
            finish();
        }
    }

    private void borrar(){
        Almacen almacen = new Almacen();
        datos.remove(linea);
        datos.remove(0);
        if (almacen.grabar(this,datos)){
            Toast.makeText(this, "Se elimino el dato correctamente", Toast.LENGTH_SHORT).show();
            lanzainicio();
            finish();
        }else{
            Toast.makeText(this, "No se puede eliminar", Toast.LENGTH_SHORT).show();
        }

    }

    private void lanzainicio(){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}