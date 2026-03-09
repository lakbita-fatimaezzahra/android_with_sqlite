package com.example.sqlite_et_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.sqlite_et_android.classes.Etudiant;
import com.example.sqlite_et_android.service.EtudiantService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nom, prenom, id;
    private Button   add, rechercher, supprimer;
    private TextView res;
    private TextView liste;
    void clear() {
        nom.setText("");
        prenom.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EtudiantService es = new EtudiantService(this);
        afficherListe(es);
        nom        = findViewById(R.id.nom);
        prenom     = findViewById(R.id.prenom);
        add        =   findViewById(R.id.bn);
        id         =  findViewById(R.id.id);
        rechercher =   findViewById(R.id.load);
        supprimer  =   findViewById(R.id.delete);
        res        = findViewById(R.id.res);
        liste = findViewById(R.id.liste);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                es.create(new Etudiant(
                        nom.getText().toString(),
                        prenom.getText().toString()));

                clear();

                afficherListe(es);

                Toast.makeText(MainActivity.this,
                        "Étudiant ajouté", Toast.LENGTH_SHORT).show();
            }
        });


        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    res.setText("");
                    Toast.makeText(MainActivity.this,
                            "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Etudiant e = es.findById(Integer.parseInt(txt));
                if (e == null) {
                    res.setText("");
                    Toast.makeText(MainActivity.this,
                            "Étudiant introuvable", Toast.LENGTH_SHORT).show();
                    return;
                }
                res.setText(e.getNom() + " " + e.getPrenom());
            }
        });


        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Etudiant e = es.findById(Integer.parseInt(txt));

                if (e == null) {
                    Toast.makeText(MainActivity.this,
                            "Aucun étudiant à supprimer", Toast.LENGTH_SHORT).show();
                    return;
                }
                es.delete(e);
                afficherListe(es);
                res.setText("");
                Toast.makeText(MainActivity.this,
                        "Étudiant supprimé", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void afficherListe(EtudiantService es) {
        String txt = "";

        for (Etudiant e : es.findAll()) {
            txt += e.getId() + " - " + e.getNom() + " " + e.getPrenom() + "\n";
        }

        liste.setText(txt);
    }
}