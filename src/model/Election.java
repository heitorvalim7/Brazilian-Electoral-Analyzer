package model;

import java.time.LocalDate;
import java.util.HashMap;


public enum Election {
    INSTANCE;
    private int municipalityCode;
    private LocalDate electionData;
    private HashMap<Integer, Party> parties;
    private HashMap<Integer, Candidate> candidates;

    Election() {
        this.parties = new HashMap<>();
        this.candidates = new HashMap<>();
    }

    public HashMap<Integer, Party> getParties() {
        return parties;
    }

    public HashMap<Integer, Candidate> getCandidates() {
        return candidates;
    }
}