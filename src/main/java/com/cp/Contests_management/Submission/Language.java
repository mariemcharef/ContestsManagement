package com.cp.Contests_management.Submission;

public enum Language {
    C(50, "c"),
    CPP(52, "cpp"),
    JAVA(62, "java"),
    PYTHON(71, "python3");

    private final int judge0Id; // Judge0 language ID
    private final String name;  // Language name

    Language(int judge0Id, String name) {
        this.judge0Id = judge0Id;
        this.name = name;
    }

    public int getJudge0Id() {
        return judge0Id;
    }

    public String getName() {
        return name;
    }

    // Optional: Get enum by Judge0 ID
    public static Language fromJudge0Id(int judge0Id) {
        for (Language language : Language.values()) {
            if (language.getJudge0Id() == judge0Id) {
                return language;
            }
        }
        throw new IllegalArgumentException("Invalid Judge0 language ID: " + judge0Id);
    }


}