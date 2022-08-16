package constants;

public enum OnAddTaskOptions {
    TASK_TO_ADD("""
            What tasks to you want to add?
            Separate each task with a space.\s
            Enter “none” if there is no task to be added.""");

    private final String name;

    OnAddTaskOptions(String otherName) {
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
