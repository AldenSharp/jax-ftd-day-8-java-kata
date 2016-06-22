package com.cooksys.ftd.kata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.cooksys.ftd.kata.ILepidopterologist;
import com.cooksys.ftd.kata.model.Sample;
import com.cooksys.ftd.kata.model.Species;

public class Lepidopterologist implements ILepidopterologist {
	
	private List<Species> registeredSpecies;
	private List<Sample> registeredSamples;

	@Override
	public boolean registerSpecies(Species species) {
		if(this.registeredSpecies.contains(species)) {
			return false;
		}
		this.registeredSpecies.add(species);
		Collections.sort(this.registeredSpecies);
		return true;
	}

	@Override
	public boolean isSpeciesRegistered(Species species) {
		return this.registeredSpecies.contains(species);
	}

	@Override
	public Optional<Species> findSpeciesForSample(Sample sample) {
		for(Species s : registeredSpecies) {
			if(s.isMember(sample.getGrowthModel())) {
				return Optional.of(s);
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean recordSample(Sample sample) {
		for(Species s : registeredSpecies) {
			if(s.isMember(sample.getGrowthModel())) {
				this.registeredSamples.add(sample);
				Collections.sort(registeredSamples);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Sample> getSamplesForSpecies(Species species) {
		List<Sample> output = new ArrayList<Sample>();
		for(Sample s : registeredSamples) {
			if(species.isMember(s.getGrowthModel())) {
				output.add(s);
			}
		}
		return output;
	}

	@Override
	public List<Species> getRegisteredSpecies() {
		return this.registeredSpecies;
	}

	@Override
	public Map<Species, List<Sample>> getTaxonomy() {
		Map<Species, List<Sample>> output = new HashMap<Species, List<Sample>>();
		for(Species s : registeredSpecies) {
			output.put(s, getSamplesForSpecies(s));
		}
		return output;
	}

}
