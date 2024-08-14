$(document).ready(function () {
    fetchClients();
});

function fetchClients() {
    fetch('http://localhost:8080/clients/all')
        .then(response => response.json())
        .then(data => {
            clearClients(); // Use 'clearClients' instead of 'cleanClients' for consistency
            displayClients(data);
        })
        .catch(error => console.error('Error fetching clients', error));
}

function clearClients() {
    const columns = ['client-name', 'client-phone-number', 'client-email', 'client-address', 'client-orders'];
    columns.forEach(columnID => {
        const column = document.getElementById(columnID);
        while (column.childNodes.length > 2) {
            column.removeChild(column.lastChild);
        }
    });
}

function displayClients(clients) {
    clients.forEach(client => {
        createClientElement(client);
    });
}

function createClientElement(client) {
    let nameDiv = document.createElement('div');
    nameDiv.textContent = client.name;

    let phoneDiv = document.createElement('div');
    phoneDiv.textContent = client.phoneNumber;

    let emailDiv = document.createElement('div');
    emailDiv.textContent = client.email;

    let addressDiv = document.createElement('div');
    addressDiv.textContent = client.address;

    let ordersDiv = document.createElement('div');
    ordersDiv.textContent = client.orderNumbers.join(', ');

    document.getElementById('client-name').appendChild(nameDiv);
    document.getElementById('client-phone-number').appendChild(phoneDiv);
    document.getElementById('client-email').appendChild(emailDiv);
    document.getElementById('client-address').appendChild(addressDiv);
    document.getElementById('client-orders').appendChild(ordersDiv);
}
