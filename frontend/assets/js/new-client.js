
function createClient() {

    const clientName = document.querySelector("#name").value;
    const clientEmail = document.querySelector("#email").value;
    const clientAddress = document.querySelector("#address").value;
    const clientPhone = document.querySelector("#phone-number").value;

    const client = {
        name: clientName,
        email: clientEmail,
        address: clientAddress,
        phoneNumber: clientPhone
    }

    console.log(client);

    fetch('http://localhost:8080/clients/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)    
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Client created successfully:', data);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });

}