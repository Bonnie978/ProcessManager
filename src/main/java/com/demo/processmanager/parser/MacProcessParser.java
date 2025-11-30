package com.demo.processmanager.parser;

import com.demo.processmanager.ProcessInfo;
import com.demo.processmanager.utils.CommandExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * macOS平台进程解析器
 * 使用ps命令获取进程信息
 */
public class MacProcessParser {
    
    /**
     * 获取所有进程列表
     * @return 进程信息列表
     * @throws IOException 如果命令执行失败
     */
    public List<ProcessInfo> listProcesses() throws IOException {
        List<ProcessInfo> processes = new ArrayList<>();
        
        // 执行ps命令获取进程信息
        String command = "ps -eo pid,pcpu,pmem,etime,comm | tail -n +2";
        List<String> output = CommandExecutor.executeCommand(command);
        
        for (String line : output) {
            ProcessInfo process = parseProcessLine(line);
            if (process != null) {
                processes.add(process);
            }
        }
        
        return processes;
    }
    
    /**
     * 解析单行进程信息
     * @param line ps命令输出的一行
     * @return 解析后的ProcessInfo对象，解析失败返回null
     */
    private ProcessInfo parseProcessLine(String line) {
        try {
            // 去除多余空格并分割字段
            String[] parts = line.trim().split("\\s+");
            if (parts.length < 5) {
                return null;
            }
            
            long pid = Long.parseLong(parts[0]);
            double cpu = Double.parseDouble(parts[1]);
            double memoryPercent = Double.parseDouble(parts[2]);
            String startTime = parts[3];
            String name = parts[4];
            
            // 将内存百分比转换为MB（估算值）
            long memoryMB = estimateMemoryMB(memoryPercent);
            
            return new ProcessInfo(pid, name, cpu, memoryMB, startTime);
            
        } catch (NumberFormatException e) {
            // 忽略解析错误
            return null;
        }
    }
    
    /**
     * 根据内存百分比估算内存使用量（MB）
     * @param memoryPercent 内存使用百分比
     * @return 估算的内存使用量（MB）
     */
    private long estimateMemoryMB(double memoryPercent) {
        try {
            // 获取系统总内存
            String memoryCommand = "sysctl -n hw.memsize";
            String totalMemoryStr = CommandExecutor.executeCommandSingleLine(memoryCommand);
            long totalMemoryBytes = Long.parseLong(totalMemoryStr);
            long totalMemoryMB = totalMemoryBytes / (1024 * 1024);
            
            // 计算实际内存使用量
            return (long) (totalMemoryMB * memoryPercent / 100);
            
        } catch (Exception e) {
            // 如果获取总内存失败，返回估算值
            return (long) (memoryPercent * 100); // 简单估算
        }
    }
    
    /**
     * 根据PID获取特定进程信息
     * @param pid 进程ID
     * @return 进程信息，如果进程不存在返回null
     * @throws IOException 如果命令执行失败
     */
    public ProcessInfo getProcessInfo(long pid) throws IOException {
        String command = String.format("ps -p %d -o pid,pcpu,pmem,etime,comm", pid);
        List<String> output = CommandExecutor.executeCommand(command);
        
        if (output.size() < 2) {
            return null; // 进程不存在
        }
        
        // 跳过标题行，解析数据行
        return parseProcessLine(output.get(1));
    }
}