package mx.edu.tesoem.isc.g7s21hugo4295p11;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Almacen {
    String nomarch="hugo.txt";
    ArrayList<String> contenido = new ArrayList<String>();

    public boolean grabar(Context context, ArrayList<String> nombres){
        try{
            OutputStreamWriter archivo = new OutputStreamWriter(context.openFileOutput(nomarch, Activity.MODE_PRIVATE));
            for(String nombre : nombres){
                archivo.write(nombre+"\n");
            }
            archivo.flush();
            archivo.close();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean leer(Context context){
        try{
            InputStreamReader archivo = new InputStreamReader(context.openFileInput(nomarch));
            BufferedReader br = new BufferedReader(archivo);
            String linea;
            contenido.add("{\"correo\":\"correo\",\"edad\":\"edad\",\"nombre\":\"nombre\"}");
            while ((linea = br.readLine()) != null){
                contenido.add(linea);
            }
            br.close();
            archivo.close();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public ArrayList<String> getContenido(){
        return contenido;
    }

    public boolean verifica_arch(Context context){
        String[] archivos = context.fileList();
        for (String archivo : archivos)
            if(archivo.equals(nomarch)) return true;
        return false;
    }


}
