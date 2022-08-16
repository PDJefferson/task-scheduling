package constants;

public enum OnDeleteTask {
    TASK_TO_DELETE("What task do you want to delete?");

    private final String name;

    OnDeleteTask(String otherName){
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
