<a name="readme-top"></a>
  
<!-- PROJECT SHIELDS -->
<div>
<h2 align="center">Battleship CLI</h2>
<h4 align="center">Wilson Ramirez | wilson.ramirez98@gmail.com | Final Version: 1.0</h4>
</div>
  <p align="center">
    A Battleship game that runs in your computer's command-line interface (CLI)
</p>

## How to Play
Make sure to download the .jar file in the Releases! Follow the instructions on that page in order to run the program</br>

<b>Players: 2</b>

<b>Ship Placement Phase</b></br>
![image](https://github.com/user-attachments/assets/9c0448d1-12e9-42b1-b5ab-ddaf1fb9bb53)

* Each player is asked to place all of their ships on the battlefield</br>
* Each player gets the following 5 ships:
  - Aircraft Carrier (Length/Health of <b>5</b>)
  - Battleship (Length/Health of <b>4</b>)
  - Submarine (Length/Health of <b>3</b>)
  - Cruiser (Length/Health of <b>3</b>)
  - Destroyer (Length/Health of <b>2</b>)

* Each ship is placed using coordinates:
  - <b>COORDINATES MUST BE ENTERED IN THE FOLLOWING FORMAT:</b>
  - [<b>ROW][COLUMN] [ROW][COLUMN]</b> (Ex. `F3 F7`)
  - Each set of rows + columns represents the front and back of the ship respectively

* Once each player places all of their ships, it's game on!


<b>Battle Phase</b></br>
![image](https://github.com/user-attachments/assets/e1e1ff18-22e6-446d-a274-aa7929fb677e)

Win condition: One player must sink all 5 ships of the other player

* Each player takes a turn guessing where the other player's ship is located using a single coordinate
  - Ex. `A1`
 
* The topmost field is the other player's field, while the bottom field is the current player's field
* Each letter corresponds with a specific piece of information:
  - O = Your undamaged ship cells (displays only on the active player's field)
  - X = A damaged ship cell (displays on both players fields)
  - M = Missed shot (displays on both players fields)

* <b>The game is over when the first player manages to sink all 5 of the other player's ships</b>
