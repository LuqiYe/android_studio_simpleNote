package com.simplenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class ListViewNoteAdapter extends ArrayAdapter<String> {
    private MainActivity mainActivity;

    public ListViewNoteAdapter(Context context, List<String> notes, MainActivity mainActivity) {
        super(context, R.layout.listview_note_row, notes);
        this.mainActivity = mainActivity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View noteView = inflater.inflate(R.layout.listview_note_row, parent, false);

        String note = getItem(position);
        EditText editTextNote = noteView.findViewById(R.id.eNote);
        editTextNote.setText(note);

        Button buttonDelete = noteView.findViewById(R.id.bDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.deleteNote(position);
            }
        });

        return noteView;
    }
}
