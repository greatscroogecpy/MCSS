# Daisyworld Java 实现

## 简介
本项目用 Java 实现了 NetLogo 经典生态模型 Daisyworld，包括世界网格、雏菊生长与温度反馈等核心机制。

## 目录结构
- `src/World.java`：世界主类
- `src/Cell.java`：网格格子类
- `src/Daisy.java`：雏菊类
- `src/Simulation.java`：模拟主入口

## 编译与运行

```bash
javac -d out src/*.java
java -cp out Simulation
```