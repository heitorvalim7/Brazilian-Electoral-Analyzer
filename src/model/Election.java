package model;

import java.time.LocalDate;
import java.util.HashMap;


public enum Election {
    INSTANCE;
    private int municipalityCode;
    private LocalDate electionData;
    private HashMap<String, Party> parties;
    private HashMap<String, Candidate> candidates;

    Election() {
        this.parties = new HashMap<>();
        this.candidates = new HashMap<>();
    }

    public HashMap<String, Party> getParties() {
        return parties;
    }

    public HashMap<String, Candidate> getCandidates() {
        return candidates;
    }
}