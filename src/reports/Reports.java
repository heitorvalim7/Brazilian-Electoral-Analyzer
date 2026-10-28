package reports;

import model.Candidate;
import model.Candidate.Gender;
import model.Election;
import model.Party;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class Reports {


    public void printNumberOfElected(){
        int i = 0;

        for(var c : Election.INSTANCE.getCandidates().values()){
            if(c.isElected()){
                i++;
            }
        }
        System.out.println("Número de vagas: " + i);
    }

    public int getNumberOfElected(){
        int i = 0;

        for(var c : Election.INSTANCE.getCandidates().values()){
            if(c.isElected()){
                i++;
            }
        }
        return i;
    }

    public ArrayList<Candidate> getSortedCandidates(){
        ArrayList<Candidate>list = new ArrayList<>(Election.INSTANCE.getCandidates().values());
        Comparator<Candidate>byVotes = (c1, c2) -> Integer.compare(c2.getTotalVotes(), c1.getTotalVotes());
        list.sort(byVotes.thenComparing(Candidate::getdateOfBirth));
        return list;
    }

    public ArrayList<Party> getSortedParties(){
        ArrayList<Party>list = new ArrayList<>(Election.INSTANCE.getParties().values());
        Comparator<Party>byVotes = (p1, p2) -> Integer.compare(p2.getTotalVotes(), p1.getTotalVotes());
        list.sort(byVotes.thenComparing(Party::getPartyNumber));
        return list;
    }

    public ArrayList<Party> getSortedPartiesByCandidate(){
        ArrayList<Party>list = new ArrayList<>(Election.INSTANCE.getParties().values());
        Comparator<Party>byVotes = (p1, p2) -> Integer.compare(p2.getMostVoted().getTotalVotes(), p1.getMostVoted().getTotalVotes());
        list.removeIf(party -> party.getNominalVotes() == 0);
        list.sort(byVotes.thenComparing(Party::getPartyNumber));
        return list;
    }

    public void printElectedCandidates(){
        int i = 1;
        ArrayList<Candidate>list = getSortedCandidates();

        System.out.println("Vereadores eleitos:");
        for(var c : list){
            if(c.isElected()){

                System.out.print(i + " - ");

                if(c.getFederation() != -1){
                    System.out.print("*");
                }

                String formatted = String.format(Locale.forLanguageTag("pt-BR"), "%,d", c.getTotalVotes());

                System.out.println(c.getElectionName() + " (" + c.getParty().getPartyAcronym() + ", " + formatted + " votos)");
                i++;

            }
        }
    }

    public void printMostVotedCandidates(){

        ArrayList<Candidate>list = getSortedCandidates();

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        for(int i = 1; i<=getNumberOfElected(); i++){

            Candidate c = list.get(i-1);

            System.out.print(i + " - ");

            if(c.getFederation() != -1){
                System.out.print("*");
            }

            String formatted = String.format(Locale.forLanguageTag("pt-BR"), "%,d", c.getTotalVotes());

            System.out.println(c.getElectionName() + " (" + c.getParty().getPartyAcronym() + ", " + formatted + " votos)");
        }
    }

    public void printNotElectedMostVoted(){

        ArrayList<Candidate>list = getSortedCandidates();

        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");

        for(int i = 1; i<=getNumberOfElected(); i++){

            Candidate c = list.get(i-1);

            if(!c.isElected()){
                System.out.print(i + " - ");

                if(c.getFederation() != -1){
                    System.out.print("*");
                }

                String formatted = String.format(Locale.forLanguageTag("pt-BR"), "%,d", c.getTotalVotes());

                System.out.println(c.getElectionName() + " (" + c.getParty().getPartyAcronym() + ", " + formatted + " votos)");
            }

        }

    }

    public void printLeastVotedElected(){
        ArrayList<Candidate>list = getSortedCandidates();
        int i = 1;

        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        for(var c : list){

            if(c.isElected() && i>getNumberOfElected()){
                System.out.print(i + " - ");

                if(c.getFederation() != -1){
                    System.out.print("*");
                }

                String formatted = String.format(Locale.forLanguageTag("pt-BR"), "%,d", c.getTotalVotes());

                System.out.println(c.getElectionName() + " (" + c.getParty().getPartyAcronym() + ", " + formatted + " votos)");
            }
            i++;
        }
    }

    public void printPartyAndNumberOfElectedCandidates() {
        ArrayList<Party> list = getSortedParties();
        int i = 1;

        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        for (var p : list) {

            String totalVotes = String.format(
                    Locale.forLanguageTag("pt-BR"),
                    "%,d",
                    p.getTotalVotes()
            );

            String nominalVotes = String.format(
                    Locale.forLanguageTag("pt-BR"),
                    "%,d",
                    p.getNominalVotes()
            );

            String partyListVotes = String.format(
                    Locale.forLanguageTag("pt-BR"),
                    "%,d",
                    p.getPartyListVotes()
            );

            String totalLabel = p.getTotalVotes() > 1 ? " votos" : " voto";
            String nominalLabel = p.getNominalVotes() > 1 ? " nominais" : " nominal";

            System.out.print(
                    i + " - " +
                            p.getPartyAcronym() + " - " +
                            p.getPartyNumber() + ", " +
                            totalVotes + totalLabel + " (" +
                            nominalVotes + nominalLabel + " e " +
                            partyListVotes + " de legenda), "
            );

            if (p.getNumberOfElectedCandidates() > 1) {
                System.out.println(
                        p.getNumberOfElectedCandidates() + " candidatos eleitos"
                );
            } else {
                System.out.println(
                        p.getNumberOfElectedCandidates() + " candidato eleito"
                );
            }

            i++;
        }
    }

    public void printMostAndLeastVotedByParty(){
        System.out.println("Primeiro e último colocados de cada partido:");
        int i = 1;
        for(var p : getSortedPartiesByCandidate()){

            String mostVotedVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getMostVoted().getTotalVotes());
            String leastVotedVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getLeastVoted().getTotalVotes());
            System.out.println(i + " - " + p.getPartyAcronym() + " - " + p.getPartyNumber() + ", " + p.getMostVoted().getElectionName() + " (" + p.getMostVoted().getVotingNumber() + ", " +
                    mostVotedVotes + " votos) / " + p.getLeastVoted().getElectionName() + " (" + p.getLeastVoted().getVotingNumber() + ", " + leastVotedVotes + " votos)");
            i++;

        }
    }

    public void printElectedByAgeGroupPercentage(){
        int underThirty=0;
        int betweenThirtyAndForty=0;
        int betweenFortyAndFifty=0;
        int betweenFiftyAndSixty=0;
        int overSixty=0;
        int total = getNumberOfElected();

        for(var p : Election.INSTANCE.getParties().values()){
            for(var c : p.getCandidates()){
                if(c.isElected()){
                    int age = c.calculateAge();
                    if(age < 30){
                        underThirty++;
                    }
                    else if(age >= 30 && age<40){
                        betweenThirtyAndForty++;
                    }
                    else if(age >= 40 && age<50){
                        betweenFortyAndFifty++;
                    }
                    else if(age >= 50 && age<60){
                        betweenFiftyAndSixty++;
                    }
                    else overSixty++;
                }
            }
        }

        System.out.println("Eleitos, por faixa etária (na data da eleição):");

        double underThirtyPercentage = (100.0 * underThirty) / total;
        double betweenThirtyAndFortyPercentage=(100.0 * betweenThirtyAndForty) / total;
        double betweenFortyAndFiftyPercentage=(100.0 * betweenFortyAndFifty) / total;
        double betweenFiftyAndSixtyPercentage=(100.0 * betweenFiftyAndSixty) / total;
        double overSixtyPercentage=(100.0 * overSixty) / total;

        String printUnderThirtyPercentage = String.format(Locale.forLanguageTag("pt-BR"),"%.2f", underThirtyPercentage);
        String printBetweenThirtyAndFortyPercentage = String.format(Locale.forLanguageTag("pt-BR"),"%.2f", betweenThirtyAndFortyPercentage);
        String printBetweenFortyAndFiftyPercentage = String.format(Locale.forLanguageTag("pt-BR"),"%.2f", betweenFortyAndFiftyPercentage);
        String printBetweenFiftyAndSixtyPercentage = String.format(Locale.forLanguageTag("pt-BR"),"%.2f", betweenFiftyAndSixtyPercentage);
        String printOverSixtyPercentage = String.format(Locale.forLanguageTag("pt-BR"),"%.2f", overSixtyPercentage);

        System.out.println("      Idade < 30: " + underThirty + " (" + printUnderThirtyPercentage +"%)");
        System.out.println("30 <= Idade < 40: " + betweenThirtyAndForty + " (" + printBetweenThirtyAndFortyPercentage +"%)");
        System.out.println("40 <= Idade < 50: " + betweenFortyAndFifty + " (" + printBetweenFortyAndFiftyPercentage +"%)");
        System.out.println("50 <= Idade < 60: " + betweenFiftyAndSixty + " (" + printBetweenFiftyAndSixtyPercentage +"%)");
        System.out.println("60 <= Idade     : " + overSixty + " (" + printOverSixtyPercentage +"%)");


    }

    public void printElectedByGenderPercentage(){
        int men = 0;
        int women = 0;
        int total = getNumberOfElected();

        for(var p : Election.INSTANCE.getParties().values()){
            for(var c : p.getCandidates()){
                if(c.isElected()){
                    Gender gender = c.getGender();
                    if(gender == Gender.MALE){
                        men++;
                    }
                    else if(gender == Gender.FEMALE){
                        women++;
                    }

                }
            }
        }
        double percentageOfMen = (100.0 * men) / total;
        double percentageOfWomen = (100.0 * women) / total;

        String printPercetageOfMen = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", percentageOfMen);
        String printPercetageOfWomen = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", percentageOfWomen);

        System.out.println("Eleitos, por gênero:");
        System.out.println("Feminino: " + women + " (" + printPercetageOfWomen + "%)");
        System.out.println("Masculino: " + men + " (" + printPercetageOfMen + "%)");
    }

    public void printVotes(){
        int totalNominalVotes = 0;
        int totalPartyListVotes = 0;

        for(var p : Election.INSTANCE.getParties().values()){
            totalNominalVotes += p.getNominalVotes();
            totalPartyListVotes += p.getPartyListVotes();
        }

        int total = totalNominalVotes + totalPartyListVotes;
        double percentageOfNominalVotes = (100.0 * totalNominalVotes) / total;
        double percentageOfPartyListVotes = (100.0 * totalPartyListVotes) / total;

        String printTotal = String.format(Locale.forLanguageTag("pt-BR"), "%,d", total);
        String printNominalVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", totalNominalVotes);
        String printPartyListVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", totalPartyListVotes);

        String printPercentageOfNominalVotes = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", percentageOfNominalVotes);
        String printPercentageOfPartyListVotes = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", percentageOfPartyListVotes);

        System.out.println("Total de votos válidos:    " + printTotal);
        System.out.println("Total de votos nominais:   " + printNominalVotes + " (" + printPercentageOfNominalVotes + "%)");
        System.out.println("Total de votos de legenda: " + printPartyListVotes + " (" + printPercentageOfPartyListVotes+ "%)");
    }

    public void printAll(){
        printNumberOfElected();
        System.out.println();
        printElectedCandidates();
        System.out.println();
        printMostVotedCandidates();
        System.out.println();
        printNotElectedMostVoted();
        System.out.println();
        printLeastVotedElected();
        System.out.println();
        printPartyAndNumberOfElectedCandidates();
        System.out.println();
        printMostAndLeastVotedByParty();
        System.out.println();
        printElectedByAgeGroupPercentage();
        System.out.println();
        printElectedByGenderPercentage();
        System.out.println();
        printVotes();
    }
}
