import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import reading.Reader;
import model.Election;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length == 4){

            int municipalityCode = Integer.parseInt(args[0]);
            String candidatesFile = args[1];
            String votingFile = args[2];
            LocalDate date = LocalDate.parse(args[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Election.INSTANCE.initialize(municipalityCode, date);
            
            Reader.readCandidates(candidatesFile);
            Reader.readVotes(votingFile);

            // for (var party : Election.INSTANCE.getParties().values()) {
            //     System.out.println("Party: " + party.getPartyAcronym() + " - Total Votes: " + party.getTotalVotes());
            //     for (var candidate : party.getCandidates()) {
            //         System.out.println("  Candidate: " + candidate.getVotingNumber() + " - Total Votes: " + candidate.getTotalVotes());
            //     }
            // }

        }

    }
}
