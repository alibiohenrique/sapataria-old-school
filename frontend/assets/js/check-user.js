let query = "";
function checkClient() {
    query = document.querySelector('#search-client').value;
    console.log(query);
    return searchClient();
}

function searchClient() {
    return fetch(`http://localhost:8080/clients/search/${query}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.status === 204) {
                showMessage(`Nenhum cliente encontrado com a pesquisa: <br><br> ${query}`);
                return null;
            }
            if (!response.ok) {
                showMessage('Erro ao buscar cliente!');
                throw new Error('An error occurred while fetching the client');
            }
            return response.json();
        })
        .then(data => {
            if (data) {
                showMessage(`Cliente encontrado com a pesquisa: <br><br> ${data.name}`);
                return data;
            }
        })
        .catch(error => {
            console.log('Error:', error.message);
            return null;
        });
}
