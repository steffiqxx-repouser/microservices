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
		    Arrays.asList(new Patient((long) 1, "Ananya Chandra Kinky Rat,Hasnain Kothawala +1"),new Patient((long) 1, "Ananya Chandra Kinky Rat,Hasnain Kothawala +1"),
			new Patient((long)2, "Rahul Kale Horny"),
			new Patient((long) 3, "bIG rAJANIKANTH"),
			new Patient((long) 4, "Rahul Roy and Raghuram Rajan,Amrita Singh,Angelina Jolie proper filth"),
			new Patient((long)5, "Kangana Ranaut,Anushka Sharma,Allu Arjun,Hrithik roshan,kejriwal,sitharaman-filthy horny and stinky"),
			new Patient((long)6
					, "Kapil Sharma and Karthik Aryan -hidden public welfare department"),
			new Patient((long)7
					, "more filthy 28 now i am 100% sure its two earth signs"),
			new Patient((long)8
					, "the radhe and idfc credit card a,along with 9 as and 2 as ,so now you got hidden snakes in where bubbu android? in 9s"),
			new Patient((long)8
					, "the mrunal thakur,amitabh bacchan,anurag kashap and sourav ganguly along with footballer 1 so now you got hidden snakes in where bubbu android? in 9s")


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
