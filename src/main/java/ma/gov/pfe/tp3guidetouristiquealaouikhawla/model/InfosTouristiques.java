package ma.gov.pfe.tp3guidetouristiquealaouikhawla.model;

import java.util.List;
import dev.langchain4j.model.output.structured.Description;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"lieu", "endroitsAVisiter", "prixMoyenRepas"})
public record InfosTouristiques(
        @Description("Nom d'une ville ou d'un pays")
        String lieu,

        @Description("Endroits à visiter dans la ville ou le pays")
        List<String> endroitsAVisiter,

        @Description("Prix moyen d'un repas avec la devise de la ville ou du pays")
        String prixMoyenRepas
) { }