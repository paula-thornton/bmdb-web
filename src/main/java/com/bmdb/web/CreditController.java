package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Credit;
import com.bmdb.db.CreditRepository;

@CrossOrigin
@RestController

@RequestMapping("/credits")

public class CreditController {
	
	@Autowired
	private CreditRepository creditRepo;
	
	// list - return all credits
	@GetMapping("/")
	public JsonResponse listCredits() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.findAll());
		} catch  (Exception e ){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// demo use of a Path Variable
	// return one credit for a given ID
	@GetMapping("/{id}")
	public JsonResponse getCredit(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.findById(id)); 
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// add - adds a new credit
	@PostMapping("/")
	public JsonResponse addCredit(@RequestBody Credit c) {
		// add a new credit
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.save(c));
		} catch (DataIntegrityViolationException dive) {	
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());	
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}
	
	// update - update a credit
	@PutMapping("/")
	public JsonResponse updateCredit(@RequestBody Credit c) {
		// update a credit
		JsonResponse jr = null;
		try {
			if (creditRepo.existsById(c.getId())) {
				jr = JsonResponse.getInstance(creditRepo.save(c));
			} else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating credit. "+
						"id: " + c.getId() + " doesn't exist");
			}
		} catch (DataIntegrityViolationException dive) {	
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());	
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// delete - delete a credit
	@DeleteMapping("/{id}")
	public JsonResponse deleteCredit(@PathVariable int id) {
		// delete a credit
		JsonResponse jr = null;
		try {
			if (creditRepo.existsById(id)) {
				creditRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Error deleting credit. "  +
						"id: " + id + " doesn't exist");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		
		return jr;
	}

}
