package com.mgckaled.notesapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mgckaled.notesapp.R;
import com.mgckaled.notesapp.database.NotesDatabase;
import com.mgckaled.notesapp.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;

    private String selectedNoteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        // Assign variables to .xml components
        ImageView imageBack = findViewById(R.id.imageBack);

        // arraw back -> Return to the last activity (main activity).
        imageBack.setOnClickListener(v -> onBackPressed());

        // Assign variables to .xml components
        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);

        // Return current DataTime
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm ", Locale.US)
                .format(new Date())
        );

        // Associate save component onclick to saveNote() function
        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(v -> saveNote());

        // Default Note Color
        selectedNoteColor = "#333333";

        // implement method onCreate
        initMiscellaneous();
        setSubtitleIndicatorColor();
    }

    //Validations
    private void saveNote() {
        if(inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputNoteSubtitle.getText().toString().trim().isEmpty()
        && inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create note Object and assing the modifications on it
        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteSubtitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(textDateTime.getText().toString());
        note.setColor(selectedNoteColor);


        // Room does not allow database operation on the Main Thread.
        //That's why we are using async task to save note

        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNoteTask().execute();
    }

    // Method to associate components to .xml file and to set behavior of botton sheet.
    private void initMiscellaneous() {
        final LinearLayout layoutMiscelllaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior
                .from(layoutMiscelllaneous);

        // on click listener -> text "Miscellaneous"
        layoutMiscelllaneous.findViewById(R.id.textMiscellaneous).setOnClickListener(v -> {
            // if miscellaneous current state view is not EXPANDED,
            if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        // Assign miscellaneous layout elements to constants Image view Objects
        final ImageView imageColor1  = layoutMiscelllaneous.findViewById(R.id.imageColor1);
        final ImageView imageColor2  = layoutMiscelllaneous.findViewById(R.id.imageColor2);
        final ImageView imageColor3  = layoutMiscelllaneous.findViewById(R.id.imageColor3);
        final ImageView imageColor4  = layoutMiscelllaneous.findViewById(R.id.imageColor4);
        final ImageView imageColor5  = layoutMiscelllaneous.findViewById(R.id.imageColor5);

        // onclick listener to set each color and icon "ic_done"
        layoutMiscelllaneous.findViewById(R.id.imageColor1).setOnClickListener(v -> {
            selectedNoteColor = "#333333";
            imageColor1.setImageResource(R.drawable.ic_done);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscelllaneous.findViewById(R.id.imageColor2).setOnClickListener(v -> {
            selectedNoteColor = "#FDBE3B";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_done);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscelllaneous.findViewById(R.id.imageColor3).setOnClickListener(v -> {
            selectedNoteColor = "#FF4842";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_done);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscelllaneous.findViewById(R.id.imageColor4).setOnClickListener(v -> {
            selectedNoteColor = "#3A52FC";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_done);
            imageColor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscelllaneous.findViewById(R.id.imageColor5).setOnClickListener(v -> {
            selectedNoteColor = "#000000";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_done);
            setSubtitleIndicatorColor();
        });
    }

    // Method for setting color indicator.
    private void setSubtitleIndicatorColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator
                .getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }
}