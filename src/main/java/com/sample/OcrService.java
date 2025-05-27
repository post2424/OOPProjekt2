package com.sample;

public interface OcrService {
    /**
     * Recognize text from image data (e.g., screenshot bytes).
     * @param imageData image bytes in a standard format (e.g., PNG or JPEG)
     * @return recognized text
     */
    String recognizeText(byte[] imageData);
}