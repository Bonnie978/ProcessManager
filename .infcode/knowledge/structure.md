# ProcessManager - 项目结构文档

## 项目根目录结构

```
ProcessManager/
├── pom.xml                    # Maven项目配置文件
├── README.md                  # 项目说明文档
├── CHANGELOG.md               # 版本变更记录
├── INTEGRATION_GUIDE.md       # 集成指南文档
├── LICENSE                    # 开源许可证文件
└── src/                       # 源代码目录
    └── main/
        └── java/
            └── com/
                └── demo/
                    └── processmanager/
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

## 核心目录说明

### 根目录文件
- **pom.xml**：Maven项目配置文件，定义了项目依赖、构建配置和插件
- **README.md**：详细的项目说明文档，包含功能特性、使用示例和API文档
- **CHANGELOG.md**：版本历史记录，跟踪项目功能演进
- **INTEGRATION_GUIDE.md**：集成指南，帮助开发者快速集成到项目中

### 源代码目录结构

#### 主包结构 (com.demo.processmanager)
- **ProcessManager.java**：核心入口类，提供统一的API接口
- **ProcessInfo.java**：进程信息实体类，封装进程基本属性
- **SystemStats.java**：系统状态实体类，封装系统资源监控信息
- **ProcessManagerDemo.java**：演示程序，展示库的基本使用方式

#### 平台适配层 (parser目录)
- **MacProcessParser.java**：macOS平台进程信息解析器
- **LinuxProcessParser.java**：Linux平台进程信息解析器  
- **WindowsProcessParser.java**：Windows平台进程信息解析器

#### 工具类 (utils目录)
- **CommandExecutor.java**：系统命令执行工具类，封装跨平台命令执行逻辑

## 架构设计特点

### 分层架构
项目采用清晰的分层架构设计：
1. **API层**：ProcessManager类提供统一的外部接口
2. **业务逻辑层**：平台解析器实现具体的进程管理逻辑
3. **工具层**：CommandExecutor提供通用的命令执行能力

### 平台适配模式
通过策略模式实现跨平台支持，每个操作系统都有对应的解析器实现，运行时根据当前系统自动选择适当的解析器。

### 模块化设计
- 实体类与业务逻辑分离
- 平台相关代码独立封装
- 工具类提供通用功能支持