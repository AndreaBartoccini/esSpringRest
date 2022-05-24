package it.clan.esSpringRest.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.clan.esSpringRest.businessLogic.LocalitaService;
import it.clan.esSpringRest.model.Localita;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/rest/localita", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocalitaController {
	
	@Autowired
	private ConfigurableApplicationContext appContext;
	
	private LocalitaService service;
	
	@Autowired
	public LocalitaController(ConfigurableApplicationContext appContext) {
		this.appContext = appContext;
		this.service = appContext.getBean("localitaService", LocalitaService.class);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<List<Localita>> findAll() {
		try {
			List<Localita> listaLocalita = service.trovaTutti();
			if (listaLocalita.size() == 0) {
				return new ResponseEntity<List<Localita>>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<List<Localita>>(listaLocalita, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Localita> findById(@PathVariable("id") Integer id) {
		try {
			List<Localita> listaLocalita = service.trovaPerId(id);
			if (listaLocalita.size() != 1) {
				return new ResponseEntity<Localita>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Localita>(listaLocalita.get(0), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<Localita> findByNome(@PathVariable("nome") String nome) {
		try {
			List<Localita> listaLocalita = service.trovaPerNome(nome);
			if (listaLocalita.size() != 1) {
				return new ResponseEntity<Localita>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Localita>(listaLocalita.get(0), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByTempLessThan/{temp}")
	public ResponseEntity<List<Localita>> findByTempLessThan(@PathVariable("temp") Integer temperatura) {
		try {
			List<Localita> listaLocalita = service.trovaPerTemperaturaMinore(temperatura);
			if (listaLocalita.size() <= 0) {
				return new ResponseEntity<List<Localita>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Localita>>(listaLocalita, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByTempMoreThan/{temp}")
	public ResponseEntity<List<Localita>> findByTempMoreThan(@PathVariable("temp") Integer temperatura) {
		try {
			List<Localita> listaLocalita = service.trovaPerTemperaturaMaggiore(temperatura);
			if (listaLocalita.size() <= 0) {
				return new ResponseEntity<List<Localita>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Localita>>(listaLocalita, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/addOne", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Localita addOne(@RequestBody Localita l) {
		Localita localita = new Localita(l.getNome(), l.getTemperatura());
		return service.aggiungiLocalita(localita);
	}
	
	@PutMapping(path = "/updateComplete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Localita> updateComplete(@RequestBody Localita l) {
		try {
			if (l.getId() == null) {
				return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
			}
			Localita localitaAggiornata = service.aggiornaLocalitaCompleta(l);
			if (localitaAggiornata == null) {
				return new ResponseEntity<Localita>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Localita>(localitaAggiornata, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping(path = "/updatePartial", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Localita> updatePartial(@RequestBody Localita l) {
		try {
			if (l.getId() == null) {
				return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
			}
			Localita localitaAggiornata = service.aggiornaLocalita(l);
			if (localitaAggiornata == null) {
				return new ResponseEntity<Localita>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Localita>(localitaAggiornata, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/deleteById/{id}")
	public ResponseEntity<Localita> deleteById(@PathVariable Integer id) {
		try {
			if (id == null) {
				return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
			}
			Localita localitaCancellata = service.cancellaLocalita(id);
			if (localitaCancellata == null) {
				return new ResponseEntity<Localita>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Localita>(localitaCancellata, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/deleteByTempLessThan/{temp}")
	public ResponseEntity<Void> deleteByTempLessThan(@PathVariable Integer temp) {
		try {
			if (temp == null) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			service.cancellaLocalitaConTempMinore(temp);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/deleteByTempMoreThan/{temp}")
	public ResponseEntity<Void> deleteByTempMoreThan(@PathVariable Integer temp) {
		try {
			if (temp == null) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			service.cancellaLocalitaConTempMaggiore(temp);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
