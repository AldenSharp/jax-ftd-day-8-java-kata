package com.cooksys.ftd.test.kata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cooksys.ftd.kata.model.*;
import com.cooksys.ftd.kata.impl.Lepidopterologist;

import org.junit.Assert;
import org.junit.Test;

public class LepidopterologistTest {
	
	@Test
	public void testRegisterSpecies() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel(1, 1), 1);
		Assert.assertEquals(true, l.registerSpecies(monarch));
		Assert.assertEquals(false, l.registerSpecies(monarch));
	}

	@Test
	public void testIsSpeciesRegistered() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel(1, 1), 1);
		Species notMonarch = new Species("not monarch", new GrowthModel(2, 2), 1);
		l.registerSpecies(monarch);
		Assert.assertEquals(true, l.isSpeciesRegistered(monarch));
		Assert.assertEquals(false, l.isSpeciesRegistered(notMonarch));
	}

	@Test
	public void testFindSpeciesForSample() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel(1, 1), 1);
		Species killer = new Species("killer", new GrowthModel(20, 20), 1);
		l.registerSpecies(monarch);
		l.registerSpecies(killer);
		Butterpillar b = new Butterpillar(1, 1);
		Catterfly c1 = new Catterfly(1, 1);
		Catterfly c2 = new Catterfly(20, 20);
		Catterfly c3 = new Catterfly(10, 10);
		Sample yellowMonarch = new Sample(b, c1);
		Sample yellowKiller = new Sample(b, c2);
		Sample yellowNothing = new Sample(b, c3);
		Assert.assertEquals(true, l.findSpeciesForSample(yellowMonarch));
		Assert.assertEquals(monarch, l.findSpeciesForSample(yellowMonarch));
		Assert.assertEquals(true, l.findSpeciesForSample(yellowKiller));
		Assert.assertEquals(killer, l.findSpeciesForSample(yellowKiller));
		Assert.assertEquals(false, l.findSpeciesForSample(yellowNothing));
	}

	@Test
	public void testRecordSample() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel (1, 1), 1);
		l.registerSpecies(monarch);
		Butterpillar b = new Butterpillar(1, 1);
		Catterfly c1 = new Catterfly(1, 1);
		Catterfly c2 = new Catterfly(500, 500);
		Sample yellowMonarch = new Sample(b, c1);
		Sample yellowKiller = new Sample(b, c2);
		Assert.assertEquals(true, l.recordSample(yellowMonarch));
		Assert.assertEquals(false, l.recordSample(yellowMonarch));
		Assert.assertEquals(false, l.recordSample(yellowKiller));
	}

	@Test
	public void testGetSamplesForSpecies() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel (1, 1), 1);
		Species killer = new Species("killer", new GrowthModel (200, 200), 1);
		l.registerSpecies(monarch);
		l.registerSpecies(killer);
		Assert.assertEquals(true, l.getSamplesForSpecies(monarch).isEmpty());
		Assert.assertEquals(true, l.getSamplesForSpecies(killer).isEmpty());
		Butterpillar b = new Butterpillar(1, 1);
		Catterfly c1 = new Catterfly(1, 1);
		Catterfly c2 = new Catterfly(0.9999, 0.9999);
		Catterfly c3 = new Catterfly(200, 200);
		Sample yellowMonarch1 = new Sample(b, c1);
		Sample yellowMonarch2 = new Sample(b, c2);
		Sample yellowKiller = new Sample(b, c3);
		l.recordSample(yellowMonarch1);
		l.recordSample(yellowMonarch2);
		l.recordSample(yellowKiller);
		List<Sample> testMonarch = new ArrayList<Sample>();
		List<Sample> testKiller = new ArrayList<Sample>();
		testMonarch.add(yellowMonarch2);
		testMonarch.add(yellowMonarch1);
		testKiller.add(yellowKiller);
		Assert.assertEquals(testMonarch, l.getSamplesForSpecies(monarch));
		Assert.assertEquals(testKiller, l.getSamplesForSpecies(killer));
		Assert.assertEquals(yellowMonarch2, l.getSamplesForSpecies(monarch).get(0));
		Assert.assertEquals(yellowMonarch1, l.getSamplesForSpecies(monarch).get(1));
	}

	@Test
	public void testGetRegisteredSpecies() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel(1, 1), 1);
		l.registerSpecies(monarch);
		Species tiny = new Species("tiny", new GrowthModel(0.05, 0.05), 0.05);
		l.registerSpecies(tiny);
		Assert.assertEquals(tiny, l.getRegisteredSpecies().get(0));
		Assert.assertEquals(monarch, l.getRegisteredSpecies().get(1));
	}

	@Test
	public void testGetTaxonomy() {
		Lepidopterologist l = new Lepidopterologist();
		Species monarch = new Species("monarch", new GrowthModel(1, 1), 1);
		Species killer = new Species("killer", new GrowthModel(20, 20), 1);
		l.registerSpecies(monarch);
		l.registerSpecies(killer);
		Butterpillar b = new Butterpillar(1, 1);
		Catterfly c1 = new Catterfly(1, 1);
		Catterfly c2 = new Catterfly(0.9999, 0.9999);
		Catterfly c3 = new Catterfly(200, 200);
		Sample yellowMonarch1 = new Sample(b, c1);
		Sample yellowMonarch2 = new Sample(b, c2);
		Sample yellowKiller = new Sample(b, c3);
		l.recordSample(yellowMonarch1);
		l.recordSample(yellowMonarch2);
		l.recordSample(yellowKiller);
		Map<Species, List<Sample>> test = new HashMap<Species, List<Sample>>();
		List<Sample> testMonarch = new ArrayList<Sample>();
		List<Sample> testKiller = new ArrayList<Sample>();
		testMonarch.add(yellowMonarch2);
		testMonarch.add(yellowMonarch1);
		testKiller.add(yellowKiller);
		test.put(monarch, testMonarch);
		test.put(killer, testKiller);
		Assert.assertEquals(test, l.getTaxonomy());
	}

}
