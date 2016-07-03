package com.alto.crawler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by drcee on 01/07/2016.
 */
public class Artifact {

    public enum ArtifactType {
        PAGE,
        IMAGE,
        EXTERNAL_LINK;
    }

    private final String address;
    private final ArtifactType artifactType;
    private final Set<Artifact> children;

    public Artifact(String address,
                    ArtifactType artifactType,
                    Set<Artifact> children) {
        this.address = address;
        this.artifactType = artifactType;
        this.children = children;
    }

    public Artifact(String address,
                    ArtifactType artifactType) {
        this(address,artifactType,null);
    }

    public String getAddress() {
        return address;
    }

    public ArtifactType getArtifactType() {
        return artifactType;
    }

    public Set<Artifact> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Artifact{" +
                ", address='" + address + '\'' +
                ", artifactType=" + artifactType +
                ", children=" + children +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artifact artifact = (Artifact) o;

        if (address != null ? !address.equals(artifact.address) : artifact.address != null) return false;
        if (artifactType != artifact.artifactType) return false;
        if (children != null ? !children.equals(artifact.children) : artifact.children != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (artifactType != null ? artifactType.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }
}
