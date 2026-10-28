    package model;

    import java.time.LocalDate;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.Map;


    public enum Election {
        INSTANCE;
        private int municipalityCode;
        private LocalDate electionDate;
        private Map<String, Party> parties;
        private Map<String, Candidate> candidates;

        Election() {
            this.parties = new HashMap<>();
            this.candidates = new HashMap<>();
        }

        public Party addParty(String partyNumber, String partyAcronym){
            Party newParty = this.parties.computeIfAbsent(partyNumber, n -> new Party(n, partyAcronym));
            return newParty;
        }

        public void addCandidate(int gender, int federation, int elected, Party party, LocalDate dateOfBirth, String votingNumber, String electionName){
            Candidate newCandidate = new Candidate(gender, federation, elected, party, dateOfBirth, votingNumber, electionName);
            party.addCandidate(this.candidates.computeIfAbsent(votingNumber, n -> newCandidate));
        }

        public void sumVotesCandidate(String voteNumber, int numberOfVotes){
            Candidate candidate = this.candidates.get(voteNumber);
            if(candidate != null){
                candidate.addVotes(numberOfVotes);
            }
        }

        public void sumVotesParty(String voteNumber, int numberOfVotes){
            Party party = this.parties.get(voteNumber);
            if(party != null){
                party.addPartyListVotes(numberOfVotes);
            }
        }

        public Map<String, Party> getParties() {
            return Collections.unmodifiableMap(parties);
        }

        public Map<String, Candidate> getCandidates() {
            return Collections.unmodifiableMap(candidates);
        }

        public Party getParty(String partyNumber){
            return parties.get(partyNumber);
        }

        public Candidate getCandidate(String votingNumber){
            return candidates.get(votingNumber);
        }

        public int getMunicipalityCode() {
            return municipalityCode;
        }

        public LocalDate getElectionDate() {
            return electionDate;
        }

        public void initialize(int municipalityCode, LocalDate electionDate){
            this.municipalityCode = municipalityCode;
            this.electionDate = electionDate;
        }
    }