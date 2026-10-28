package reports;

import model.Candidate;
import model.Election;
import model.Party;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

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

                System.out.println(c.getElectionName() + "(" + c.getParty().getPartyAcronym() + ", " + formatted + "votos)");
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

            System.out.println(c.getElectionName() + "(" + c.getParty().getPartyAcronym() + ", " + formatted + "votos)");
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

                System.out.println(c.getElectionName() + "(" + c.getParty().getPartyAcronym() + ", " + formatted + "votos)");
            }

        }

    }

    public void printLeastVotedElected(){
        int n = getNumberOfElected();
        ArrayList<Candidate>list = getSortedCandidates();
        int i = 1;

        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        for(var c : list){
            System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
            if(c.isElected() && i>getNumberOfElected()){
                System.out.print(i + " - ");

                if(c.getFederation() != -1){
                    System.out.print("*");
                }

                String formatted = String.format(Locale.forLanguageTag("pt-BR"), "%,d", c.getTotalVotes());

                System.out.println(c.getElectionName() + "(" + c.getParty().getPartyAcronym() + ", " + formatted + "votos)");
            }
            i++;
        }
    }

    public void printPartyAndNumberOfElectedCandidates(){
        ArrayList<Party>list = getSortedParties();
        int i = 1;

        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        for(var p : list){
            String totalVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getTotalVotes());
            String nominalVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getNominalVotes());
            String partyListVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getPartyListVotes());

            System.out.print(i + " - " + p.getPartyAcronym() + " - " + p.getPartyNumber() + ", "  + totalVotes + " votos " + "(" + nominalVotes + " nominais e " + partyListVotes + " de legenda), ");
            if(p.getNumberOfElectedCandidates() > 1){
                System.out.println(p.getNumberOfElectedCandidates() + " candidatos eleitos");
            }
            else System.out.println(p.getNumberOfElectedCandidates() + " candidato eleito");
            i++;
        }

    }

    public void printMostAndLeastVotedByParty(){
        System.out.println("Primeiro e último colocados de cada partido:");
        int i = 1;
        for(var p : getSortedParties()){
            if(p.getMostVoted().getTotalVotes() > 0){
                String mostVotedVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getMostVoted().getTotalVotes());
                String leastVotedVotes = String.format(Locale.forLanguageTag("pt-BR"), "%,d", p.getLeastVoted().getTotalVotes());
                System.out.println(i+" - " + p.getPartyAcronym() + " - " + p.getPartyNumber() + "," + p.getMostVoted().getElectionName() + " (" + p.getMostVoted().getVotingNumber() + ", " +
                        mostVotedVotes + ") / " + p.getLeastVoted().getElectionName() + "(" + p.getLeastVoted().getVotingNumber() + ", " + p.getLeastVoted().getTotalVotes() + "votos)");
            }
            i++;
        }
    }
}
