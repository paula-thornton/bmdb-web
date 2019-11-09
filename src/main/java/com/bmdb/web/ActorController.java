package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepository;

@CrossOrigin
@RestController

@RequestMapping("/actors")

public class ActorController {
	
	@Autowired
	private ActorRepository actorRepo;
	
	// list - return all actors
	@GetMapping("/")
	public JsonResponse listActors() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.findAll());
		} catch  (Exception e ){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// demo use of a Path Variable
	// return one actor for a given ID
	@GetMapping("/{id}")
	public JsonResponse getActor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.findById(id)); 
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// add - adds a new actor
	@PostMapping("/")
	public JsonResponse addActor(@RequestBody Actor a) {
		// add a new actor
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.save(a));
		} catch (DataIntegrityViolationException dive) {	
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// update - update an actor
	@PutMapping("/")
	public JsonResponse updateActor(@RequestBody Actor a) {
		// update an actor
		JsonResponse jr = null;
		try {
			if (actorRepo.existsById(a.getId())) {
				jr = JsonResponse.getInstance(actorRepo.save(a));
			} else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating actor. "+
						"id: " + a.getId() + " doesn't exist");
			}
		} catch (DataIntegrityViolationException dive) {	
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// delete - delete an actor
	@DeleteMapping("/{id}")
	public JsonResponse deleteActor(@PathVariable int id) {
		// delete an actor
		JsonResponse jr = null;
		try {
			if (actorRepo.existsById(id)) {
				actorRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Error deleting actor. "  +
						"id: " + id + " doesn't exist");
			}
		} catch (DataIntegrityViolationException dive) {	
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());	
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		
		return jr;
	}

}
