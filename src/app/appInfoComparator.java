package com.example.ee202b.taskscheduler202b;

import java.util.Comparator;

class appInfoComparator implements Comparator<appObj> {
    public int compare(appObj x, appObj y) {
        if(x.getM_priority() > y.getM_priority()) {
            return 1;
        }
        else if(x.getM_priority() < y.getM_priority()) {
            return -1;
        }
        else
            return 0;
    }
}
