package com.demo.processmanager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令执行工具类
 * 封装系统命令的执行和结果解析
 */
public class CommandExecutor {

    /**
     * 执行系统命令并返回结果
     * @param command 要执行的命令
     * @return 命令执行结果的每一行
     * @throws IOException 如果命令执行失败
     */
    public static List<String> executeCommand(String command) throws IOException {
        List<String> output = new ArrayList<>();
        
        Process process = null;
        BufferedReader reader = null;
        
        try {
            // 执行命令
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                processBuilder.command("sh", "-c", command);
            }
            
            process = processBuilder.start();
            
            // 读取命令输出
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    output.add(line);
                }
            }
            
            // 等待命令执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("命令执行失败，退出码: " + exitCode);
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("命令执行被中断", e);
        } finally {
            // 清理资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 忽略关闭异常
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        
        return output;
    }

    /**
     * 执行系统命令并返回单行结果
     * @param command 要执行的命令
     * @return 命令执行结果的第一行
     * @throws IOException 如果命令执行失败
     */
    public static String executeCommandSingleLine(String command) throws IOException {
        List<String> output = executeCommand(command);
        return output.isEmpty() ? "" : output.get(0);
    }

    /**
     * 获取当前操作系统类型
     * @return 操作系统类型（"windows", "mac", "linux"）
     */
    public static String getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("mac")) {
            return "mac";
        } else {
            return "linux";
        }
    }
}