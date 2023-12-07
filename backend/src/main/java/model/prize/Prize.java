package model.prize;

public abstract class Prize {
    protected final PrizeType type;
    protected final String name;
    protected final String description;


    protected Prize(PrizeType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public PrizeType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}
