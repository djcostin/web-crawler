package com.alto.crawler;

import org.junit.Test;

import static org.junit.Assert.*;

public class SiteMapBuilderTest {

    @Test
    public void testBuild() throws Exception {

        Artifact site = new SiteMapBuilder()
                .withStartingUrl("http://wiprodigital.com")
                .withMaxLevels(2)
                .build();

        assertEquals("",site);

        System.out.println(site);
    }
}