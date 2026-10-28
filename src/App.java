import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import reading.Reader;
import model.Election;
import reports.Reports;

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
            Reports reports = new Reports();
            reports.printAll();

        }

    }
}
