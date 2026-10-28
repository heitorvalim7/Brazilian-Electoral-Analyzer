package model;

import java.time.LocalDate;
import java.time.Period;

public class Candidate {
    private String electionName;
    private String votingNumber;
    private int totalVotes;
    private LocalDate dateOfBirth;
    private Party party;
    private int elected;
    private int federation;
    private int gender;

    public Candidate(int gender, int federation, int elected, Party party, LocalDate dateOfBirth, String votingNumber, String electionName) {
        this.gender = gender;
        this.federation = federation;
        this.elected = elected;
        this.party = party;
        this.dateOfBirth = dateOfBirth;
        this.totalVotes = 0;
        this.votingNumber = votingNumber;
        this.electionName = electionName;
    }

    public String getElectionName() {
        return electionName;
    }


    public String getVotingNumber() {
        return votingNumber;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void addVotes(int numberOfVotes) {
        if(numberOfVotes > 0){
            this.totalVotes += numberOfVotes;
        }
    }

    public LocalDate getdateOfBirth() {
        return dateOfBirth;
    }


    public Party getParty() {
        return party;
    }


    public boolean isElected() {
        if (elected == 2 || elected == 3) { return true; } 
        else { return false; }
    }


    public int getFederation() {
        return federation;
    }


    public int getGender() {
        return gender;
    }

    public int calculateAge(){

        Period period = Period.between(this.dateOfBirth, Election.INSTANCE.getElectionDate());
        int age = period.getYears();
        return age;
    }

}
