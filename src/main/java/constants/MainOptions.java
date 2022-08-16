package constants;

public enum MainOptions {

    OPTION_ONE("(a) add tasks"),
    OPTION_TWO("(b) delete task"),
    OPTION_THREE("(c) add a dependency"),
    OPTION_FOUR("(d) quit");

    private final String name;

    MainOptions(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
