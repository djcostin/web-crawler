package com.alto.crawler;

/**
 * Created by drcee on 01/07/2016.
 */
public class Application {


    public static void main(String[] args) throws Exception{

        int maxDepth=3;

        if (args[1] != null) {
            try {
                maxDepth = Integer.parseInt(args[1]);
            }
           catch (Exception e) {

           }

        }

        Artifact site = new SiteMapBuilder()
                .withStartingUrl(args[0])
                .withMaxLevels(maxDepth)
                .build();


    }
}
