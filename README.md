# Aircraft War (飞机大战)

这是一个基于 Java Swing 开发的经典“飞机大战”桌面游戏。玩家通过鼠标控制英雄机，击败不断来袭的敌机，获取道具增强火力，并挑战更高的分数。

## ✨ 功能特性

*   **多重难度**：提供简单、普通、困难三种模式，满足不同水平玩家的需求。
    *   **简单模式**：敌机数量固定，适合新手。
    *   **普通/困难模式**：随着分数增加，难度动态提升（敌机数量增加、射击频率加快）。
*   **丰富道具**：
    *   ❤️ **回血道具**：恢复英雄机生命值。
    *   💣 **炸弹道具**：清屏，消灭当前所有非 Boss 敌机和子弹。
    *   🔥 **火力道具**：升级子弹发射模式（直射 -> 散射 -> 环绕）。
*   **多样敌机**：包含普通敌机、精英敌机、Boss 敌机等多种类型。
*   **排行榜**：游戏结束后自动记录分数，支持按难度分别查看历史高分（数据持久化存储）。
*   **音效系统**：支持背景音乐及战斗音效的开关控制。

## 🛠 技术栈

*   **编程语言**: Java 25
*   **构建工具**: Maven
*   **GUI 框架**: Java Swing
*   **测试框架**: JUnit 5
*   **工具库**: Apache Commons Lang3

## 📐 设计模式

本项目在开发中广泛应用了多种设计模式，以提高代码的可维护性和扩展性：

*   **单例模式 (Singleton)**
    *   应用：[`edu.hitsz.aircraft.HeroAircraft`](src/main/java/edu/hitsz/aircraft/HeroAircraft.java)
    *   说明：确保游戏中只有一个英雄机实例。
*   **工厂模式 (Factory)**
    *   应用：[`edu.hitsz.factory`](src/main/java/edu/hitsz/factory) 包下的各类工厂（如 `EnemyFactory`, `PropFactory`）。
    *   说明：封装对象的创建逻辑，方便扩展新的敌机或道具类型。
*   **策略模式 (Strategy)**
    *   应用：[`edu.hitsz.strategy.shoot.ShootStrategy`](src/main/java/edu/hitsz/strategy/shoot/ShootStrategy.java)
    *   说明：定义了直射、散射、环绕等多种射击策略，使英雄机或敌机可以灵活切换攻击方式。
*   **模板方法模式 (Template Method)**
    *   应用：[`edu.hitsz.application.Game`](src/main/java/edu/hitsz/application/Game.java) 及其子类 (`EasyGame`, `NormalGame`, `HardGame`)。
    *   说明：在父类中定义游戏主循环的骨架，将难度相关的具体实现（如最大敌机数、产生周期）留给子类实现。
*   **数据访问对象模式 (DAO)**
    *   应用：[`edu.hitsz.scoreboard.ScoreRecordDao`](src/main/java/edu/hitsz/scoreboard/ScoreRecordDao.java)
    *   说明：将排行榜数据的持久化操作（读写文件）与业务逻辑分离。

## 🚀 快速开始

### 环境要求

*   **JDK**: 25 或更高版本
*   **Maven**: 3.8+

### 方式一：使用 IDE 运行 (推荐)

1.  使用 IntelliJ IDEA 或 VS Code 打开项目根目录。
2.  等待 Maven 依赖下载完成。
3.  找到主类 [`edu.hitsz.application.Main`](src/main/java/edu/hitsz/application/Main.java) 并运行。

### 方式二：命令行运行

在项目根目录下执行以下 Maven 命令：

```bash
mvn -q org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=edu.hitsz.application.Main
```

## 🎮 游戏操作

*   **移动**：移动鼠标控制英雄机位置。
*   **攻击**：英雄机自动发射子弹。
*   **道具获取**：击败 **精英敌机** 或 **Boss** 有概率掉落道具，移动触碰即可拾取。

## 📂 项目结构

```text
src/main/java/edu/hitsz/
├── aircraft/      # 飞行器类（英雄机、敌机）
├── application/   # 应用程序入口、游戏主循环、难度控制
├── basic/         # 基础抽象类
├── bullet/        # 子弹类
├── factory/       # 工厂类（创建敌机、子弹、道具）
├── layout/        # 界面布局（开始菜单、排行榜）
├── music/         # 音频控制
├── prop/          # 道具类
├── scoreboard/    # 排行榜数据处理
└── strategy/      # 策略类（射击策略）
```

## ❓ 常见问题

**Q: 启动时报错 "java: 错误: 不支持发行版本 25"**

**A: 请确保您的 JDK 版本至少为 25。如果使用 IDE，请检查项目结构设置中的 SDK 版本和语言级别。或者修改 pom.xml 中的 `maven.compiler.source` 和 `maven.compiler.target` 为您本地的 JDK 版本（如 17 或 21）。**

**Q: 图片或音效无法加载**

**A: 游戏资源加载依赖于工作目录。请确保运行时的“工作目录 (Working Directory)”设置为项目的根目录（即包含 pom.xml 的目录）。**