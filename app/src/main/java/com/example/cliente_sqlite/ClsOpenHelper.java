package com.example.cliente_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClsOpenHelper extends SQLiteOpenHelper {
    public ClsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table TblCliente(identificacion text primary key,correo text not null,nombre text not null,clave text not null,activo text not null default 'si')");
        db.execSQL("create table TblElectrodomestico(codigo text primary key, descripcion text not null,identificacion text not null,cantidad integer not null,valor_unitario integer not null,activo text not null default 'si',constraint pk_venta foreign key (identificacion)references TblCliente(identificacion))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Tblcliente");
        db.execSQL("drop table TblElectrodomestico");{
            onCreate(db);
        }
    }
}
