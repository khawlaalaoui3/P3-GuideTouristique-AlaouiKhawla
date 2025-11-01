package ma.gov.pfe.tp3guidetouristiquealaouikhawla.llm;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.data.message.SystemMessage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import ma.gov.pfe.tp3guidetouristiquealaouikhawla.model.GuideTouristique;
import ma.gov.pfe.tp3guidetouristiquealaouikhawla.model.InfosTouristiques;

/**
 * Classe métier pour interagir avec le LLM Gemini via LangChain4j.
 * Spécialisée pour le guide touristique.
 */
@ApplicationScoped
public class LlmClient {

    private static final String ROLE_SYSTEME = """
            Tu es un guide touristique expert.
            Réponds STRICTEMENT au schéma demandé.
            N'ajoute aucun texte hors JSON, n'utilise pas Markdown.
            """;

    private final ChatMemory chatMemory;
    private GuideTouristique guideTouristique;

    public LlmClient() {
        // Création d'une mémoire de conversation (fenêtre glissante de 10 messages)
        this.chatMemory = MessageWindowChatMemory.withMaxMessages(10);
    }

    @PostConstruct
    public void init() {
        String apiKey = System.getenv("GEMINI_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("La clé API Gemini (GEMINI_KEY) n'est pas définie.");
        }

        // Création du ChatModel (Gemini)
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.0-flash-exp")
                .temperature(0.7)
                .build();

        // Ajout du rôle système dans la mémoire
        chatMemory.clear();
        chatMemory.add(SystemMessage.from(ROLE_SYSTEME));

        // Création du service IA : LangChain4j génère une implémentation de GuideTouristique
        this.guideTouristique = AiServices.builder(GuideTouristique.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();
    }

    /**
     * Envoie une demande au LLM pour obtenir des informations touristiques.
     *
     * @param villeOuPays le lieu à visiter
     * @param nb le nombre d'endroits à visiter
     * @return les informations touristiques structurées
     */
    public InfosTouristiques envoyer(String villeOuPays, int nb) {
        if (villeOuPays == null || villeOuPays.isBlank()) {
            throw new IllegalArgumentException("Le nom du lieu ne peut pas être vide");
        }
        return guideTouristique.chat(villeOuPays, nb);
    }
}