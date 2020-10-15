package com.github.bartrina.bootcamp.types;

import java.util.Arrays;
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
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Address)) {
            return false;
        }

        Address c = (Address) o;

        return Arrays.deepEquals(lines.toArray(), c.lines.toArray());
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for(String line : lines)
            buff.append(line).append('\n');
        return buff.toString();
    }
}
