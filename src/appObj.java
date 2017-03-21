package com.example.ee202b.taskscheduler202b;
import java.lang.String;

public class appObj extends TaskScheduler {
    String m_packName;
    int m_priority;

    public String getM_packName() { return m_packName; }
    public void setM_packName(String m_packName) { this.m_packName = m_packName; }
    public int getM_priority() { return m_priority; }
    public void setM_priority(int m_priority) { this.m_priority = m_priority; }
    appObj(String packName, int priority) { m_packName = packName; m_priority = priority; }
}


