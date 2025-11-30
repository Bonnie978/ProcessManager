package com.demo.processmanager;

/**
 * 进程信息实体类
 * 封装系统进程的基本信息
 */
public class ProcessInfo {
    private long pid;           // 进程标识符
    private String name;        // 进程名称
    private double cpu;         // CPU使用率（%）
    private long memory;        // 内存占用（MB）
    private String startTime;   // 启动时间文本

    public ProcessInfo() {
    }

    public ProcessInfo(long pid, String name, double cpu, long memory, String startTime) {
        this.pid = pid;
        this.name = name;
        this.cpu = cpu;
        this.memory = memory;
        this.startTime = startTime;
    }

    // Getter和Setter方法
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ProcessInfo{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}