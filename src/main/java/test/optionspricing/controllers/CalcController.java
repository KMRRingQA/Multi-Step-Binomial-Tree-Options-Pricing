package test.optionspricing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import test.optionspricing.dtos.CalcDto;
import test.optionspricing.services.CalcService;

@RestController
@CrossOrigin
@RequestMapping("/calc")
public class CalcController {

    private CalcService service;

	@Autowired
	public CalcController(CalcService service) {
		this.service = service;
	}

    // calculate
	@PostMapping("/price")
	public ResponseEntity<Double> calculate(@RequestBody CalcDto calcDto) {
		return new ResponseEntity<>(this.service.calc(calcDto), HttpStatus.OK);
	}
}