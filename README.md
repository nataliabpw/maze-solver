![MazeSolver](docs/MazeSolverBanner.png)

**Live Demo:** https://maze-solver.up.railway.app/

Maze Solver is a Spring Boot web application that solves mazes uploaded as text files.

The application uses the Breadth-First Search (BFS) algorithm to find the shortest path between start and end points selected by the user and visualizes the result in the maze.

## Demo
![App demo](docs/demo.gif)

## Features

- Upload a maze from a text file
- Visualize the maze in the browser
- Select start and end points interactively
- Find the shortest path using the Breadth-First Search (BFS) algorithm
- Display the resulting path directly in the maze

## Tech Stack

- Java
- Spring Boot
- REST API
- HTML / CSS / JavaScript
- Breadth-First Search (BFS)
- Maven

## API Endpoints

### Upload Maze

**POST** `/api/maze/upload`

Uploads a maze file and returns its structure for visualization.

Parameters:
- `file` - text file containing the maze

Response:
- `maze` - array of strings representing maze rows

### Solve Maze

**POST** `/api/maze/solve`

Solves the maze using the start and end points selected by the user.

Parameters:
- `file` - maze file
- `startX` - x coordinate of the start point
- `startY` - y coordinate of the start point
- `endX` - x coordinate of the end point
- `endY` - y coordinate of the end point

Response:
- `maze` -  maze with the calculated path marked

## Example Maze File

Example maze file content:
```
XXXXXXXXXXXXXXXXXXXXXXXXX
X     X     X   X       X
X XXXXX X X X X X XXXXX X
X     X X X   X X   X   X
X XXX X X XXXXX XXXXX XXX
X X X   X X     X   X   X
X X XXXXX X XXXXX X XXX X
X X     X X   X   X     X
X XXX X X XXX XXX XXXXX X
X X   X X   X   X X     X
X X XXXXXXX XXX X X XXXXX
X   X       X X   X     X
XXXXX XXXXXXX XXXXXXXXX X
X     X   X           X X
X XXXXX X X XXXXXXX X X X
X X     X X     X X X X X
X X XXXXX XXXXX X X XXX X
X X X   X X   X   X     X
X X X XXX X X XXX XXXXXXX
X X   X X   X   X   X   X
X XXX X X XXXXX XXX XXX X
X   X X X   X   X   X   X
X XXX X XXXXX XXX XXX X X
X     X       X       X X
XXXXXXXXXXXXXXXXXXXXXXXXX
```


- `X` - wall 
- ` ` - (space) - open path

## Project background
This project was created as a way to learn and explore Spring Boot.

It is based on an earlier university project implemented in Java Swing, which focused on solving mazes using graph algorithms. The backend logic responsible for maze representation and pathfinding was adapted from that project.

The current project focuses on building a REST API and web-based interfaces using Spring Boot.

It demonstrates how an existing algorithmic solution can be adapted into a modern web application using Spring Boot.

The original Swing project was developed together with a colleague and is available here:

https://github.com/ntnlbk/labirynt


## Project Status
The core functionality is complete, and the project is under active development with a focus on improving code quality, structure, and user experience.


