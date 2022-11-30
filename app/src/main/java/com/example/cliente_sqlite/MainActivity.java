package com.example.cliente_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText jetusuario,jetclave;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Almacen.db",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        jetusuario=findViewById(R.id.etusuario);
        jetclave=findViewById(R.id.etclave);
    }

    public void Ingresar(View view){
        String usuario,clave;
        usuario=jetusuario.getText().toString();
        clave=jetclave.getText().toString();
        if (usuario.isEmpty() || clave.isEmpty()){
            Toast.makeText(this, "Usuario y clave requeridos", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }else{
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblCliente where identificacion='"+usuario+"' and clave='"+clave+"'",null);
            if (fila.moveToNext()){
                Intent intvehiculo=new Intent(this,VehiculosActivity.class);
                startActivity(intvehiculo);
            }else{
                Toast.makeText(this, "Usuario o clave invalidos", Toast.LENGTH_SHORT).show();
                jetusuario.requestFocus();
            }

            db.close();

        }

    }

    public void Registrarse(View view){
        Intent intregistrarse=new Intent(this, RegistrarseActivity.class);
        startActivity(intregistrarse);
    }

}