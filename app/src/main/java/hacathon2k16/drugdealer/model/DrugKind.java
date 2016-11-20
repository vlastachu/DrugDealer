package hacathon2k16.drugdealer.model;

/**
 * Created by vlastachu on 19/11/16.
 */

public class DrugKind {
    String name;
    String description;
    String container;
    ActiveSubstance activeSubstance;

    public DrugKind(String name, String description, String container, ActiveSubstance activeSubstance) {
        this.name = name;
        this.description = description;
        this.container = container;
        this.activeSubstance = activeSubstance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ActiveSubstance getActiveSubstance() {
        return activeSubstance;
    }
}
