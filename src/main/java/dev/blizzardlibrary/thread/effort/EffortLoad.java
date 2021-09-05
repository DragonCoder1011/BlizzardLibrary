package dev.blizzardlibrary.thread.effort;

public interface EffortLoad {

    void compute();

    default boolean reschedule() {
        return false;
    }

    default boolean computeThenCheckForScheduling() {
        this.compute();
        return !this.reschedule();
    }
}
