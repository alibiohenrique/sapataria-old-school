let query = "";
function checkClient() {

   query = document.querySelector('#search-client').value; 
    console.log(query);
    searchClient();
}

function searchClient() {
    fetch(`http://localhost:8080/clients/search/${query}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.status === 204) { 
            console.log('No client found');
            return null; 
        }
        if (!response.ok) {
            throw new Error('An error occurred while fetching the client');
        }
        return response.json(); 
    })
    .then(data => {
        if (data) {
            console.log('Client found:', data);
            // Handle the client data
        }
    })
    .catch(error => {
        console.log('Error:', error.message);
    });
}