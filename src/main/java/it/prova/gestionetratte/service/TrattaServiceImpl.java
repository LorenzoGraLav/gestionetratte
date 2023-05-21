package it.prova.gestionetratte.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;
import it.prova.gestionetratte.web.api.exceptions.TrattaNotFoundException;

@Service
@Transactional
public class TrattaServiceImpl implements TrattaService {
	
	@Autowired
	private TrattaRepository repository;

	
	public List<Tratta> listAllElements(boolean eager) {
		if(eager)
			return (List<Tratta>) repository.findAllTrattaEager();
		
		return (List<Tratta>) repository.findAll();
	}

	
	public Tratta caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	
	public Tratta caricaSingoloElementoEager(Long id) {
		
		return repository.findSingleTrattaEager(id);
	}

	@Transactional
	public Tratta aggiorna(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		repository.findById(idToRemove)
			.orElseThrow(() -> new TrattaNotFoundException("Tratta not found con id: " + idToRemove));
		
	}

	@Override
	public List<Tratta> findByExample(Tratta example) {
		//da implementare
		return this.listAllElements(false);
	}

	@Override
	public List<Tratta> findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}


	@Override
	public List<Tratta> findByStato(Stato stato) {
		return repository.findByStato(stato);
	}

	
}
