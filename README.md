# AircraftWar-base（飞机大战）

基于 Java Swing 的简易“飞机大战”项目（Maven 工程）。启动后进入开始菜单，可选择难度与音效开关；游戏结束后自动跳转到排行榜界面，并将成绩持久化到文本文件。

## 运行环境

- JDK：项目 `pom.xml` 目前设置为 **Java 25**（`maven.compiler.source/target=25`）
- Maven：建议 3.8+（或使用 IDE 自带 Maven）
- OS：Windows/macOS/Linux 均可（本仓库资源路径为相对路径，见“常见问题”）

## 快速开始

### 方式一：IDE 直接运行（推荐）

1. 使用 IntelliJ IDEA / VS Code（Java 扩展）打开工程根目录。
2. 等待 Maven 依赖下载完成。
3. 运行主类：`edu.hitsz.application.Main`

### 方式二：命令行运行（Maven Exec）

在工程根目录执行：

```bash
mvn -q org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=edu.hitsz.application.Main
```

> 说明：当前 `pom.xml` 未内置可执行 jar 打包配置，使用 `exec-maven-plugin` 能自动拼好运行时 classpath（包含依赖）。

## 玩法与界面说明

- 开始菜单：
	- 难度：简单 / 普通 / 困难
	- 音效：开 / 关（背景音乐、Boss 音乐、拾取/命中/爆炸等）
- 操作方式：鼠标控制英雄机移动（英雄机自动射击）。
- 结算与排行榜：英雄机生命值归零后，自动跳转排行榜界面；排行榜按模式分别记录到文本文件。

### 难度差异（代码实现）

- 简单：敌机最大数量固定为 5。
- 普通/困难：会随分数动态提升难度（敌机最大数量增加、敌机/英雄射击周期缩短等）。

## 项目结构

核心目录（`src/main/java/edu/hitsz`）：

- `application/`：程序入口与游戏主循环
	- `Main`：创建窗口与 CardLayout，负责开始菜单/游戏/排行榜页面切换
	- `Game`：游戏主面板与主循环（对象生成、移动、碰撞、结算、重绘）
	- `EasyGame`/`NormalGame`/`HardGame`：不同难度配置（模板方法/策略式参数覆写）
- `aircraft/`：飞机相关（英雄机、敌机）
- `bullet/`：子弹对象
- `prop/`：道具对象（回血、炸弹、火力等）
- `factory/`：工厂体系（创建敌机/子弹/道具）
- `strategy/shoot/`：射击策略（不同散射/环绕等发射模式）
- `music/`：音效播放控制
- `layout/`：Swing 界面布局（开始菜单、排行榜布局）
- `scoreboard/`：排行榜数据结构与持久化（读取/写入日志文件）

资源目录：

- `src/main/resources/images/`：图片资源
- `src/main/resources/videos/`：音效资源（wav）
- `src/main/resources/logs/`：排行榜数据文件（不同模式分文件存储）

## 设计要点（与代码对应）

- 单例：`HeroAircraft.getHeroAircraft()` 保证英雄机唯一实例。
- 工厂：`factory/*` 负责按规则生成敌机/道具/子弹。
- 策略：`strategy/shoot/*` 抽象不同射击模式，降低耦合。
- 模板方法：`Game` 统一主循环流程，子类通过覆写参数方法改变难度。

## 测试

测试目录：`src/test/java/edu/hitsz/aircraft/HeroAircraftTest.java`

## 常见问题

1. **资源找不到 / 图片加载失败 / 音效找不到**
	 - 该项目通过类似 `src/main/resources/...` 的相对路径读取资源（见 `ImageManager`、`MusicController`）。
	 - 请确保“工作目录（Working Directory）”为工程根目录运行。

2. **编译提示不支持的 release 版本（Java 25）**
	 - 说明当前 JDK 版本低于 25；可切换到 JDK 25，或将 `pom.xml` 的 `maven.compiler.source/target` 改为你本地 JDK 版本。

