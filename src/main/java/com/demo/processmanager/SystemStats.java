package com.demo.processmanager;

/**
 * 系统状态信息实体类
 * 封装系统资源监控信息
 */
public class SystemStats {
    private double cpuUsage;    // CPU使用率
    private double memUsage;    // 系统内存占用比例
    private long jvmHeap;       // JVM当前堆使用量
    private long jvmMaxHeap;    // JVM最大堆大小

    public SystemStats() {
    }

    public SystemStats(double cpuUsage, double memUsage, long jvmHeap, long jvmMaxHeap) {
        this.cpuUsage = cpuUsage;
        this.memUsage = memUsage;
        this.jvmHeap = jvmHeap;
        this.jvmMaxHeap = jvmMaxHeap;
    }

    // Getter和Setter方法
    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(double memUsage) {
        this.memUsage = memUsage;
    }

    public long getJvmHeap() {
        return jvmHeap;
    }

    public void setJvmHeap(long jvmHeap) {
        this.jvmHeap = jvmHeap;
    }

    public long getJvmMaxHeap() {
        return jvmMaxHeap;
    }

    public void setJvmMaxHeap(long jvmMaxHeap) {
        this.jvmMaxHeap = jvmMaxHeap;
    }

    /**
     * 获取JVM堆使用率
     * @return JVM堆使用率（0-1之间的值）
     */
    public double getJvmHeapUsage() {
        if (jvmMaxHeap == 0) {
            return 0.0;
        }
        return (double) jvmHeap / jvmMaxHeap;
    }

    @Override
    public String toString() {
        return "SystemStats{" +
                "cpuUsage=" + cpuUsage +
                ", memUsage=" + memUsage +
                ", jvmHeap=" + jvmHeap +
                ", jvmMaxHeap=" + jvmMaxHeap +
                ", jvmHeapUsage=" + String.format("%.2f", getJvmHeapUsage() * 100) + "%" +
                '}';
    }
}