let orderItemsList = document.querySelector("#items");

function newOrder() {

  let itemName = document.querySelector('#items-names').value;
  if (!orderItemsList.innerHTML.trim()) {
    orderItemsList.innerHTML += `<div class="card" style="width: 100%;">
    <div class="card-header">
      Itens do pedido   
    </div>
    <ul class="list-group list-group-flush" id="item-list">
      <li class="list-group-item">${itemName}</li>
    </ul>
  </div>`;
  }
  else {
    let itemList = document.querySelector("#item-list");
    itemList.innerHTML += `<li class="list-group-item">${itemName}</li>`;
  }

}

function createOrder() {

  const clientOrder = document.querySelector(`#search-client`).value;
  const orderValue = parseFloat(document.querySelector("#order-value").value);
  const orderValuePaid = parseFloat(document.querySelector("#order-value-paid").value);
  const orderIsPaid = document.querySelector("#flexCheckIndeterminate").checked;
  const itemElements = document.querySelectorAll("#item-list li");
  const orderType = document.querySelector("#order-type").value;
  const pickUpDate = document.querySelector("#order-pick-up-date").value;

  let orderTypeParsed = orderType;
  switch (orderTypeParsed) {
    case "Afiação":
      orderTypeParsed = "SHARPENING";
      break;
    case "Mala":
      orderTypeParsed = "SUITCASE";
      break;
    case "Mochila":
      orderTypeParsed = "BACKPACK";
      break;
    case "Sapato":
      orderTypeParsed = "SHOES";
      break;
    case "Tênis":
      orderTypeParsed = "SNEAKER";
      break;
    case "Bolsa":
      orderTypeParsed = "HANDBAG";
      break;
  }

  const itemsList = Array.from(itemElements).map(item => ({
    name: item.textContent,
  
  }));

  let currentDate = new Date();
  let day = String(currentDate.getDate()).padStart(2, '0');
  let month = String(currentDate.getMonth() + 1).padStart(2, '0');
  let year = currentDate.getFullYear();
  let formattedDate = `${year}-${month}-${day}`;


  const order = {
    orderStatus: "ORDER_RECEIVED",
    itsPaid: orderIsPaid,
    totalPrice: orderValue,
    totalPaid: orderValuePaid,
    orderType: orderTypeParsed,
    itemsList: itemsList,
    client: { name: clientOrder },
    pickUpDate: pickUpDate,
    receivedDate: formattedDate,
  };

  console.log(order);

  fetch('http://localhost:8080/orders/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(order)
  })
    .then(response => {
      if (!response.ok) {
        showMessage('Erro ao criar pedido! <br><br> Verifique se todos os campos estão preenchidos corretamente!');
        throw new Error('Network response was not ok ' + response.statusText);
      }
      return response.json();
    })
    .then(data => {
      showMessage('Pedido criado com sucesso!');
      console.log('Order created successfully:', data);
    })
    .catch(error => {
      showMessage('Erro ao criar pedido! <br><br> Verifique se todos os campos estão preenchidos corretamente!');
      console.error('There was a problem with the fetch operation:', error);
    });

}