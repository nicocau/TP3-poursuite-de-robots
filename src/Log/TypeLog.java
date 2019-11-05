package log;

public enum TypeLog {
    DEBUG("DEBUG "),
    INFO("INFO  "),
    WARN("WARN  "),
    ERREUR("ERREUR"),
    FATAL("FATAL ");

    private String name = "";

    TypeLog(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
