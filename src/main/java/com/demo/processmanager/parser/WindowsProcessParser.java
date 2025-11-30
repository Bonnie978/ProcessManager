package com.demo.processmanager.parser;

import com.demo.processmanager.ProcessInfo;
import com.demo.processmanager.utils.CommandExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Windows平台进程解析器
 * 使用tasklist和wmic命令获取进程信息
 */
public class WindowsProcessParser {
    
    /**
     * 获取所有进程列表
     * @return 进程信息列表
     * @throws IOException 如果命令执行失败
     */
    public List<ProcessInfo> listProcesses() throws IOException {
        List<ProcessInfo> processes = new ArrayList<>();
        
        // 使用tasklist获取基本进程信息
        String command = "tasklist /FO CSV /NH";
        List<String> output = CommandExecutor.executeCommand(command);
        
        for (String line : output) {
            ProcessInfo process = parseTasklistLine(line);
            if (process != null) {
                // 使用wmic获取更详细的CPU和内存信息
                enrichProcessInfo(process);
                processes.add(process);
            }
        }
        
        return processes;
    }
    
    /**
     * 解析tasklist命令输出的一行
     * @param line tasklist CSV格式输出
     * @return 解析后的ProcessInfo对象，解析失败返回null
     */
    private ProcessInfo parseTasklistLine(String line) {
        try {
            // 解析CSV格式："映像名称","PID","会话名","会话#","内存使用"
            String[] parts = line.split(",");
            if (parts.length < 5) {
                return null;
            }
            
            // 去除引号
            String name = parts[0].replaceAll("^\"|\"$", "");
            long pid = Long.parseLong(parts[1].replaceAll("^\"|\"$", ""));
            String memoryStr = parts[4].replaceAll("^\"|\"$", "").replace(" K", "");
            
            // 转换内存单位为MB（Windows返回的是KB）
            long memoryKB = Long.parseLong(memoryStr.replace(",", ""));
            long memoryMB = memoryKB / 1024;
            
            // 创建基础进程信息（CPU和启动时间稍后补充）
            return new ProcessInfo(pid, name, 0.0, memoryMB, "");
            
        } catch (NumberFormatException e) {
            // 忽略解析错误
            return null;
        }
    }
    
    /**
     * 使用wmic命令丰富进程信息（CPU使用率和启动时间）
     * @param process 需要补充信息的进程对象
     */
    private void enrichProcessInfo(ProcessInfo process) {
        try {
            // 获取CPU使用率
            String cpuCommand = String.format("wmic path Win32_PerfFormattedData_PerfProc_Process where \"IDProcess=%d\" get PercentProcessorTime /value", 
                                             process.getPid());
            String cpuOutput = CommandExecutor.executeCommandSingleLine(cpuCommand);
            if (cpuOutput.contains("PercentProcessorTime")) {
                String cpuStr = cpuOutput.split("=")[1].trim();
                double cpu = Double.parseDouble(cpuStr);
                process.setCpu(cpu);
            }
            
            // 获取启动时间
            String timeCommand = String.format("wmic process where ProcessId=%d get CreationDate /value", 
                                              process.getPid());
            String timeOutput = CommandExecutor.executeCommandSingleLine(timeCommand);
            if (timeOutput.contains("CreationDate")) {
                String timeStr = timeOutput.split("=")[1].trim();
                process.setStartTime(formatWindowsTime(timeStr));
            }
            
        } catch (Exception e) {
            // 忽略wmic命令执行失败
        }
    }
    
    /**
     * 格式化Windows时间戳为可读格式
     * @param windowsTime Windows时间格式：yyyyMMddHHmmss.ffffff+zzz
     * @return 格式化后的时间字符串
     */
    private String formatWindowsTime(String windowsTime) {
        try {
            // 简单格式化：提取年月日时分秒
            if (windowsTime.length() >= 14) {
                String datePart = windowsTime.substring(0, 14);
                return String.format("%s-%s-%s %s:%s:%s", 
                    datePart.substring(0, 4), // 年
                    datePart.substring(4, 6), // 月
                    datePart.substring(6, 8), // 日
                    datePart.substring(8, 10), // 时
                    datePart.substring(10, 12), // 分
                    datePart.substring(12, 14) // 秒
                );
            }
        } catch (Exception e) {
            // 格式化失败
        }
        return windowsTime; // 返回原始格式
    }
    
    /**
     * 根据PID获取特定进程信息
     * @param pid 进程ID
     * @return 进程信息，如果进程不存在返回null
     * @throws IOException 如果命令执行失败
     */
    public ProcessInfo getProcessInfo(long pid) throws IOException {
        String command = String.format("tasklist /FI \"PID eq %d\" /FO CSV /NH", pid);
        List<String> output = CommandExecutor.executeCommand(command);
        
        if (output.isEmpty() || output.get(0).contains("没有运行") || output.get(0).contains("No tasks")) {
            return null; // 进程不存在
        }
        
        ProcessInfo process = parseTasklistLine(output.get(0));
        if (process != null) {
            enrichProcessInfo(process);
        }
        
        return process;
    }
}