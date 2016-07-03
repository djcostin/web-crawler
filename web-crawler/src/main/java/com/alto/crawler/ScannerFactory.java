package com.alto.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by drcee on 02/07/2016.
 */
public class ScannerFactory {

    private static Logger log = LoggerFactory.getLogger(ScannerFactory.class);

    private static final Scanner BLANK_SCANNER = new Scanner("");

    public Scanner createScanner(String url) {

        try {
            return createScanner(new URL(url));
        }
        catch (MalformedURLException mue) {
            log.error("Unable to open url {} ",url);
        }
        return BLANK_SCANNER;
    }

    public Scanner createScanner(URL url)  {
        try {
            InputStream input = url.openStream();
            return new Scanner(input);
        }

        catch (IOException ioe) {
            log.error("Unable to open stream from url {} ",url);
        }

        return BLANK_SCANNER;
    }
}
