# FishingQuestionGame

一个基于CustomFishing插件的判断问题对错的小游戏
### 如何使用？
1. 下载本Expansion
2. 将**CustomFishing**插件放入服务器的`plugins`文件夹中
3. 将本Expansion放入**CustomFishing**插件的`expansions`文件夹中
4. 在`plugins`文件夹中找到`CustomFishing`文件夹，进入`contents/minigames`文件夹，找到`default.yml`文件或添加自己的文件，增加以下配置
    ```yaml
    question-game:
      game-type: question-game
      title:
        true_or_false: '<gray><key:key.use> if true; <red><key:key.jump><gray> if false.'
        multiple_choice: '<gray>Chat the answer to the <red>chat box'
      subtitle:
        true_or_false: '{question}'
        multiple_choice: '{question}'
      question-game:
        - question: "CustomFishing 是大默米出的插件吗？"
          type: true_or_false
          right: false
        - question: "CustomFishing 是小默米出的插件吗？"
          type: true_or_false
          right: true
        - question: "CustomFishing 是谁出的插件？"
          type: multiple_choice
          right:
            - "XiaoMoMi"
            - "小默米"
    ```
5. 在`plugins`文件夹中找到`CustomFishing`文件夹，进入`game-groups.yml`文件，在games选项下面增加以下配置
   ```yaml
    mixed_accurate_click_group:
      groups:
        - accurate_click_group:6
        - rainbow_group:1
        # 以下是新增的配置
        - questiongame_group:1
    accurate_click_group:
      difficulty: 10~60
      time: 10~15
      games:
        - accurate_click_bar_1:3
        - accurate_click_bar_2:3
        - accurate_click_bar_3:3
        - accurate_click_bar_4:3
        - accurate_click_bar_5:3
        - accurate_click_bar_6:3
        - accurate_click_bar_7:3
        - accurate_click_bar_8:3
        - accurate_click_bar_9:3
    rainbow_group:
      difficulty: 10~60
      time: 10~15
      games:
        - rainbow_1:1
        - rainbow_2:1
        - rainbow_3:1
        - rainbow_4:1
        - rainbow_5:1
        - rainbow_6:1
        - rainbow_7:1
   # 以下是新增的配置
   questiongame_group:
      difficulty: 10~60
      time: 10~15
      games:
        - question-game:1
    ```
6. 输入`/cf reload`重载插件，然后 **Enjoy it!** 😀