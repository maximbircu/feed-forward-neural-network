package com.company.utils;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeechConverter {
    private static TextToSpeechConverter ourInstance = new TextToSpeechConverter();
    public static TextToSpeechConverter getInstance() {
        return ourInstance;
    }

    private static final String VOICE_NAME_KEVIN = "kevin16";
    private final Voice voice;

    private TextToSpeechConverter() {
        VoiceManager vm = VoiceManager.getInstance();
        voice = vm.getVoice(VOICE_NAME_KEVIN);
        voice.allocate();
    }

    public void speak(String inputText) {
        if(inputText != null && !inputText.isEmpty()) {
            voice.speak(inputText);
        }
    }
}
