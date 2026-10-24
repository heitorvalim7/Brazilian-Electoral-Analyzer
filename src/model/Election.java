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

        public void addParty(Party party){
            this.parties.put(party.getPartyNumber(), party);
        }

        public void addCandidate(Candidate candidate){
            this.candidates.put(candidate.getVotingNumber(), candidate);
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