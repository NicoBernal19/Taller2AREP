document.getElementById('imageForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const imageName = document.getElementById('imageName').value;
    const imageElement = document.getElementById('resultImage');

    try {
        const response = await fetch(`/images/${imageName}`);
        if (response.ok) {
            const imageBlob = await response.blob();
            const imageUrl = URL.createObjectURL(imageBlob);
            imageElement.src = imageUrl;
            imageElement.style.display = 'block';
        } else {
            alert('Imagen no encontrada.');
            imageElement.style.display = 'none';
        }
    } catch (error) {
        console.error('Error al obtener la imagen:', error);
    }
});

document.getElementById("getForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const responseDiv = document.getElementById("response");

    fetch(`/App/hello?name=${encodeURIComponent(name)}`)
        .then(response => response.text())  // Cambié json() por text()
        .then(data => {
            responseDiv.innerHTML = data;  // Mostramos el texto directamente
        })
        .catch(error => console.error("Error al obtener los datos:", error));
});

document.getElementById("postForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const data = {
        message: document.getElementById("message").value
    };

    try {
        const response = await fetch("/App/submit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        const responseData = await response.text();
        document.getElementById("postResponse").innerText = responseData;
    } catch (error) {
        console.error("Error en la solicitud POST:", error);
    }
});
