# Cobblestone API
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)  
**为《Minecraft: Java Edition》模组开发提供的基础工具库**   
[English](README.md) | 简体中文
## 概述
Cobblestone API 是专为 Minecraft 模组开发者设计的核心工具库，提供高效的数据持久化与事件管理解决方案，显著简化模组开发流程。
## 核心模块
### 核心模块
核心模块支持你的模组自动加载一些类，使你不需要手动加载它们。
### 数据模块
提供先进的数据持久化支持：
- **实体数据扩展**：通过注入 `LivingEntity` 类实现自定义数据绑定
- **智能序列化**：自动化 Java 对象与NBT 数据之间的转换
### 事件模块
扩展事件系统：
- **增强型事件钩子**：覆盖更多游戏关键节点
## 构建指南
使用Gradle构建工具编译项目。
## 协议
[查看NOTICE文件](NOTICE)
### 源代码许可
**本项目采用 LGPL-3.0 许可协议**  
[查看完整协议文本](LICENSE)

### 第三方组件
| 组件       | 许可协议           |
|----------|----------------|
| 所有运行时依赖项 | Apache-2.0/MIT |
| 构建工具链    | MIT            |

[查看DEPENDENCIES文件](DEPENDENCIES)

### 合规说明
1. 分发二进制文件时**必须包含** [NOTICE](NOTICE) 文件
2. 修改库代码后**需公开修改后的源代码**
3. 使用本库开发的模组**无需开放自身源代码**
