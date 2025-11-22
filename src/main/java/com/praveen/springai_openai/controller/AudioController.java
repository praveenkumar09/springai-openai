package com.praveen.springai_openai.controller;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audio")
public class AudioController {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;


    @Value("classpath:SpringAI.mp3")
    Resource audioFile;


    public AudioController(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
    }

    @GetMapping("/transcribe")
    public String transcribe(){
        return openAiAudioTranscriptionModel
                .call(audioFile);
    }


}
