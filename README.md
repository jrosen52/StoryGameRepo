# StoryGameRepo

Fifth CS441 Project

For our fifth project, we were given the opportunity to make any kind of game we want. I decided to make a game that has some
kind of story to it, as I enjoy story games and we haven't done something like that before. However, I also wanted to create 
some kind of gameplay. I decided to make my game The Spooky Dungeon, in which the only control is moving around, with the story 
of looking for treasure in the dungeon, and trying to survive a wave of enemies if the incorrect chest is opened. After 3 waves,
the game ends. Admittedly the story is not as deep as I wanted it to, but with more time I think I could expand the story as much
as I want to.

To operate the game, there are 2 main classes: MainActivity and Story. The MainActivity file has the main class that controls
the canvas and everything within the canvas, and the story class controls the story elements and order in which they are triggered.
When the incorrect (blue) chest is selected, it will trigger a wave of spider enemies that will spawn arounf the dungeon area
and walk towards the other side of the dungeon. If you get hit by a spider, you lose one of 3 lives. This will happen 3 times
(if the blue chest is selected all 3 times) until the player wins. The second wave is snakes, and the final wave is spiders.

Here is documentation for each git commit, and any relative citations:

October 28: Initial commit to create the project

October 30: Added Story class and activity to control story functions

October 31: Updated MainActivity to create the gameview

November 1: Began crafting story elements

November 2: Updated Story class and implemented some features into MainActivity

November 3: Update MainActivity to use moving bitmaps, based upon http://gamecodeschool.com/android/coding-android-sprite-sheet-animations/

November 5: Added and implemented additional bitmaps

November 7: Updated Story and winning

November 9: Finalized story features and charcter movement

November 10: Added all images to canvas and created enemy waves.

