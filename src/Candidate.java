import java.time.LocalDate;
import java.time.Period;

public class Candidate {
    private String name;
    private String electionName;
    private int votingNumber;
    private int totalVotes;
    private LocalDate dateOfBirth;
    private Party party;
    private boolean elected;
    private int federation;
    private int gender;

    public Candidate(int gender, int federation, boolean elected, Party party, LocalDate dateOfBirth, int votingNumber, String electionName, String name) {
        this.gender = gender;
        this.federation = federation;
        this.elected = elected;
        this.party = party;
        this.dateOfBirth = dateOfBirth;
        this.totalVotes = 0;
        this.votingNumber = votingNumber;
        this.electionName = electionName;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getElectionName() {
        return electionName;
    }


    public int getVotingNumber() {
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
        return elected;
    }


    public int getFederation() {
        return federation;
    }


    public int getGender() {
        return gender;
    }

    public int calculateAge(LocalDate electionDate){
        Period period = Period.between(this.dateOfBirth, electionDate);
        int age = period.getYears();
        return age;
    }

}
