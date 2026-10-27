package model;

import java.util.HashSet;
import java.util.Set;

public class Party {
    private String partyNumber;
    private Set<Candidate> candidates;
    private int partyListVotes;
    private String partyAcronym;

    public Party(String partyNumber, String partyAcronym ) {
        this.partyNumber = partyNumber;
        this.partyAcronym = partyAcronym;
        this.partyListVotes = 0;
        this.candidates = new HashSet<>();
    }

    public void addCandidate(Candidate candidate){
        candidates.add(candidate);
    }

    public String getPartyAcronym() {
        return partyAcronym;
    }

    public Set<Candidate> getCandidates(){
        return this.candidates;
    }
    public String getPartyNumber(){
        return this.partyNumber;
    }

    public int getNominalVotes(){
        int nominalVotes = 0;
        for(var v : candidates){
            nominalVotes += v.getTotalVotes();
        }
        return nominalVotes;
    }

    public int getPartyListVotes() {
        return partyListVotes;
    }

    public void addPartyListVotes(int PartyListVotes){
        if(PartyListVotes > 0){
            this.partyListVotes += PartyListVotes;
        }
    }

    public int getTotalVotes(){
        int totalVotes = getNominalVotes() + getPartyListVotes();
        return totalVotes;
    }

    public int getNumberOfElectedCandidates(){
        int numberOfElected = 0;
        for(var n : candidates){
            if(n.isElected()){
                numberOfElected++;
            }
        }
        return numberOfElected;
    }
}
