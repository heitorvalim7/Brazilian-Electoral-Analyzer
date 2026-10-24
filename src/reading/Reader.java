// TODO: Tratar possíveis exceções de parses

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

    private static int SG_UE;
    private static int CD_CARGO;
    private static int NR_CANDIDATO;
    private static int NM_URNA_CANDIDATO;
    private static int NR_PARTIDO;
    private static int SG_PARTIDO;
    private static int NR_FEDERACAO;
    private static int DT_NASCIMENTO;
    private static int CD_SIT_TOT_TURNO;
    private static int CD_GENERO;


    // Lê o arquivo de canditados e popula os HashMaps de cnadidatos e partidos da instancia de Election
    public static void readCandidates(int municipality, String candidateFile, HashMap<Integer, Candidate> candidates, HashMap<Integer, Party> parties) {


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
            int votingNumber;
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
                votingNumber = Integer.parseInt(columns[NR_CANDIDATO]);
                dateOfBirth = LocalDate.parse(columns[DT_NASCIMENTO], formatter);
                elected = Integer.parseInt(columns[CD_SIT_TOT_TURNO]);
                federation = Integer.parseInt(columns[NR_FEDERACAO]);
                gender = Integer.parseInt(columns[CD_GENERO]);

                // Função própria do hashmap que verifica se a chave já existe, se não existe cria uma nova Party e adiciona no hashmap parties
                party = parties.computeIfAbsent(Integer.parseInt(columns[NR_PARTIDO]), n -> new Party(n, columns[SG_PARTIDO]));
                
                Candidate newCandidate = new Candidate(gender, federation, elected, party, dateOfBirth, votingNumber, electionName);
                candidates.put(votingNumber, newCandidate);

                party.addCandidate(newCandidate);

            }

        } catch (IOException e) {
          System.out.println("File " + e.toString() + " not found");
        }


    }
}

