package it.prova.gestionetratte.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.web.api.exceptions.AirbusNotFoundException;

@Service
@Transactional
public class AirbusServiceImpl implements AirbusService {

	@Autowired
	private AirbusRepository repository;
	
	
	

	public List<Airbus> listAllElements() {
		return (List<Airbus>) repository.findAll();
	}

	@Override
	public List<Airbus> listAllElementsEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Airbus caricaSingoloElementoConTratte(Long id) {
		return repository.findByIdEager(id);
	}

	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);

	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.findById(idToRemove).orElseThrow(() -> new AirbusNotFoundException("Airbus not found con id: " + idToRemove));
		repository.deleteById(idToRemove);

	}

	public List<Airbus> findByExample(Airbus example) {
		return repository.findByExample(example);
	}

	public List<Airbus> cercaByDescrizioneECodiceILike(String term) {
		return repository.findByDescrizioneIgnoreCaseContainingOrCodiceIgnoreCaseContainingOrderByCodiceAsc(term, term);
	}

	public Airbus findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	public List<AirbusDTO> listaAirbusEvidenziandoSovrapposizioni() {
		List<AirbusDTO> listaAirbus = AirbusDTO.createAirbusDTOListFromModelList(repository.findAllEager(), true);
			for(AirbusDTO airbusItem : listaAirbus) {
				for(TrattaDTO trattaItem : airbusItem.getTratte()) {
					for(TrattaDTO trattaItem2 : airbusItem.getTratte()) {
						if(trattaItem.getData() == trattaItem2.getData()
								&&(trattaItem2.getOraDecollo().isAfter(trattaItem.getOraDecollo())
								&&(trattaItem2.getOraDecollo().isBefore(trattaItem.getOraAtterraggio())))
								||(trattaItem2.getOraAtterraggio().isAfter(trattaItem.getOraAtterraggio())
								      && trattaItem.getOraAtterraggio().isBefore(trattaItem2.getOraAtterraggio()))) {
							airbusItem.setConSovrapposizioni(true);
						}
							
					}
				}
			}
			return listaAirbus;

	}

}
