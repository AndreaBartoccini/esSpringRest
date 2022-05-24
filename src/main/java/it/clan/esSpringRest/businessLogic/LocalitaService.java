package it.clan.esSpringRest.businessLogic;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.clan.esSpringRest.dao.LocalitaRepository;
import it.clan.esSpringRest.model.Localita;

@Service
@Transactional
public class LocalitaService {

	@Autowired
	private LocalitaRepository localRepo;

	public List<Localita> trovaTutti() {
		return (List<Localita>) localRepo.findAll();
	}

	public List<Localita> trovaPerId(Integer id) {
		return (List<Localita>) localRepo.findByMyId(id);
	}

	public List<Localita> trovaPerNome(String nome) {
		return (List<Localita>) localRepo.findByNome(nome);
	}

	public List<Localita> trovaPerTemperaturaMinore(Integer temperatura) {
		return (List<Localita>) localRepo.findByTempLessThan(temperatura);
	}

	public List<Localita> trovaPerTemperaturaMaggiore(Integer temperatura) {
		return (List<Localita>) localRepo.findByTempMoreThan(temperatura);
	}

	public Localita aggiungiLocalita(Localita l) {
		return localRepo.save(l);
	}
	
	public Localita aggiornaLocalitaCompleta(Localita newLocalita) {
		List<Localita> listaLocalita = trovaPerId(newLocalita.getId());
		if (listaLocalita.size() != 1) {
			return null;
		}
		Localita oldLocalita = listaLocalita.get(0);
		oldLocalita.setNome(newLocalita.getNome());
		oldLocalita.setTemperatura(newLocalita.getTemperatura());
		localRepo.save(oldLocalita);
		return oldLocalita;
	}

	public Localita aggiornaLocalita(Localita newLocalita) {
		List<Localita> listaLocalita = trovaPerId(newLocalita.getId());
		if (listaLocalita.size() != 1) {
			return null;
		}
		Localita oldLocalita = listaLocalita.get(0);
		if (newLocalita.getNome() != null && !newLocalita.getNome().isEmpty()) {
			oldLocalita.setNome(newLocalita.getNome());
		}
		if (newLocalita.getTemperatura() != null) {
			oldLocalita.setTemperatura(newLocalita.getTemperatura());
		}
		localRepo.save(oldLocalita);
		return oldLocalita;
	}

	public Localita cancellaLocalita(Integer id) {
		List<Localita> listaLocalita = localRepo.findByMyId(id);
		if (listaLocalita.size() != 1) {
			return null;
		}
		Localita localitaDaCancellare = listaLocalita.get(0);
		localRepo.delete(localitaDaCancellare);
		return localitaDaCancellare;
	}

	public void cancellaLocalitaConTempMinore(Integer temperatura) {
		List<Localita> listaLocalita = localRepo.findByTempLessThan(temperatura);
		localRepo.deleteAll(listaLocalita);
	}

	public void cancellaLocalitaConTempMaggiore(Integer temperatura) {
		List<Localita> listaLocalita = localRepo.findByTempMoreThan(temperatura);
		localRepo.deleteAll(listaLocalita);
	}







}
