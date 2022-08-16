package constants;

public enum OnAddDependency {
    DEPENDENCY_TO_ADD("What dependency do you want to add?");

    private final String name;

    OnAddDependency(String otherName){
        this.name = otherName;
    }
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
