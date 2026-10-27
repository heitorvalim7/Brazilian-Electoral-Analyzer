package reports;

import model.Candidate;
import model.Election;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    public void printElectedCandidates(){
        int i = 1;
        ArrayList<Candidate>list = new ArrayList<>(Election.INSTANCE.getCandidates().values());
        Comparator<Candidate>byVotes = (c1, c2) -> Integer.compare(c2.getTotalVotes(), c1.getTotalVotes());
        list.sort(byVotes.thenComparing(Candidate::getdateOfBirth));

        for(var c : list){
            if(c.isElected()){

                System.out.print(i + " - ");

                if(c.getFederation() != -1){
                    System.out.print("*");
                }

                System.out.println(c.getElectionName() + "(" + c.getParty().getPartyAcronym() + ", " + c.getTotalVotes() + "votos)");
                i++;

            }
        }
    }
}
