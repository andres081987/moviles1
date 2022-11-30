package com.example.cliente_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarseActivity extends AppCompatActivity {

    EditText jetidentificacion, jetcorreo,jetnombre, jetclave1,jetclave2;
    CheckBox jcbactivo;
    String identificacion,correo,nombre,clave1,clave2;
    Long resp;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Almacen.db",null,1);
    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        // Ocultar la barra de titulo y asociar objetos Java y Xml
        jetidentificacion=findViewById(R.id.etidentificacion);
        jetcorreo=findViewById(R.id.etcorreo);
        jetnombre=findViewById(R.id.etnombre);
        jetclave1=findViewById(R.id.etclave1);
        jetclave2=findViewById(R.id.etclave2);
        jcbactivo=findViewById(R.id.cbactivo);
        sw=0;
    }

    public void Guardar (View view){
        identificacion=jetidentificacion.getText().toString();
        correo=jetcorreo.getText().toString();
        nombre=jetnombre.getText().toString();
        clave1=jetclave1.getText().toString();
        clave2=jetclave2.getText().toString();
        if (identificacion.isEmpty() || correo.isEmpty() || nombre.isEmpty() || clave1.isEmpty() || clave2.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else{
            if (!clave1.equals(clave2)){
                Toast.makeText(this, "Verifique las claves", Toast.LENGTH_SHORT).show();
                jetclave1.requestFocus();
            }
            else{
                SQLiteDatabase db=admin.getWritableDatabase();
                ContentValues registro=new ContentValues();
                registro.put("identificacion",identificacion);
                registro.put("correo",correo);
                registro.put("nombre",nombre);
                registro.put("clave",clave1);
                if (sw==0)
                    resp=db.insert("TblCliente",null,registro);
                else
                    resp=(long)db.update("TblCliente",registro,"identificacion ='"+identificacion+"'",null);
                if (resp == 0){
                    Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                    Limpiar_campos();
                }
                db.close();
            }
        }
    }

    public void consultar(View view){
        Consulta_cliente();
    }

    private void Consulta_cliente(){
        identificacion=jetidentificacion.getText().toString();
        if (identificacion.isEmpty()){
            Toast.makeText(this, "Identificacion es requerida", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else{
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblCliente where identificacion='"+ identificacion + "'",null);
            if (fila.moveToNext()) {
                sw = 1;
                if (fila.getString(4).equals("no")) {
                    Toast.makeText(this, "Registro existe pero esta anulado", Toast.LENGTH_SHORT).show();
                } else {
                    jetcorreo.setText(fila.getString(1));
                    jetnombre.setText(fila.getString(2));
                    jcbactivo.setChecked(true);
                }
            }
            else{
                Toast.makeText(this, "Registro no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void Eliminar (View view){
        if (sw == 0){
            Toast.makeText(this, "Para eliminar, debe primero consultar", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else{
            sw=0;
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("identificacion",identificacion);
            resp=(long)db.delete("TblCliente","identificacion='"+identificacion+"'",null);
                if (resp == 0){
                    Toast.makeText(this, "Error eliminando registro", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                    Limpiar_campos();
                }
                db.close();
            }
        }

    public void Anular (View view){
        if (sw == 0){
            Toast.makeText(this, "Para anular, debe primero consultar", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else{
            sw=0;
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("activo","no");
            resp=(long)db.update("TblCliente",registro,"identificacion='"+identificacion+"'",null);
            if (resp == 0){
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            db.close();
        }
    }

    public void Cancelar(View view) {
        Limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }
    private void Limpiar_campos(){
        jetclave1.setText("");
        jetidentificacion.setText("");
        jetclave2.setText("");
        jetnombre.setText("");
        jetcorreo.setText("");
        jcbactivo.setChecked(false);
        jetidentificacion.requestFocus();
    }
}