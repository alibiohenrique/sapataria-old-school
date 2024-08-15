function closeMessage() {
    document.querySelector("#user-message-container").style.display = "none";
    document.querySelector("#user-message-container").style.zIndex = "-1";
}

function showMessage(message) {
    const container = document.querySelector("#user-message-container");
    container.style.zIndex = "1";

    container.innerHTML = '';

    const messageElement = `  <div class="card-body text-center">
            <div class="user-message">
                <h5 class="card-title">${message}</h5>
                <button class="btn btn-danger" onclick="closeMessage()">Fechar</button>
            </div>
        </div>`;
    container.innerHTML = messageElement;
    container.style.display = "flex";

}

