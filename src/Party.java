import java.util.HashSet;
import java.util.Set;

public class Party {
    private int partyNumber;
    private Set<Candidate> candidates;
    int electedCandidates;
    int nominalVotes;
    int partyListVotes;

    public Party(int partyNumber) {
        this.partyNumber = partyNumber;
        this.candidates = new HashSet<>();
    }

    public void addCandidate(Candidate candidate){
        candidates.add(candidate);
    }

    public Set<Candidate> getCandidates(){
        return this.candidates;
    }
    public int getPartyNumber(){
        return this.partyNumber;
    }

    public void setTotalVotes(){
        for(var v : candidates){
            this.nominalVotes += v.getTotalVotes();
        }
    }
}
