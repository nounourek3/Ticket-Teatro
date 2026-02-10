package com.example.ticketteatro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="teatro.db";
    private static final int DATABASE_VERSION=1;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE usuarios (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "apellido TEXT NOT NULL, " +
                        "telefono TEXT, " +
                        "email TEXT UNIQUE NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "rol TEXT DEFAULT 'cliente')"
        );
        db.execSQL(
                "CREATE TABLE cartelera (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "titulo TEXT NOT NULL, " +
                        "cartel_url TEXT, " +
                        "descripcion TEXT)"
        );
        db.execSQL(
                "CREATE TABLE funciones (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "cartelera_id INTEGER, " +
                        "fecha_hora TEXT NOT NULL, " +
                        "FOREIGN KEY(cartelera_id) REFERENCES cartelera(id))"
        );
        db.execSQL(
                "CREATE TABLE zonas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, " +
                        "precio REAL NOT NULL)"
        );
        db.execSQL(
                "CREATE TABLE butacas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "zona_id INTEGER, " +
                        "fila INTEGER, " +
                        "numero INTEGER, " +
                        "FOREIGN KEY(zona_id) REFERENCES zonas(id))"
        );
        db.execSQL(
                "CREATE TABLE ocupacion_butacas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "funcion_id INTEGER, " +
                        "butaca_id INTEGER, " +
                        "estado TEXT DEFAULT 'disponible', " +
                        "FOREIGN KEY(funcion_id) REFERENCES funciones(id), " +
                        "FOREIGN KEY(butaca_id) REFERENCES butacas(id))"
        );
        db.execSQL(
                "CREATE TABLE ventas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuario_id INTEGER, " +
                        "fecha_venta TEXT DEFAULT CURRENT_TIMESTAMP, " +
                        "total REAL, " +
                        "FOREIGN KEY(usuario_id) REFERENCES usuarios(id))"
        );
        db.execSQL(
                "CREATE TABLE tickets (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "venta_id INTEGER, " +
                        "ocupacion_id INTEGER, " +
                        "codigo_ticket TEXT UNIQUE, " +
                        "FOREIGN KEY(venta_id) REFERENCES ventas(id), " +
                        "FOREIGN KEY(ocupacion_id) REFERENCES ocupacion_butacas(id))"
        );

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Esto se usa para actualizar la DB si cambia la estructura
        db.execSQL("DROP TABLE IF EXISTS tickets");
        db.execSQL("DROP TABLE IF EXISTS ventas");
        db.execSQL("DROP TABLE IF EXISTS ocupacion_butacas");
        db.execSQL("DROP TABLE IF EXISTS butacas");
        db.execSQL("DROP TABLE IF EXISTS zonas");
        db.execSQL("DROP TABLE IF EXISTS funciones");
        db.execSQL("DROP TABLE IF EXISTS cartelera");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
    public long insertUsuario(String nombre, String apellido, String email, String telefono, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("apellido", apellido);
        cv.put("email", email);
        cv.put("telefono", telefono);
        cv.put("password", password);
        return db.insert("usuarios", null, cv); // returns the new row ID, or -1 if error
    }

}
