# FishingQuestionGame

一个基于CustomFishing插件的判断问题对错的小游戏
### 如何使用？
1. 下载本插件
2. 将**CustomFishing**插件与本插件放入服务器的`plugins`文件夹中
3. 重启服务器
4. 在`plugins`文件夹中找到`CustomFishing`文件夹，进入`contents/minigames`文件夹，找到`default.yml`文件或添加自己的文件，增加以下配置
```yaml
chat-game: # 可随意替换为其他名称
  game-type: chat-game
  title: '<gray><key:key.use> if true; <red><key:key.jump><gray> if false.'
  subtitle: '{question}'
  chat-game:
    - question: "CustomFishing 是大默米出的插件吗？"
      right: false
    - question: "CustomFishing 是小默米出的插件吗？"
      right: true
    - question: "CustomCrops 是大默米出的插件吗？"
      right: false
    - question: "CustomCrops 是小默米出的插件吗？"
      right: true
```
5. 输入`/cf reload`重载插件