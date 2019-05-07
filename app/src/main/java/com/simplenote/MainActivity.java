package com.simplenote;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> notes;
    private ListViewNoteAdapter notesAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notes = new ArrayList<>();
        notesAdapter = new ListViewNoteAdapter(getApplicationContext(), notes, this);
        ((ListView)findViewById(R.id.notesListView)).setAdapter(notesAdapter);

        loadNotes();
    }

    public void deleteNote(int position) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(notes.get(position));
        editor.commit();

        notes.remove(position);
        notesAdapter.notifyDataSetChanged();
    }

    public void buttonAddNoteClick(View view) {
        EditText editTextNote = findViewById(R.id.eNote);
        String note = editTextNote.getText().toString();

        if(notes.contains(note)) {
            Toast.makeText(this, "You have that note previously added.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(note, note);
        editor.commit();

        notes.add(note);
        notesAdapter.notifyDataSetChanged();
        editTextNote.setText("");
    }

    public void buttonSearchNotesClick(View view) {
        EditText editTextNote = findViewById(R.id.eNote);
        String note = editTextNote.getText().toString();

        if(note.isEmpty()) {
            loadNotes();
            return;
        }

        loadNotes();

        for(int i = notes.size() - 1; i >= 0; i--) {
            if(!notes.get(i).toLowerCase().contains(note.toLowerCase())) {
                notes.remove(i);
            }
        }

        notesAdapter.notifyDataSetChanged();
    }

    private void loadNotes() {
        notes.clear();
        sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);

        for(String note: sharedPreferences.getAll().keySet()) {
            notes.add(note);
        }

        notesAdapter.notifyDataSetChanged();
    }
}
