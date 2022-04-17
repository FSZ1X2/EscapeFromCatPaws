# EscapeFromCatPaws
this project is the final project for csi5175 course

GUIs:
- MainActivity.java:
  - we use this activity for generate the GUIs of the app enterance.
  - show the app title and app theme.
- HomePage.java:
  - we use this activity for generate the GUIs of the app lobby.
  - users can choose actions they want to take in this scene.
  - users can also check the introduction as well as infromation of the app by click the about button.
  - set up auto changing for the screen brightness.

Main features:
- A 2D game:
  1. Users will control a yarn ball and secure it without being destroyed by cat paws.
  2. How long(seconds) user can secure the ball will be count as the score.
  3. Related files:
    - Constants.java: 
      - we use this to save the global values like screen resolutions and current context.
    - PlayGame.java: 
      - we use this activity to generate the GUIs for game entrance scene.
      - users can also also generate color for their ball randomly in this scene by click the ball image(A context awareness feature).
      - users can start game by click the go button.
    - GameScene.java:
      - this activity is used to connect the gameplay scene and the game entrance scene. 
    - GamePanel.java:
      - the main java class for generate a surfaceView for gameplay. 
    - GameObject.java:
      - an interface for all game object in game. 
    - Player.java:
      - the java class for creating the palyer and updating as well as drawing it into view.
      - shrinking player based on player's health.  
    - Obstacle.java:
      - the java class for creating cat paw and updating as well as drawing it.
      - randomly pick cat paw from the database(AWS server).
      - checking if player hit the current cat paw or not.
    - ObstacleManager.java:
      - the java class for controling all cat paws and updating as well as drawing them into view.
      - adding new cat paws as the game continue.
      - get user current score based on time last.
      - checking if player hit any cat paws or not.
    - MainThread.java:
      - a custom thread for controlling game process and handlers.
    - GameOver.java:
      -  we use this activity to generate the GUIs for game over scene.
      -  users can check the score they got for the game.
      -  users can choose to restart the game by click the restart button.
      -  users can choose to share their score by click the share button.
      -  users can choose to save their score to database(AWS server) by click save score button.
      -  users can choose to go back to homepage by click the leave button.

- Customize cat paws(obstacles) - A context awareness feature:
  1. Pick colors from user uploaded image.
  2. Generate cat paws based on those picked color with template paws.
  3. Related files:
    - EditImages.java:
      -   allow users to upload their own image from their system album.
      -   using Palette API to pick colors(3 color) from user uploaded image.
      -   generate cat paws(four paws every generating process) based on picked color and paws templates.
      -   allow users to re-generate and select as well as save the cat paw they liked to database(one cat paw one submission).

Advanced features:
- Light sensor data:
  - the app can track the brightness of the enviorment by using light sensor control. 
  - the app will auto switch between day time mode and night mode theme based on current brightness.
- Share your score of current game:
  -  we will generate a image including the current socre that can be shared to other app by user.
- Score board that allow user to check their saved score and global score:
  - we set up a AWS server for store all user scores:
    - we used the device id(hardware ids) as the user id.
    - the user score will be saved as {userID, score} pair.
- Bush notifications that will let users know if there is any update for the game：
  - daliy notification that show how many cat paws we currently have in database.
- Backgroud music:
  - users can toggle background music in lobbly with a fab button.
  - users can also toggle music by using the menu bar. 
- Menu bar:
  - we made menu_bar.xml to initial a menu bar object as well as items in that bar for our app.
  - we implemented this menu object and created menu bar for each scene.
- Push notification:
  - we added Firebase SDK and using Firebase console to send cloud message.
