import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpeechRecognitionExample {
    public static void main(String[] args) {
        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {

            // Reads the audio file into memory
            Path path = Paths.get("path/to/audio/file.flac");
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            // Builds the audio content
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Configure speech recognition settings
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                    .setLanguageCode("en-US")
                    .build();

            // Performs speech recognition
            RecognizeResponse response = speechClient.recognize(config, audio);
            for (SpeechRecognitionResult result : response.getResultsList()) {
                // Print the transcription
                System.out.println("Transcription: " + result.getAlternativesList().get(0).getTranscript());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
