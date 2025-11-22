package com.praveen.springai_openai.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
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

    @GetMapping("/transcribe-options")
    public String transcribeWithOptions(){
        AudioTranscriptionResponse audioTranscriptionResponse = openAiAudioTranscriptionModel
                .call(
                        new AudioTranscriptionPrompt(
                                audioFile,
                                OpenAiAudioTranscriptionOptions
                                        .builder()
                                        .prompt("Talking about Spring AI")
                                        .language("en")
                                        .temperature(0.5f)
                                        .responseFormat(OpenAiAudioApi
                                                .TranscriptResponseFormat
                                                .VTT)
                                        .build()
                        )
                );
        return audioTranscriptionResponse
                .getResult()
                .getOutput();
    }


}
