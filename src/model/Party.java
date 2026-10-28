package model;

import java.util.ArrayList;
import java.util.Comparator;
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

    public ArrayList<Candidate> getSortedCandidates(){
        ArrayList<Candidate>list = new ArrayList<>(getCandidates());
        Comparator<Candidate>byVotes = (c1, c2) -> Integer.compare(c2.getTotalVotes(), c1.getTotalVotes());
        list.removeIf(c -> c.getTotalVotes() == 0);
        list.sort(byVotes.thenComparing(Candidate::getdateOfBirth));
        return list;
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

    public Candidate getMostVoted(){
        ArrayList<Candidate>list = getSortedCandidates();
        return list.get(0);
    }
    public Candidate getLeastVoted(){
        ArrayList<Candidate>list = getSortedCandidates();
        return list.get(list.size()-1);
    }

}
