package com.shimmita.devopssociety;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

public class SpeechClass extends Application implements TextToSpeech.OnInitListener {

    private static final String TAG = "SpeechClass";
    @NonNull
    Context context;
    TextToSpeech textToSpeech;
    @NonNull
    String words = null;

    public SpeechClass(@NonNull Context context,@NonNull String words) {
        this.context = context;
        this.words =words;
        textToSpeech=new TextToSpeech(context,this);

        speakOutFromTextToSpeech();
    }

    private void speakOutFromTextToSpeech() {
        textToSpeech.speak(words, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int afterSuccess = textToSpeech.setLanguage(Locale.US);
            if (afterSuccess == TextToSpeech.LANG_MISSING_DATA ||
                    afterSuccess == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d(TAG, "onInit: error language not supported:" + afterSuccess);
            } else {
                Log.d(TAG, "onInit: Yes Language Supported:" + afterSuccess);
                speakOutFromTextToSpeech();
            }
        }
    }

    @Override
    public void onTerminate() {
        textToSpeech.stop();
        textToSpeech.shutdown();
        super.onTerminate();
    }
}
