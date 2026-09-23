# TaskFlow

一个用于学习 Java 后端基础流程的任务管理 REST API 项目。

项目从内存版任务 CRUD 开始，逐步完成 MySQL 持久化、MyBatis、参数校验、统一异常处理、日志、单元测试、OpenAPI 文档，以及 Docker Compose 一键启动。

## 技术栈

- Java 17
- Spring Boot 4.1.1
- Maven
- MySQL 8
- MyBatis
- JUnit 5
- Mockito
- SpringDoc OpenAPI
- Docker
- Docker Compose
- Git 和 GitHub

## 已实现功能

- 任务新增、查询、按 ID 查询、修改、删除
- 修改任务状态
- 任务优先级、描述、截止日期
- 按状态筛选任务
- 分页查询
- 请求参数校验
- 统一处理 400、404、500 错误
- 应用日志
- TaskService 单元测试
- Swagger UI 接口文档
- Docker 镜像构建
- Docker Compose 启动应用与 MySQL

## 项目结构

```text
taskflow
├─ src
│  ├─ main
│  │  ├─ java/com/leo/taskflow
│  │  │  ├─ controller       # 接收 HTTP 请求
│  │  │  ├─ service          # 业务逻辑
│  │  │  ├─ mapper           # MyBatis 数据库访问
│  │  │  ├─ dto              # 请求和响应对象
│  │  │  ├─ entity           # Task、Priority、TaskStatus
│  │  │  ├─ exception        # 全局异常处理
│  │  │  └─ config           # OpenAPI 配置
│  │  └─ resources
│  │     ├─ application.properties
│  │     ├─ application-dev.properties
│  │     └─ application-prod.properties
│  └─ test                   # JUnit 和 Mockito 测试
├─ db/init/01-schema.sql     # Compose MySQL 初始化建表脚本
├─ Dockerfile                # TaskFlow 镜像构建说明
├─ compose.yaml              # 应用和 MySQL 容器编排
├─ .env.example              # Compose 环境变量模板
└─ requests.http             # HTTP 接口测试集
```

## 推荐启动方式：Docker Compose

### 1. 前置条件

- 已安装并启动 Docker Desktop
- Docker Desktop 使用 Linux containers 模式
- 在项目根目录执行命令

### 2. 创建本地环境变量文件

PowerShell 执行：

```powershell
Copy-Item .env.example .env
```

打开 `.env`，替换其中的示例密码：

```properties
MYSQL_DATABASE=taskflow
MYSQL_USER=taskflow
MYSQL_PASSWORD=你的TaskFlow数据库密码
MYSQL_ROOT_PASSWORD=你的MySQL root密码
```

不要提交 `.env`，它已被 `.gitignore` 忽略。

### 3. 启动项目

```powershell
docker compose up --build -d
```

查看容器状态：

```powershell
docker compose ps
```

查看应用日志：

```powershell
docker compose logs -f app
```

### 4. 停止项目

```powershell
docker compose down
```

该命令会删除容器和网络，但保留 MySQL 数据卷。

> 注意：`docker compose down -v` 会同时删除 MySQL 数据卷，并清空 Compose 数据库中的全部任务数据。

## 本机开发方式

本机开发使用 Windows 的 `MySQL80` 服务。

### 1. 准备数据库

创建 `taskflow` 数据库，并执行 `db/init/01-schema.sql` 创建 `task` 表。

### 2. 配置 IDEA 环境变量

在 IDEA Run Configuration 中配置：

```text
SPRING_PROFILES_ACTIVE=dev
DB_PASSWORD=你的MySQL密码
```

### 3. 启动应用

运行 `TaskflowApplication`，或在 PowerShell 执行：

```powershell
$env:SPRING_PROFILES_ACTIVE = "dev"
$env:DB_PASSWORD = "你的MySQL密码"
mvn spring-boot:run
```

应用默认运行在：

```text
http://localhost:8080
```

## 接口文档与测试

Swagger UI：

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON：

```text
http://localhost:8080/v3/api-docs
```

HTTP 测试文件：

```text
requests.http
```

在 IntelliJ IDEA 中打开 `requests.http`，点击请求上方的运行按钮即可测试接口。

## 主要接口

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/hello` | 测试接口 |
| POST | `/tasks` | 创建任务 |
| GET | `/tasks` | 分页查询任务，可按状态筛选 |
| GET | `/tasks/{id}` | 按 ID 查询任务 |
| PUT | `/tasks/{id}` | 完整更新任务 |
| PATCH | `/tasks/{id}/status` | 更新任务状态 |
| DELETE | `/tasks/{id}` | 删除任务 |

任务状态：

```text
TODO
IN_PROGRESS
DONE
```

任务优先级：

```text
LOW
MEDIUM
HIGH
```

查询任务示例：

```text
GET /tasks?status=TODO&page=0&size=10
```

## Docker Compose 说明

Compose 会同时启动两个容器：

```text
app 容器：运行 Spring Boot TaskFlow
db 容器：运行 MySQL 8
```

应用通过 Compose 内部服务名连接数据库：

```text
app → db:3306
```

MySQL 数据保存在 Docker 命名数据卷 `mysql-data` 中，因此普通停止或删除容器后数据仍可保留。

## 安全说明

- 不要提交 `.env`。
- 不要在 GitHub 提交 MySQL 密码。
- 本机开发密码通过 IDEA 环境变量 `DB_PASSWORD` 传入。
- Compose 密码通过本机 `.env` 文件传入。