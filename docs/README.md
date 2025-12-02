# ProcessManager 文档

## 项目概述

ProcessManager 是一个跨平台的 Java 进程管理工具库，专门设计用于封装系统层级的进程管理与系统状态监控能力。

## 功能特性

### 进程管理
- 获取系统中所有运行进程的详细信息列表
- 根据进程ID(PID)查询特定进程的详细信息
- 跨平台支持：macOS、Linux、Windows

### 系统监控
- CPU使用率监控
- 内存使用监控
- JVM堆内存监控
- 统一的状态统计接口

## 快速开始

### 依赖配置

在您的 `pom.xml` 中添加依赖：

```xml
<dependency>
    <groupId>com.demo</groupId>
    <artifactId>process-manager</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 基本使用

```java
import com.demo.processmanager.ProcessManager;
import com.demo.processmanager.ProcessInfo;
import com.demo.processmanager.SystemStats;

// 获取所有进程列表
List<ProcessInfo> processes = ProcessManager.getAllProcesses();

// 获取系统状态
SystemStats stats = ProcessManager.getSystemStats();

// 根据PID获取进程信息
ProcessInfo process = ProcessManager.getProcessInfo(1234);
```

## API 文档

### ProcessManager 类

主要静态方法：
- `getAllProcesses()` - 获取所有进程列表
- `getProcessInfo(int pid)` - 根据PID获取进程信息
- `getSystemStats()` - 获取系统状态统计

### ProcessInfo 类

进程信息实体，包含：
- `pid` - 进程ID
- `name` - 进程名称
- `cpuUsage` - CPU使用率
- `memoryUsage` - 内存使用量
- `startTime` - 启动时间

### SystemStats 类

系统状态统计，包含：
- `cpuUsage` - 系统CPU使用率
- `memoryUsage` - 系统内存使用率
- `jvmHeapUsage` - JVM堆内存使用率
- `totalProcesses` - 总进程数

## 平台支持

- **macOS**: 使用 `ps` 和 `top` 命令
- **Linux**: 使用 `ps` 和 `/proc` 文件系统
- **Windows**: 使用 `tasklist` 和 `wmic` 命令

## 构建说明

```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 打包
mvn package

# 运行演示程序
java -jar target/process-manager-1.0.0.jar
```

## 许可证

本项目采用 MIT 许可证，详见 [LICENSE](../LICENSE) 文件。

## 贡献指南

欢迎提交 Issue 和 Pull Request 来改进这个项目。