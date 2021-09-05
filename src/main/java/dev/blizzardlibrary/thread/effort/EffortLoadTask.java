package dev.blizzardlibrary.thread.effort;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class EffortLoadTask implements Runnable {

    private List<EffortLoad> effortLoadList = Collections.synchronizedList(Lists.newLinkedList());

    public void addEffortLoad(EffortLoad effortLoad) {
        effortLoadList.add(effortLoad);
    }

    public void run() {
        effortLoadList.removeIf(EffortLoad::computeThenCheckForScheduling);
    }
}
