package com.example.sqlite_et_android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.sqlite_et_android.classes.Etudiant;
import com.example.sqlite_et_android.util.MySQLiteHelper;

public class EtudiantService {

    private static final String   TABLE_NAME = "etudiant";
    private static final String   KEY_ID     = "id";
    private static final String   KEY_NOM    = "nom";
    private static final String   KEY_PRENOM = "prenom";
    private static final String[] COLUMNS    = {KEY_ID, KEY_NOM, KEY_PRENOM};

    private final MySQLiteHelper helper;

    public EtudiantService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    // CREATE
    public void create(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM,    e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        db.insert(TABLE_NAME, null, values);
        Log.d("insert", e.getNom());
        db.close();
    }

    // UPDATE
    public void update(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,     e.getId());
        values.put(KEY_NOM,    e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        db.update(TABLE_NAME, values, "id = ?", new String[]{e.getId() + ""});
        db.close();
    }

    // READ ONE
    public Etudiant findById(int id) {
        Etudiant e = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME, COLUMNS,
                "id = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (c.moveToFirst()) {
            e = new Etudiant();
            e.setId(c.getInt(0));
            e.setNom(c.getString(1));
            e.setPrenom(c.getString(2));
        }
        c.close();
        db.close();
        return e;
    }

    // DELETE
    public void delete(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(e.getId())});
        db.close();
    }

    // READ ALL
    public List<Etudiant> findAll() {
        List<Etudiant> eds = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Etudiant e = new Etudiant();
                e.setId(c.getInt(0));
                e.setNom(c.getString(1));
                e.setPrenom(c.getString(2));
                eds.add(e);
                Log.d("id = ", e.getId() + "");
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return eds;
    }
}