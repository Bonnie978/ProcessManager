# ProcessManager - 跨平台进程管理Java库

ProcessManager是一个独立的Java工具库，用于封装系统层级的进程管理与系统状态监控能力。该库通过统一的Java API提供跨平台的进程查询、CPU/内存使用率获取等能力。

## 功能特性

- ✅ **跨平台支持**：支持macOS、Linux和Windows三大操作系统
- ✅ **进程管理**：获取系统进程列表、查询特定进程信息
- ✅ **系统监控**：监控CPU使用率、内存使用率、JVM堆信息
- ✅ **统一API**：简单的Java接口，无需关心底层系统命令
- ✅ **轻量级**：无外部依赖，可直接集成到任何Java项目中

## 快速开始

### 1. 引入JAR包

将`process-manager.jar`添加到你的项目classpath中。

### 2. 基本使用

```java
import com.demo.processmanager.ProcessManager;
import com.demo.processmanager.ProcessInfo;
import com.demo.processmanager.SystemStats;

public class Example {
    public static void main(String[] args) {
        // 创建进程管理器实例
        ProcessManager processManager = new ProcessManager();
        
        // 获取系统状态
        SystemStats stats = processManager.getSystemStats();
        System.out.println("系统状态: " + stats);
        
        // 获取进程列表
        try {
            List<ProcessInfo> processes = processManager.listProcesses();
            System.out.println("总进程数: " + processes.size());
            
            // 显示前5个进程
            for (int i = 0; i < Math.min(processes.size(), 5); i++) {
                System.out.println(processes.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 3. 运行演示程序

项目包含一个演示程序，可以直接运行：

```bash
java -jar process-manager-executable.jar
```

## API文档

### ProcessManager类

#### 构造函数
- `ProcessManager()` - 创建进程管理器实例

#### 主要方法
- `List<ProcessInfo> listProcesses()` - 获取系统进程列表
- `ProcessInfo getProcessInfo(long pid)` - 根据PID获取进程信息
- `SystemStats getSystemStats()` - 获取系统状态统计信息
- `String getOsType()` - 获取当前操作系统类型

### ProcessInfo类

进程信息实体类，包含以下字段：
- `long pid` - 进程标识符
- `String name` - 进程名称
- `double cpu` - CPU使用率（%）
- `long memory` - 内存占用（MB）
- `String startTime` - 启动时间文本

### SystemStats类

系统状态实体类，包含以下字段：
- `double cpuUsage` - CPU使用率
- `double memUsage` - 系统内存占用比例
- `long jvmHeap` - JVM当前堆使用量
- `long jvmMaxHeap` - JVM最大堆大小

## 平台支持

| 操作系统 | 进程查询命令 | 系统监控方式 |
|---------|-------------|-------------|
| macOS | `ps`命令 | ManagementFactory + 系统命令 |
| Linux | `ps`命令 | ManagementFactory + 系统命令 |
| Windows | `tasklist` + `wmic` | ManagementFactory + WMI命令 |

## 项目结构

```
src/main/java/com/demo/processmanager/
├── ProcessManager.java      # 主入口类
├── ProcessInfo.java         # 进程信息实体类
├── SystemStats.java         # 系统状态实体类
├── ProcessManagerDemo.java  # 演示类
├── parser/                  # 平台适配层
│   ├── MacProcessParser.java
│   ├── LinuxProcessParser.java
│   └── WindowsProcessParser.java
└── utils/
    └── CommandExecutor.java # 命令执行工具类
```

## 构建说明

### 使用Maven构建
```bash
mvn clean package
```

### 手动编译
```bash
# 编译Java文件
javac -d target/classes src/main/java/com/demo/processmanager/*.java \
    src/main/java/com/demo/processmanager/parser/*.java \
    src/main/java/com/demo/processmanager/utils/*.java

# 打包为JAR
jar -cvf process-manager.jar -C target/classes .
```

## 许可证

本项目采用MIT许可证。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 版本历史

- v1.0.0 - 初始版本，实现基本进程管理和系统监控功能