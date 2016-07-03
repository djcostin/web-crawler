package com.alto.crawler;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by drcee on 01/07/2016.
 */
public class LinkPatternMatcher {

    private Pattern startHrefPattern = Pattern.compile("href");
    private Pattern startHttpPattern = Pattern.compile("https?://");
    private Pattern endHttpPattern =  Pattern.compile("[\'\"\\s]");

    private Pattern pngPattern = Pattern.compile(".png");
    private Pattern jpegPattern = Pattern.compile(".jpeg");
    private Pattern jpgPattern = Pattern.compile(".jpg");

    public Optional<String> extractAddress(String line) {

        Optional<Integer> haveLink = getIndex(startHrefPattern.matcher(line),0);

        if (haveLink.isPresent()) {
            Optional<Integer> start = getIndex(startHttpPattern.matcher(line),haveLink.get());
            if (start.isPresent()) {
                Optional<Integer> end = getIndex(endHttpPattern.matcher(line),start.get());
                if (end.isPresent()) {
                    return Optional.of(line.substring(start.get(), end.get()));
                }
            }
        }

        return Optional.empty();
    }

    private Optional<Integer> getIndex(Matcher matcher,int offset) {
        return matcher.find(offset) ? Optional.of(new Integer(matcher.start())) : Optional.empty();
    }

    public Artifact.ArtifactType determineLinkType(String address) {
        return isImage(address)? Artifact.ArtifactType.IMAGE: Artifact.ArtifactType.PAGE;
    }

    public boolean isImage(String address) {
        Optional<Integer> count = combine(
                getIndex(pngPattern.matcher(address),0),
                getIndex(jpegPattern.matcher(address),0),
                getIndex(jpgPattern.matcher(address),0)
        );
        return count.isPresent();
    }

    public Optional<Integer> combine(Optional<Integer>...indexes) {

        return Arrays.stream(indexes)
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .findFirst();

    }
}
