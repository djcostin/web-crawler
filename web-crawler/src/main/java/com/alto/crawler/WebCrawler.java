package com.alto.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by drcee on 01/07/2016.
 */
public class WebCrawler {

    private Logger log = LoggerFactory.getLogger(SiteMapBuilder.class);

    private final LinkPatternMatcher linkPatternMatcher;
    private final String startingDomain;
    private final ScannerFactory scannerFactory;
    private final int maxLevels;

    public WebCrawler(String startingDomain,
                      LinkPatternMatcher linkPatternMatcher,
                      ScannerFactory scannerFactory,
                      int maxLevels) {
        this.startingDomain = startingDomain;
        this.linkPatternMatcher = linkPatternMatcher;
        this.scannerFactory = scannerFactory;
        this.maxLevels = maxLevels;
    }

    public WebCrawler(String startingDomain,
                      LinkPatternMatcher linkPatternMatcher,
                      ScannerFactory scannerFactory) {
        this(startingDomain, linkPatternMatcher, scannerFactory,3);
    }

    public WebCrawler(String startingDomain,
                      int maxLevels) {
        this(startingDomain,new LinkPatternMatcher(),new ScannerFactory(),maxLevels);
    }

    public Artifact doCrawl(Scanner scanner) throws Exception {
        prettyPrint(startingDomain);
        return doCrawl(scanner, startingDomain,new HashSet<String>(),1);
    }

    public Artifact doCrawl(Scanner scanner,
                                 String currentAddress,
                                 Set<String> visitedAddresses,
                                 int currentLevel) throws Exception {

        Set<Artifact> artifacts = new HashSet<Artifact>();

        visitedAddresses.add(currentAddress);
        while (currentLevel <= maxLevels && scanner.hasNext()) {
            String line = scanner.nextLine();
            Optional<String> address = linkPatternMatcher.extractAddress(line);

            if (address.isPresent()) {
                if (!visitedAddresses.contains(address.get())) {
                    visitedAddresses.add(address.get());
                    Artifact.ArtifactType linkType = getLinkType(address.get());
                    artifacts.add(new Artifact(
                            address.get(),
                            linkType,
                            linkType.equals(Artifact.ArtifactType.PAGE)?
                                    getChildren(address.get(),visitedAddresses,currentLevel)
                                    :null
                            ));
                }
            }
        }

        Artifact page = new Artifact(currentAddress,
                getLinkType(currentAddress),
                artifacts.isEmpty()?null:artifacts
        );

        prettyPrint(page);

        return page;
    }

    private Set<Artifact> getChildren(String address,
                                      Set<String> visitedAddresses,
                                      int currentLevel) throws Exception {
        return doCrawl(scannerFactory.createScanner(address),
                address,
                visitedAddresses,
                currentLevel++).getChildren();
    }

    private Artifact.ArtifactType getLinkType(String url) {
        return !sameDomain(url)? Artifact.ArtifactType.EXTERNAL_LINK:linkPatternMatcher.determineLinkType(url);
    }

    private boolean sameDomain(String url) {
        return (url.contains(startingDomain));
    }

    private void prettyPrint(Artifact artifact) {
        System.out.println("  " + outputAddress(artifact)+ " - " + artifact.getArtifactType());

        if (artifact.getChildren()!=null) {
            artifact.getChildren()
                    .stream()
                    .forEach(child -> System.out.println("    " + outputAddress(child) + " - " + child.getArtifactType()));
        }
    }

    private void prettyPrint(String address) {
        System.out.println(address);
    }

    private String outputAddress(Artifact artifact) {
        return artifact.getAddress();
    }
}
