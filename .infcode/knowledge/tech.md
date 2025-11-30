# ProcessManager - 技术栈文档

## 核心技术

### 编程语言
- **Java 11**：项目采用Java 11作为主要开发语言，利用其现代特性和跨平台能力
- **Java Management Extensions (JMX)**：用于获取JVM运行时信息和系统监控数据

### 构建工具
- **Maven 3.8+**：标准的Java项目构建工具，管理依赖和构建生命周期
- **Maven Compiler Plugin**：配置Java 11编译目标
- **Maven Jar Plugin**：生成可执行JAR包
- **Maven Source Plugin**：打包源代码

### 系统集成技术
- **跨平台系统命令**：通过执行系统命令获取进程和系统信息
- **进程管理命令**：
  - macOS/Linux：`ps`、`top`、`vm_stat`、`sysctl`
  - Windows：`tasklist`、`wmic`
- **Java运行时管理**：`ManagementFactory`获取JVM统计信息

## 项目使用的工具

### 开发工具
- **Maven**：依赖管理和项目构建
- **Java 11 SDK**：开发环境要求
- **Git**：版本控制

### 测试框架
- **JUnit 4.13.2**：单元测试框架
- **测试范围**：仅用于测试阶段，不包含在生产依赖中

### 日志框架
- **SLF4J API 1.7.36**：日志门面接口
- **SLF4J Simple 1.7.36**：简单的日志实现（运行时依赖）

## 关键依赖

### 生产依赖
```xml
<!-- 日志依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.36</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.36</version>
    <scope>runtime</scope>
</dependency>
```

### 测试依赖
```xml
<!-- 测试依赖 -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

## 构建、开发和执行命令

### 构建命令
```bash
# 清理并打包项目
mvn clean package

# 跳过测试构建
mvn clean package -DskipTests

# 安装到本地仓库
mvn clean install
```

### 开发命令
```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 生成项目文档
mvn javadoc:javadoc
```

### 执行命令
```bash
# 运行演示程序
java -jar target/process-manager-1.0.0.jar

# 指定主类运行
java -cp target/process-manager-1.0.0.jar com.demo.processmanager.ProcessManagerDemo
```

## 项目配置特点

### Maven配置
- **Java版本**：11（source和target）
- **编码**：UTF-8
- **打包方式**：JAR
- **主类**：com.demo.processmanager.ProcessManagerDemo

### 构建配置
- **编译器插件**：配置Java 11兼容性
- **JAR插件**：生成包含清单的可执行JAR
- **源码插件**：提供源代码打包功能

### 依赖管理策略
- **最小化依赖**：仅包含必要的日志框架
- **测试分离**：测试依赖与生产依赖严格分离
- **无外部进程管理库**：完全基于Java标准库和系统命令实现