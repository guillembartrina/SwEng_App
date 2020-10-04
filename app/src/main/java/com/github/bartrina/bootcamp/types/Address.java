package com.github.bartrina.bootcamp.types;

import java.util.List;

public final class Address {
    private final List<String> lines;

    public Address(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for(String line : lines)
            buff.append(line).append('\n');
        return buff.toString();
    }
}
