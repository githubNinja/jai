package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.text.completion.CommonCompletionRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.util.JsonSerializer;

import java.util.List;
import java.util.Map;

public class SerializerUtils {

    private SerializerUtils() {}


    public static void outputModel(TextRequest chatRequest, JsonSerializer jsonBodyBuilder) {
        // Note property values are always in snake case for the JSON output.
        jsonBodyBuilder.addAttribute("model", chatRequest.getModel());
    }

    public static void outputTextParams(TextRequest chatRequest, JsonSerializer jsonBodyBuilder) {
        final float temperature = chatRequest.getTemperature();
        if (temperature != 0) {
            jsonBodyBuilder.addAttribute("temperature", temperature);
        }
        final float topP = chatRequest.getTopP();
        if (topP != 0) {
            jsonBodyBuilder.addAttribute("top_p", topP);
        }
        // add completionCount to JSON
        final int completionCount = chatRequest.getCompletionCount();
        if (completionCount != 0) {
            jsonBodyBuilder.addAttribute("n", completionCount);
        }
    }

    public static void outputCompletionParams(CommonCompletionRequest chatRequest, JsonSerializer jsonBodyBuilder) {

        final float frequencyPenalty = chatRequest.getFrequencyPenalty();
        if (frequencyPenalty != 0) {
            jsonBodyBuilder.addAttribute("frequency_penalty", frequencyPenalty);
        }

        float presencePenalty = chatRequest.getPresencePenalty();
        if (presencePenalty != 0) {
            jsonBodyBuilder.addAttribute("presence_penalty", presencePenalty);
        }

        final Map<Integer, Float> logitBias = chatRequest.getLogitBias();
        if (logitBias != null && !logitBias.isEmpty()) {
            jsonBodyBuilder.startNestedListAttribute("logit_bias");
            for (Integer key : logitBias.keySet()) {
                jsonBodyBuilder.startNestedObjectElement();
                jsonBodyBuilder.addAttribute(key, logitBias.get(key));
                jsonBodyBuilder.endObject();
            }
            jsonBodyBuilder.endList();
        }

        final int maxTokens = chatRequest.getMaxTokens();
        if (maxTokens != 0) {
            jsonBodyBuilder.addAttribute("max_tokens", maxTokens);
        }

        // add user to JSON
        final String user = chatRequest.getUser();
        if (user != null && !user.isBlank()) {
            jsonBodyBuilder.addAttribute("user", user);
        }

        // add stop to JSON
        final List<String> stop = chatRequest.getStop();

        if (stop != null && !stop.isEmpty()) {
            jsonBodyBuilder.startNestedListAttribute("stop");
            for (String str : stop) {
                jsonBodyBuilder.addElement(str);
            }
            // remove trailing comma
            jsonBodyBuilder.endList();
        }
    }
}
