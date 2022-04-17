# EscapeFromCatPaws
this project is the final project for csi5175 course

**GUIs**:
- MainActivity.java:
  - We use this activity to generate the GUIs of the app entrance.
  - Show the app title and app theme.
  - Allow users to enter the app by tapping the bottom of the screen.
- HomePage.java:
  - We use this activity to generate the GUIs of the app lobby.
  - Users can choose actions they want to take in this scene.
  - Users can also check the introduction as well as information on the app by clicking the about button.
  - Set up auto changing for the screen brightness.

**Main features**:
- A 2D game: This game will allow users to control a ball of yarn and secure it without being destroyed by cat paws. When the game starts, cat paws will randomly appear on the screen toward random directions and users need to control the ball of yarn to avoid those paws. Every time the ball hits a cat paw it will lose some yarns and when it becomes empty, game over. The game can always continue as long as the ball is ‘alive’.
  - Related files:
    - Constants.java: we use this to save the global values like screen resolutions and current context.
    - PlayGame.java:
      - We use this activity to generate the GUIs for game entrance scenes.
      - Users can also generate color for their ball randomly in this scene by clicking the ball image(A context awareness feature).
      - Users can start the game by clicking the go button.
    - GameScene.java: this java class is used to connect the gameplay scene and the game entrance scene.
    - GamePanel.java: this main java class is used for generating a surfaceView for gameplay.
    - GameObject.java: this interface is used for all game objects in game.
    - Player.java:
      - The java class for creating the player and updating as well as drawing it into view.
      - Function for shrinking players based on player's health.
    - Obstacle.java:
      - The java class for creating cat paw and updating as well as drawing it.
      - Randomly pick cat paw from the database(AWS server).
      - Checking if the player hit the current cat paw or not.
    - ObstacleManager.java:
      - This java class is used  for controlling all cat paws and updating as well as drawing them into view.
      - Adding new cat paws as the game continues.
      - Get the user 's current score based on time last.
      - Checking if the player hit any cat paws or not.
    - MainThread.java: a custom thread for controlling game processes and handlers.
    - GameOver.java:
      - We use this activity to generate the GUIs for game over scene.
      - Users can check the score they got for the game.
      - Users can choose to restart the game by clicking the restart button.
      - Users can choose to share their score by clicking the share button.
      - Users can choose to save their score to the database(AWS server) by clicking the save score button.
      - Users can choose to go back to the homepage by clicking the leave button.

- Customize cat paws(obstacles) - A context awareness feature:
  - Pick colors from user uploaded images.
  - Generate cat paws based on those picked colors with template paws.
  - Related files:
    - EditImages.java:
      -   Allow users to upload their own image from their system album.
      -   Using Palette API to pick colors(3 colors) from user uploaded images.
      -   Generate cat paws(four paws every generating process) based on picked color and paws templates.
      -   Allow users to re-generate and select as well as save the cat paw they liked to the database(one cat paw one submission).

**Advanced features**:
- Light sensor data:
  - The app can track the brightness of the environment by using light sensor control.
  - The app will auto switch between day time mode and night mode theme based on current brightness.
- Share your score of current game:
  - We will generate an image including the current score that can be shared to other apps installed in the device by the user.
- Scoreboard that allow user to check their saved score and global score:
  - We set up a AWS server for store all user scores:
    - we used the device id(hardware ids) as the user id.
    - the user score will be saved as {userID, score} pair.
- Backgroud music:
  - Users can toggle background music in the lobby with a fab button.
  - Users can also toggle music by using the menu bar.
- Menu bar:
  - We made menu_bar.xml to initial a menu bar object as well as items in that bar for our app.
  - We implemented this menu object and created a menu bar for each scene.
- Push notification:
  - We added Firebase SDK and used Firebase console to send push notifications to the device that installed our app.

**Web Server:**
- Hosted on cloud. NodeJS server:
  - This server uses https to connect with the app and all scores and customized pictures are parsed by this server.
  - This server will read/write data from DB.

**Resource Reference:**
- All images are made by Zixun Xiang.
