package org.example.AlgoGraphs.DataStructures.Pair;

import lombok.Getter;

import java.util.Objects;


@Getter
public class Pair<F, S> implements Comparable<Pair<F, S>> {
    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(Pair<F, S> other) {
        int firstComparison = ((Comparable<F>) this.first).compareTo(other.first);
        if (firstComparison != 0) {
            return firstComparison;
        }
        return ((Comparable<S>) this.second).compareTo(other.second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}