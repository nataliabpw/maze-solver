const uploadButton = document.getElementById("uploadButton");
const fileInput = document.getElementById("mazeFileInput"); 

const errorDiv = document.getElementById('errorContainer');
const mazeDiv = document.getElementById('mazeContainer');

uploadButton.addEventListener('click', uploadMazeFile);

function showErrors(message) {
    mazeDiv.innerHTML = '';

    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}

function clearErrors(){
    errorDiv.textContent = '';
    errorDiv.style.display = 'none';
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
    } catch (error) {

        showErrors('Network error');
    } finally {

        uploadButton.disabled = false;
    }

    function displayMaze(data){
        clearErrors();
        mazeDiv.innerHTML='Maze will be displayed here';
    }

}