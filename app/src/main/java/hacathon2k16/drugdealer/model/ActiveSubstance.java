package hacathon2k16.drugdealer.model;

/**
 * Created by vlastachu on 19/11/16.
 */
public class ActiveSubstance {
    String name;
    String description;

    public ActiveSubstance(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
