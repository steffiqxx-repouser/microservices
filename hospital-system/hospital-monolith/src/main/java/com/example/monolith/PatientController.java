package com.example.monolith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	private List<Patient> list = new ArrayList<>(
		    Arrays.asList(new Patient((long) 1, "abc"),new Patient((long) 1, "def"),
			new Patient((long)2, "ghi"),
			new Patient((long) 3, "jkl"),
			new Patient((long) 4, "mno"),
			new Patient((long)5, "pqr"),
			new Patient((long)6
					, "stu"),
			new Patient((long)7
					, "vwx"),
			new Patient((long)8
					, "yz"),
			new Patient((long)8
					, "abc")


));
	
	@GetMapping
	public List<Patient> getPatients(){
		return list;
	}
	@GetMapping("/{id}")
    public Patient getPatientById(@PathVariable long id) {
        return list.stream()
            .filter(patient -> patient.getId() == id)
            .findFirst()
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Patient not found: " + id
                )
            );
    }
}
