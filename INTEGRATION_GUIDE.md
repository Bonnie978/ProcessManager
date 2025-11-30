# ProcessManager 集成指南

本文档详细说明如何在其他Java项目中引入和使用ProcessManager JAR包。

## 方法一：手动添加JAR包到Classpath

### 1. 复制JAR文件
将 `process-manager.jar` 文件复制到您的项目目录中（例如 `lib/` 文件夹）。

### 2. 编译时包含JAR包
```bash
# 使用javac编译
javac -cp ".:process-manager.jar" YourApplication.java

# 或者指定lib目录
javac -cp ".:lib/process-manager.jar" YourApplication.java
```

### 3. 运行时包含JAR包
```bash
# 运行程序
java -cp ".:process-manager.jar" YourApplication

# 或者
java -cp ".:lib/process-manager.jar" YourApplication
```

## 方法二：使用Maven项目集成

### 1. 安装到本地Maven仓库
```bash
mvn install:install-file \
    -Dfile=process-manager.jar \
    -DgroupId=com.demo \
    -DartifactId=process-manager \
    -Dversion=1.0.0 \
    -Dpackaging=jar
```

### 2. 在pom.xml中添加依赖
```xml
<dependencies>
    <dependency>
        <groupId>com.demo</groupId>
        <artifactId>process-manager</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## 方法三：使用Gradle项目集成

### 1. 安装到本地Gradle缓存
```bash
# 将JAR包复制到libs目录
cp process-manager.jar libs/
```

### 2. 在build.gradle中添加依赖
```gradle
dependencies {
    implementation files('libs/process-manager.jar')
    // 或者使用文件路径
    implementation fileTree(dir: 'libs', include: ['*.jar'])
}
```

## 使用示例

### 基本使用
```java
import com.demo.processmanager.ProcessManager;
import com.demo.processmanager.ProcessInfo;
import com.demo.processmanager.SystemStats;
import java.util.List;

public class MyApplication {
    public static void main(String[] args) {
        // 创建进程管理器实例
        ProcessManager processManager = new ProcessManager();
        
        // 获取系统信息
        System.out.println("操作系统: " + processManager.getOsType());
        
        // 获取系统状态
        SystemStats stats = processManager.getSystemStats();
        System.out.println("系统状态: " + stats);
        
        // 获取进程列表
        try {
            List<ProcessInfo> processes = processManager.listProcesses();
            System.out.println("总进程数: " + processes.size());
            
            // 显示前10个进程
            for (int i = 0; i < Math.min(processes.size(), 10); i++) {
                System.out.println((i+1) + ". " + processes.get(i));
            }
        } catch (Exception e) {
            System.err.println("获取进程列表失败: " + e.getMessage());
        }
    }
}
```

### 高级使用示例
```java
import com.demo.processmanager.ProcessManager;
import com.demo.processmanager.ProcessInfo;

public class ProcessMonitor {
    private ProcessManager processManager;
    
    public ProcessMonitor() {
        this.processManager = new ProcessManager();
    }
    
    /**
     * 监控特定进程的CPU使用率
     */
    public void monitorProcess(long pid) {
        try {
            ProcessInfo process = processManager.getProcessInfo(pid);
            if (process != null) {
                System.out.printf("进程 %d (%s) CPU使用率: %.2f%%\n", 
                    process.getPid(), process.getName(), process.getCpu());
            } else {
                System.out.println("进程不存在: " + pid);
            }
        } catch (Exception e) {
            System.err.println("监控进程失败: " + e.getMessage());
        }
    }
    
    /**
     * 查找包含关键字的进程
     */
    public void findProcesses(String keyword) {
        try {
            List<ProcessInfo> processes = processManager.listProcesses();
            System.out.println("包含 \"" + keyword + "\" 的进程:");
            
            for (ProcessInfo process : processes) {
                if (process.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.printf("  PID: %d, 名称: %s, CPU: %.2f%%, 内存: %d MB\n",
                        process.getPid(), process.getName(), process.getCpu(), process.getMemory());
                }
            }
        } catch (Exception e) {
            System.err.println("查找进程失败: " + e.getMessage());
        }
    }
}
```

## 集成注意事项

### 1. 系统权限
- 在某些操作系统上，可能需要管理员权限才能获取完整的进程信息
- 如果遇到权限问题，请以管理员身份运行程序

### 2. 跨平台兼容性
- ProcessManager自动检测操作系统类型
- 无需手动配置平台相关的命令

### 3. 错误处理
- 建议使用try-catch块处理可能出现的异常
- 进程信息获取可能因系统权限或进程状态而失败

### 4. 性能考虑
- 获取完整进程列表可能消耗较多系统资源
- 对于频繁监控，建议使用缓存或定时获取

## 常见问题

### Q: 运行时出现ClassNotFoundException
A: 确保JAR包已正确添加到classpath中

### Q: 无法获取进程信息
A: 检查系统权限，可能需要以管理员身份运行

### Q: 在某些Linux发行版上无法正常工作
A: 确保系统已安装ps、top等基本命令工具

## 技术支持

如果在集成过程中遇到问题，请参考：
- README.md - 基本使用说明
- 演示程序 - process-manager-executable.jar
- 源代码 - 查看具体实现逻辑