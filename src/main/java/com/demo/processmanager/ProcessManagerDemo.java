package com.demo.processmanager;

/**
 * ProcessManager演示类
 * 用于测试和演示库的功能
 */
public class ProcessManagerDemo {
    
    public static void main(String[] args) {
        ProcessManager processManager = new ProcessManager();
        
        System.out.println("=== ProcessManager 演示程序 ===");
        System.out.println("检测到操作系统: " + processManager.getOsType());
        
        // 演示系统状态获取
        demoSystemStats(processManager);
        
        // 演示进程列表获取
        demoProcessList(processManager);
        
        // 演示特定进程信息获取
        demoSpecificProcess(processManager);
    }
    
    /**
     * 演示系统状态获取功能
     */
    private static void demoSystemStats(ProcessManager processManager) {
        System.out.println("\n=== 系统状态信息 ===");
        SystemStats stats = processManager.getSystemStats();
        System.out.println(stats);
    }
    
    /**
     * 演示进程列表获取功能
     */
    private static void demoProcessList(ProcessManager processManager) {
        System.out.println("\n=== 进程列表演示 ===");
        try {
            var processes = processManager.listProcesses();
            System.out.println("总进程数: " + processes.size());
            
            // 显示前5个进程
            int count = Math.min(processes.size(), 5);
            System.out.println("前" + count + "个进程:");
            for (int i = 0; i < count; i++) {
                System.out.println("  " + processes.get(i));
            }
        } catch (Exception e) {
            System.err.println("获取进程列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 演示特定进程信息获取功能
     */
    private static void demoSpecificProcess(ProcessManager processManager) {
        System.out.println("\n=== 特定进程信息演示 ===");
        try {
            // 获取当前Java进程的信息
            long currentPid = ProcessHandle.current().pid();
            ProcessInfo currentProcess = processManager.getProcessInfo(currentPid);
            
            if (currentProcess != null) {
                System.out.println("当前Java进程信息:");
                System.out.println("  PID: " + currentProcess.getPid());
                System.out.println("  名称: " + currentProcess.getName());
                System.out.println("  CPU使用率: " + currentProcess.getCpu() + "%");
                System.out.println("  内存占用: " + currentProcess.getMemory() + " MB");
                System.out.println("  启动时间: " + currentProcess.getStartTime());
            } else {
                System.out.println("无法获取当前进程信息");
            }
        } catch (Exception e) {
            System.err.println("获取特定进程信息失败: " + e.getMessage());
        }
    }
}