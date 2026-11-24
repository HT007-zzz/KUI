# KUANIAN Visual（客户端视觉模组）— Minecraft 1.20.1 Forge

纯客户端模组，只装在玩家自己的客户端，服务器不用装。

- 主界面：白蓝配色的解构风，大标题 KUANIAN，背景有缓慢漂移的色块、刻度线和虚线
- 主界面的「退出游戏」按钮文字改为：好梦
- 窗口标题改为：可你明知道我爱着这世界的一切

想改文字，只需要改 `src/main/java/com/kuanian/visual/KuanianVisual.java` 里的三个常量。

无前置模组，只需要 Forge 1.20.1（47.x）。

构建方法和之前的模组一样：把整个项目上传到 GitHub 仓库，Actions 会自动构建，
到运行记录底部的 Artifacts 下载 `kuanianvisual-jar`，解压得到 jar。
