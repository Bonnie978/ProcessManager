# 更新日志

ProcessManager项目的所有显著更改都将记录在此文件中。

## [1.0.0] - 2025-11-30

### 新增
- 初始版本发布
- 实现跨平台进程管理功能
- 支持macOS、Linux、Windows三大操作系统
- 提供统一的Java API接口
- 包含完整的演示程序

### 功能特性
- 获取系统进程列表
- 根据PID查询特定进程信息
- 监控系统CPU使用率
- 监控系统内存使用率
- 获取JVM堆使用情况
- 跨平台适配（macOS使用ps，Linux使用ps，Windows使用tasklist+wmic）

### 技术特性
- 基于Java 11开发
- 无外部依赖
- 完整的错误处理机制
- 统一的API设计

### 文件结构
```
process-manager/
├── src/main/java/com/demo/processmanager/
│   ├── ProcessManager.java      # 主入口类
│   ├── ProcessInfo.java         # 进程信息实体类
│   ├── SystemStats.java         # 系统状态实体类
│   ├── ProcessManagerDemo.java  # 演示类
│   ├── parser/                  # 平台适配层
│   └── utils/                   # 工具类
├── pom.xml                      # Maven配置文件
├── README.md                    # 项目说明文档
├── LICENSE                      # 许可证文件
└── .gitignore                   # Git忽略文件
```

### 发布文件
- `process-manager.jar` - 库文件（不含主类信息）
- `process-manager-executable.jar` - 可执行JAR包（包含演示程序）