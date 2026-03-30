const uploadButton = document.getElementById("uploadButton");
const fileInput = document.getElementById("mazeFileInput"); 

const errorDiv = document.getElementById('errorContainer');
const mazeDiv = document.getElementById('maze');
const messageDiv = document.getElementById('messageContainer');

const chooseContainer = document.getElementById('chooseContainer');
const chooseStartButton = document.getElementById('chooseStartButton');
const chooseEndButton = document.getElementById('chooseEndButton');

const startInfo = document.getElementById('startInfo');
const endInfo = document.getElementById('endInfo');

const solveButton = document.getElementById('solveButton');

let mode = "normal";

let startCell = null;
let startX = -1;
let startY = -1;

let endCell = null;
let endX = -1;
let endY = -1;

uploadButton.addEventListener('click', uploadMazeFile);
solveButton.addEventListener('click', solveMaze);
solveButton.disabled = false;

function showErrors(message) {
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}

function clearErrors(){
    errorDiv.textContent = '';
    errorDiv.style.display = 'none';
}

function showMessages(message){
    messageDiv.textContent = message;
    messageDiv.style.display = 'block';
}

function cleanMessages(){
    messageDiv.textContent = '';
    messageDiv.style.display = 'none';
}

async function uploadMazeFile(){
    uploadButton.disabled = true;

    if (fileInput.files.length>1){
        showErrors("You can choose only one file!");
        return;
    }

    const file = fileInput.files[0];
    
    if (!file) {
        alert("Please select a file first");
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try{
        const response = await fetch('/api/maze/upload', {
            method: "POST",
            body: formData
        })
        ;
        const data = await response.json();
        if (!response.ok) {
            showErrors(data.error || 'Server error');
            return;
        }
        displayMaze(data);

        chooseContainer.style.display = 'block';

        chooseStartButton.addEventListener('click', chooseStart);
        chooseEndButton.addEventListener('click', chooseEnd);
    } catch (error) {
        showErrors('Network error');
        chooseContainer.style.display = 'none';
    } finally {
        uploadButton.disabled = false;
    }
}

function displayMaze(data){
    clearErrors();
    cleanMessages();
    mazeDiv.innerHTML="";

    const mazeLines = data.maze;

    const mazeWidth = mazeLines[0].length;
    const cellSize = 20;

    mazeDiv.style.gridTemplateColumns = `repeat(${mazeWidth}, ${cellSize}px)`;
    for (let y=0; y<mazeLines.length; y++){
        const line = mazeLines[y];
        for (let x=0; x<line.length; x++){
            let c;
            if (x==startX && y==startY){
                c = "P";
            } else if (x==endX && y==endY) {
                c = "K";
            } else {
                c = line[x];
            }
            
            const cell = document.createElement("div");

            cell.classList.add("cell");
            console.log("displayMaze working");

            switch(c){
                case "X":
                    cell.classList.add("wall");
                    break;
                case "O":
                    cell.classList.add("path");
                    break;
                case "P":
                    cell.classList.add("start");
                    break;

                case "K":
                    cell.classList.add("end");
                    break;

                default:
                    cell.classList.add("space");
            }
            cell.dataset.x = x;
            cell.dataset.y = y;
            cell.addEventListener('click', handleCellClick);
            mazeDiv.appendChild(cell);
        }
    }
}

function chooseStart(){
    cleanMessages();
    showMessages("Click on the desired location in the maze to select the start point");

    chooseEndButton.disabled = true;
    mode = "chooseStart";
}

function chooseEnd(){
    cleanMessages();
    showMessages("Click on the desired location in the maze to select the end point");

    chooseStartButton.disabled = true;
    mode = "chooseEnd";
}

function handleCellClick(event){
    if (mode=='normal'){
        return;
    }

    const cell = event.target;

    const x = parseInt(cell.dataset.x);
    const y = parseInt(cell.dataset.y);

    if (cell.classList.contains("wall")){
        showErrors("You can't choose a wall as start!");
        return;
    }

    if (mode=="chooseStart"){
        if (startCell != null){
            startCell.classList.remove('start');

            if (!startCell.classList.contains('space')){
                startCell.classList.add('space');
            }
        } 

        cell.classList.add('start');
        startCell = cell;
        startX = x;
        startY = y;

        startInfo.innerText = "Start: ("+x+", "+y+")";
        chooseEndButton.disabled=false;
    }

    if (mode=="chooseEnd"){
        if (endCell != null){
            endCell.classList.remove('end');

            if (!endCell.classList.contains('space')){
                endCell.classList.add('space');
            }
        }
        
        cell.classList.add('end');
        endCell = cell;
        endX = x;
        endY = y;

        endInfo.innerText = "End: ("+x+", "+y+")";   
        chooseStartButton.disabled=false;  
    }

    mode = "normal";
}

async function solveMaze(){
    solveButton.disabled = true;

    if (fileInput.files.length>1){
        showErrors("You can choose only one file!");
        solveButton.disabled = false;
        return;
    }

    const file = fileInput.files[0];
    
    if (!file) {
        showErrors("Please select a file first!");
        solveButton.disabled = false;
        return;
    }

    if (startCell==null){
        showErrors("Please select a start point!");
        solveButton.disabled = false;
        return;
    }

    if (endCell==null){
        showErrors("Please select an end point!");
        solveButton.disabled = false;
        return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('startX', startX);
    formData.append('startY', startY);
    formData.append('endX', endX);
    formData.append('endY', endY);

    try{
        const response = await fetch('/api/maze/solve', {
            method: "POST",
            body: formData
        })
        ;
        const data = await response.json();
        if (!response.ok) {
            showErrors(data.error || 'Server error');
            return;
        }
        displayMaze(data);

        chooseContainer.style.display = 'block';
    } catch (error) {
        showErrors('Network error');
    } finally {
        solveButton.disabled = false;
    }
}