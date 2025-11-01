package ma.gov.pfe.tp3guidetouristiquealaouikhawla.model;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Interface du service IA pour le guide touristique.
 * LangChain4j génère automatiquement l'implémentation.
 */
public interface GuideTouristique {

    @UserMessage("""
        Donne les informations structurées pour {{lieu}} avec un format JSON.
        Contraintes :
        - "prixMoyenRepas" est une courte chaîne avec devise (ex. "18 EUR").
        - "endroitsAVisiter" contient les {{nb}} principaux endroits à visiter dans {{lieu}}.
        """)
    InfosTouristiques chat(@V("lieu") String lieu, @V("nb") int nbAVisiter);
}