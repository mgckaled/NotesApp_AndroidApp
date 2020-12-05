package com.mgckaled.notesapp.listeners;

import com.mgckaled.notesapp.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
