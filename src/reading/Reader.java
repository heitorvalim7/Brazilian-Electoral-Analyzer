// TODO: Tratar possíveis exceções de parses
// trocar o .replace, professor tinha comentado algo sobre isso?


package reading;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.HashMap;

import model.Candidate;
import model.Party;

public class Reader {

    private static int CD_CARGO;

    private static int SG_UE;
    private static int NR_CANDIDATO;
    private static int NM_URNA_CANDIDATO;
    private static int NR_PARTIDO;
    private static int SG_PARTIDO;
    private static int NR_FEDERACAO;
    private static int DT_NASCIMENTO;
    private static int CD_SIT_TOT_TURNO;
    private static int CD_GENERO;

    private static int CD_MUNICIPIO;
    private static int NR_VOTAVEL;
    private static int QT_VOTOS;


    // Lê o arquivo de canditados e popula os HashMaps de cnadidatos e partidos da instancia de Election
    public static void readCandidates(int municipality, String candidateFile, HashMap<String, Candidate> candidates, HashMap<String, Party> parties) {


        try (FileInputStream fisCandidate = new FileInputStream(candidateFile);
             InputStreamReader isrCandidate = new InputStreamReader(fisCandidate, StandardCharsets.ISO_8859_1);
             BufferedReader brCandidate = new BufferedReader(isrCandidate)) {
            
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String line = brCandidate.readLine();
            String[] header_columns = line.replace("\"", "").split(";");

            for (int i = 0; i < header_columns.length; i++) {
                switch (header_columns[i]) {
                    case "SG_UE" -> SG_UE = i;
                    case "CD_CARGO" -> CD_CARGO = i;
                    case "NR_CANDIDATO" -> NR_CANDIDATO = i;
                    case "NM_URNA_CANDIDATO" -> NM_URNA_CANDIDATO = i;
                    case "NR_PARTIDO" -> NR_PARTIDO = i;
                    case "SG_PARTIDO" -> SG_PARTIDO = i;
                    case "NR_FEDERACAO" -> NR_FEDERACAO = i;
                    case "DT_NASCIMENTO" -> DT_NASCIMENTO = i;
                    case "CD_SIT_TOT_TURNO" -> CD_SIT_TOT_TURNO = i;
                    case "CD_GENERO" -> CD_GENERO = i;
                }
            }

            String electionName;
            String votingNumber;
            LocalDate dateOfBirth;
            Party party;
            int elected;
            int federation;
            int gender;

            while ((line = brCandidate.readLine()) != null) {

                String[] columns = line.replace("\"", "").split(";");

                if (municipality != Integer.parseInt(columns[SG_UE]) || !Objects.equals(columns[CD_CARGO], "13")) {
                    continue;
                }  

                electionName = columns[NM_URNA_CANDIDATO];
                votingNumber = columns[NR_CANDIDATO];
                dateOfBirth = LocalDate.parse(columns[DT_NASCIMENTO], formatter);
                elected = Integer.parseInt(columns[CD_SIT_TOT_TURNO]);
                federation = Integer.parseInt(columns[NR_FEDERACAO]);
                gender = Integer.parseInt(columns[CD_GENERO]);

                // Função própria do hashmap que verifica se a chave já existe, se não existe cria uma nova Party e adiciona no hashmap parties
                party = parties.computeIfAbsent(columns[NR_PARTIDO], n -> new Party(n, columns[SG_PARTIDO]));
                
                Candidate newCandidate = new Candidate(gender, federation, elected, party, dateOfBirth, votingNumber, electionName);
                candidates.put(votingNumber, newCandidate);

                party.addCandidate(newCandidate);

            }

        } catch (IOException e) {
          System.out.println("File " + e.toString() + " not found");
        }


    }

    public static void readVotes(int municipality, String voteFile, HashMap<String, Candidate> candidates, HashMap<String, Party> parties) {

        try (FileInputStream fisVotes = new FileInputStream(voteFile);
             InputStreamReader isrVotes = new InputStreamReader(fisVotes, StandardCharsets.ISO_8859_1);
             BufferedReader brVotes = new BufferedReader(isrVotes)) {

            String line = brVotes.readLine();
            String[] header_columns = line.replace("\"", "").split(";");

            for (int i = 0; i < header_columns.length; i++) {
                switch (header_columns[i]) {
                    case "CD_CARGO" -> CD_CARGO = i;
                    case "CD_MUNICIPIO" -> CD_MUNICIPIO = i;
                    case "NR_VOTAVEL" -> NR_VOTAVEL = i;
                    case "QT_VOTOS" -> QT_VOTOS = i;
                }
            }

            int quantityOfVotes;
            String voteNumber;

            while ((line = brVotes.readLine()) != null) {

                String[] columns = line.replace("\"", "").split(";");
                
                voteNumber = columns[NR_VOTAVEL];
                
                if (municipality != Integer.parseInt(columns[CD_MUNICIPIO]) || !Objects.equals(columns[CD_CARGO], "13") || !isValidVote(voteNumber)) {
                    continue;
                }  
               
                quantityOfVotes = Integer.parseInt(columns[QT_VOTOS]);

                if (voteNumber.length() == 5) {
                    Candidate c = candidates.get(voteNumber);
                    if (c != null) { c.addVotes(quantityOfVotes); }
                }
                else if (voteNumber.length() == 2) {
                    Party p = parties.get(voteNumber);
                    if (p != null) { p.addPartyListVotes(quantityOfVotes); }
                }
            }

        } catch (IOException e) {
            System.out.println("File " + e.toString() + " not found");
        }
    }

    public static boolean isValidVote(String voteNumber) {
        if (voteNumber.equals("95") || voteNumber.equals("96") || voteNumber.equals("97") || voteNumber.equals("98")) {
            return false;
        }
        return true;
    }
}

