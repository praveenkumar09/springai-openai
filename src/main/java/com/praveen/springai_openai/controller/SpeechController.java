package com.praveen.springai_openai.controller;

import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/speech")
public class SpeechController {

    private final SpeechModel speechModel;

    public SpeechController(SpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    @GetMapping("speech")
    String speech(
            @RequestParam("message") String message
    ) throws IOException {
        byte[] audioInBytes = speechModel.call(message);
        Path path = Paths.get("out.mp3");
        Files.write(path, audioInBytes);
        return "MP3 file " +
                "saved successfully " +
                "to " +path.toAbsolutePath();
    }

    @GetMapping("speech-options")
    String speechOptions(
            @RequestParam("message") String message
    ) throws IOException {
        SpeechResponse speechResponse = speechModel.call(
                new SpeechPrompt(message,
                        OpenAiAudioSpeechOptions.builder()
                                .voice(OpenAiAudioApi
                                        .SpeechRequest.Voice.NOVA)
                                .speed(1.1f)
                                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                                .build())
        );
        Path path = Paths.get("out.mp3");
        Files.write(path, speechResponse.getResult().getOutput());
        return "MP3 file " +
                "saved successfully " +
                "to " +path.toAbsolutePath();
    }
}
