package com.praveen.springai_openai.controller;

import org.springframework.ai.openai.audio.speech.SpeechModel;
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
}
