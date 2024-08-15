const options = { year: 'numeric', month: 'long', day: 'numeric', timeZone: 'UTC' };

$(document).ready(function () {
    fetchOrders();
});

function fetchOrders() {
    fetch('http://localhost:8080/orders/all')
        .then(response => response.json())
        .then(data => {
            clearOrders();
            displayOrders(data);

        })
        .catch(error => {
            console.error('Error fetching orders:', error)
        });
}

function clearOrders() {
    const columns = ['order-received', 'being-repaired', 'waiting-pick-up', 'done'];
    columns.forEach(columnId => {
        const column = document.getElementById(columnId);
        while (column.childNodes.length > 1) {
            column.removeChild(column.lastChild);
        }
    });
}

function displayOrders(orders) {
    orders.forEach(order => {
        let orderElement = createOrderElement(order);
        switch (order.orderStatus) {
            case 'ORDER_RECEIVED':
                document.getElementById('order-received').appendChild(orderElement);
                break;
            case 'BEING_REPAIRED':
                document.getElementById('being-repaired').appendChild(orderElement);
                break;
            case 'WAITING_PICK_UP':
                document.getElementById('waiting-pick-up').appendChild(orderElement);
                break;
            case 'DONE':
                document.getElementById('done').appendChild(orderElement);
                break;
        }
    });
}

function createOrderElement(order) {
    let orderDiv = document.createElement('div');
    let receivedDateFormatted;
    if (order.receivedDate) {
        let receivedDate = new Date(order.receivedDate);
        receivedDateFormatted = !isNaN(receivedDate.getDate()) ? receivedDate.toLocaleDateString("pt-BR", options) : "Data indisponível";
    } else {
        receivedDateFormatted = "Data indisponível";
    }
    orderDiv.className = 'card order';
    orderDiv.innerHTML = `
        <div class="accordion" id="accordion-${order.orderNumber}">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapse-${order.orderNumber}" aria-expanded="true" aria-controls="collapse-${order.orderNumber}">
                        Pedido #${order.orderNumber}
                    </button>
                </h2>
            
                <div id="collapse-${order.orderNumber}" class="accordion-collapse collapse">
                    <h5 class="card-title">${order.client.name}</h5>
                    <div class="card-header text-body-secondary">
                        ${receivedDateFormatted}
                    </div>
                    <div class="accordion-body">
                        <ul class="list-group">
                            ${order.itemsList ? order.itemsList.map(item => {
        const key = `checkbox-${order.orderNumber}-${item.id}`;
        const isChecked = localStorage.getItem(key) === 'true';
        return `
                                    <li class="list-group-item">
                                        <input class="form-check-input me-3" type="checkbox" value="${item.id}" id="checkbox-${order.orderNumber}-${item.id}" ${isChecked ? 'checked' : ''}>
                                        <label class="form-check-label stretched-link" for="checkbox-${order.orderNumber}-${item.id}">${item.name}</label>
                                    </li>`;
    }).join('') : 'Sem itens disponível'}
                            </ul>
                            <a href="https://wa.me/55${order.client.phoneNumber}?text=Olá,%20${order.client.name}.%20Seu%20pedido%20de%20reforma%20é%20%23${order.orderNumber}%20${order.orderStatus == 'WAITING_PICK_UP' ? '%20e%20está%20pronto%20para retirada.' : ''}" class="btn btn-primary">WHATSAPP</a> 
                            <div class="btn-group dropup" role="group">
                                <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                    Estado
                                </button>
                                <ul class="dropdown-menu" id="drop-menu-${order.orderNumber}">
                                    <li><a class="dropdown-item" href="#" data-status="BEING_REPAIRED">Em reforma</a></li>
                                    <li><a class="dropdown-item" href="#" data-status="WAITING_PICK_UP">Aguardando retirada</a></li>
                                   
                                    <li><a class="dropdown-item" href="#" data-status="DONE">Finalizado</a></li>
                                </ul>
                            </div>
                            
                            <div class="card-footer text-body-secondary mt-3">
                                ${order.itsPaid && order.totalPaid > order.totalPrice ? "Já pagou o sinal" : `Falta pagar: R$ ${Math.abs(order.totalPrice - order.totalPaid)}`}
                            </div>
                            <div class="card-footer text-body-secondary mt-3">
                                ${order.pickUpDate ? new Date(order.pickUpDate).toLocaleDateString("pt-BR", options) : "Data indisponível"} 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Add event listeners to each checkbox
    const checkboxes = orderDiv.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            handleCheckboxChange(order.orderNumber, this.value, this.checked);
        });
    });

    // Add event listener to dropdown items
    const dropdownItems = orderDiv.querySelectorAll('.dropdown-menu .dropdown-item');
    dropdownItems.forEach(item => {
        item.addEventListener('click', function () {
            const newStatus = this.getAttribute('data-status');
            updateOrderStatus(order.orderNumber, newStatus);
        });
    });

    return orderDiv;
}

function handleCheckboxChange(orderNumber, itemId, isChecked) {
    console.log(`Order Number: ${orderNumber}, Item ID: ${itemId}, Checked: ${isChecked}`);
    // Create a unique key for the checkbox state
    const key = `checkbox-${orderNumber}-${itemId}`;
    // Save the checkbox state to localStorage
    localStorage.setItem(key, isChecked);
}

function updateOrderStatus(orderNumber, newStatus) {
    fetch(`http://localhost:8080/orders/change-status/${orderNumber}`, {
        method: 'PUT', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(newStatus)
    })
        .then(response => {
            if (response.ok) {
                console.log(`Order ${orderNumber} status updated to ${newStatus}`);
                fetchOrders();
            } else {
                console.error('Error updating order status:', response.statusText);
            }
        })
        .catch(error => console.error('Error updating order status:', error));
}