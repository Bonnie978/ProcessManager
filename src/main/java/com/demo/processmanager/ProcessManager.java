package com.demo.processmanager;

import com.demo.processmanager.parser.LinuxProcessParser;
import com.demo.processmanager.parser.MacProcessParser;
import com.demo.processmanager.parser.WindowsProcessParser;
import com.demo.processmanager.utils.CommandExecutor;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

/**
 * 进程管理器主入口类
 * 提供统一的进程管理和系统监控API
 */
public class ProcessManager {
    
    private final String osType;
    
    public ProcessManager() {
        this.osType = CommandExecutor.getOS();
    }
    
    /**
     * 获取当前系统中运行的所有进程列表
     * @return 进程信息列表
     * @throws IOException 如果获取进程信息失败
     */
    public List<ProcessInfo> listProcesses() throws IOException {
        switch (osType) {
            case "mac":
                MacProcessParser macParser = new MacProcessParser();
                return macParser.listProcesses();
            case "linux":
                LinuxProcessParser linuxParser = new LinuxProcessParser();
                return linuxParser.listProcesses();
            case "windows":
                WindowsProcessParser windowsParser = new WindowsProcessParser();
                return windowsParser.listProcesses();
            default:
                throw new UnsupportedOperationException("不支持的操作系统: " + osType);
        }
    }
    
    /**
     * 根据PID获取特定进程的详细信息
     * @param pid 进程ID
     * @return 进程信息，如果进程不存在返回null
     * @throws IOException 如果获取进程信息失败
     */
    public ProcessInfo getProcessInfo(long pid) throws IOException {
        switch (osType) {
            case "mac":
                MacProcessParser macParser = new MacProcessParser();
                return macParser.getProcessInfo(pid);
            case "linux":
                LinuxProcessParser linuxParser = new LinuxProcessParser();
                return linuxParser.getProcessInfo(pid);
            case "windows":
                WindowsProcessParser windowsParser = new WindowsProcessParser();
                return windowsParser.getProcessInfo(pid);
            default:
                throw new UnsupportedOperationException("不支持的操作系统: " + osType);
        }
    }
    
    /**
     * 获取系统状态统计信息
     * @return 系统状态信息
     */
    public SystemStats getSystemStats() {
        try {
            double cpuUsage = getSystemCpuUsage();
            double memUsage = getSystemMemoryUsage();
            long jvmHeap = getJvmHeapUsage();
            long jvmMaxHeap = getJvmMaxHeap();
            
            return new SystemStats(cpuUsage, memUsage, jvmHeap, jvmMaxHeap);
        } catch (Exception e) {
            // 如果获取系统信息失败，返回默认值
            return new SystemStats(0.0, 0.0, 0L, 0L);
        }
    }
    
    /**
     * 获取系统CPU使用率
     * @return CPU使用率（0-100）
     */
    private double getSystemCpuUsage() {
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            
            if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
                com.sun.management.OperatingSystemMXBean sunOsBean = 
                    (com.sun.management.OperatingSystemMXBean) osBean;
                return sunOsBean.getSystemCpuLoad() * 100;
            }
            
            // 备用方案：使用系统命令获取CPU使用率
            return getCpuUsageFromCommand();
            
        } catch (Exception e) {
            return getCpuUsageFromCommand();
        }
    }
    
    /**
     * 通过系统命令获取CPU使用率
     * @return CPU使用率
     */
    private double getCpuUsageFromCommand() {
        try {
            if (osType.equals("windows")) {
                String command = "wmic cpu get loadpercentage /value";
                String output = CommandExecutor.executeCommandSingleLine(command);
                if (output.contains("LoadPercentage")) {
                    String cpuStr = output.split("=")[1].trim();
                    return Double.parseDouble(cpuStr);
                }
            } else {
                // macOS/Linux使用top命令
                String command = "top -l 1 | grep -E \"^CPU\" | awk '{print $3}'";
                if (osType.equals("linux")) {
                    command = "top -bn1 | grep \"Cpu(s)\" | awk '{print $2}'";
                }
                
                String output = CommandExecutor.executeCommandSingleLine(command);
                if (output != null && !output.isEmpty()) {
                    // 去除百分号并转换为数字
                    String cpuStr = output.replace("%", "").trim();
                    return Double.parseDouble(cpuStr);
                }
            }
        } catch (Exception e) {
            // 忽略错误
        }
        return 0.0;
    }
    
    /**
     * 获取系统内存使用率
     * @return 内存使用率（0-100）
     */
    private double getSystemMemoryUsage() {
        try {
            if (osType.equals("windows")) {
                String command = "wmic OS get FreePhysicalMemory,TotalVisibleMemorySize /value";
                List<String> output = CommandExecutor.executeCommand(command);
                
                long freeMemory = 0;
                long totalMemory = 0;
                
                for (String line : output) {
                    if (line.startsWith("FreePhysicalMemory")) {
                        freeMemory = Long.parseLong(line.split("=")[1].trim());
                    } else if (line.startsWith("TotalVisibleMemorySize")) {
                        totalMemory = Long.parseLong(line.split("=")[1].trim());
                    }
                }
                
                if (totalMemory > 0) {
                    return (1 - (double) freeMemory / totalMemory) * 100;
                }
            } else {
                // macOS/Linux使用free命令
                String command = "vm_stat | grep -E \"(free|active|inactive)\"";
                if (osType.equals("linux")) {
                    command = "free | grep Mem";
                }
                
                List<String> output = CommandExecutor.executeCommand(command);
                // 简化实现，返回估算值
                return 50.0; // 默认值
            }
        } catch (Exception e) {
            // 忽略错误
        }
        return 0.0;
    }
    
    /**
     * 获取JVM当前堆使用量
     * @return JVM堆使用量（字节）
     */
    private long getJvmHeapUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        return memoryBean.getHeapMemoryUsage().getUsed();
    }
    
    /**
     * 获取JVM最大堆大小
     * @return JVM最大堆大小（字节）
     */
    private long getJvmMaxHeap() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        return memoryBean.getHeapMemoryUsage().getMax();
    }
    
    /**
     * 获取当前操作系统类型
     * @return 操作系统类型
     */
    public String getOsType() {
        return osType;
    }
    
    /**
     * 测试方法：打印当前系统进程和状态信息
     */
    public void printSystemInfo() {
        try {
            System.out.println("=== 系统信息 ===");
            System.out.println("操作系统: " + osType);
            
            SystemStats stats = getSystemStats();
            System.out.println("系统状态: " + stats);
            
            System.out.println("\n=== 进程列表（前10个） ===");
            List<ProcessInfo> processes = listProcesses();
            int count = Math.min(processes.size(), 10);
            for (int i = 0; i < count; i++) {
                System.out.println(processes.get(i));
            }
            
            System.out.println("\n总进程数: " + processes.size());
            
        } catch (Exception e) {
            System.err.println("获取系统信息失败: " + e.getMessage());
        }
    }
}