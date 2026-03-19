package battleship.ai;

import battleship.Game;
import battleship.ai.utils.LLMClient;
import battleship.ai.utils.MoveExtractor;
import battleship.ai.utils.PromptBuilder;

public class AIService {

    private StringBuilder logs = new StringBuilder();
    private Game game;

    public String getMove() {

        System.out.println("AI a pensar...");
        String prompt = PromptBuilder.build(game, logs.toString());

        try {
            String response = LLMClient.call(prompt);
            System.out.println("AI a pensar...");
            return MoveExtractor.extract(response);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "rajada A1 A2 A3";
        }
    }

    public void updateWithResult(String move, String jsonResult) {

        logs.append("Jogada: ").append(move).append("\n");
        logs.append("Resultado: ").append(jsonResult).append("\n\n");
    }
}
