package it.prova.gestionetratte;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.service.TrattaService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner {

	@Autowired
	private AirbusService airbusService;

	@Autowired
	private TrattaService trattaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String codiceRoma = "RY001";
		String descrizioneRoma = "RyanAir Roma";

		Airbus airbusRoma = airbusService.findByCodiceAndDescrizione(codiceRoma, descrizioneRoma);

		if (airbusRoma == null) {
			airbusRoma = new Airbus(codiceRoma, descrizioneRoma, LocalDate.of(2022, 8, 23), 58);
			airbusService.inserisciNuovo(airbusRoma);
		}

		Tratta trattaRoma = new Tratta("RM001", "Roma-NewYork", LocalDate.of(2022, 9, 18), LocalTime.of(18, 00, 00),
				LocalTime.of(19, 00, 00), Stato.ATTIVA, airbusRoma);
		if (trattaService.findByCodiceAndDescrizione(trattaRoma.getCodice(), trattaRoma.getDescrizione()).isEmpty())
		    trattaService.inserisciNuovo(trattaRoma);

		String codiceMiami = "US001";
		String descrizioneMiami = "USAir Miami";

		Airbus airbusMiami = airbusService.findByCodiceAndDescrizione(codiceMiami, descrizioneMiami);

		if (airbusMiami == null) {
			airbusMiami = new Airbus(codiceMiami, descrizioneMiami, LocalDate.of(2023, 5, 10), 78);
			airbusService.inserisciNuovo(airbusMiami);
		}

		Tratta trattaMiami = new Tratta("MIA001", "Miami-LosAngeles", LocalDate.of(2023, 5, 12), LocalTime.of(11, 00, 00),
				LocalTime.of(14, 00, 00), Stato.ATTIVA, airbusMiami);
		if (trattaService.findByCodiceAndDescrizione(trattaMiami.getCodice(), trattaMiami.getDescrizione()).isEmpty())
			trattaService.inserisciNuovo(trattaMiami);

	}

}
