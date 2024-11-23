const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/customer-created', (greeting) => {
        showMessage(greeting.body);
    });
    stompClient.subscribe('/topic/new-customer-in-queue', (greeting) => {
        showMessage(greeting.body);
    });
    stompClient.subscribe('/topic/order-accepted', (greeting) => {
        showMessage(greeting.body);
    });
    stompClient.subscribe('/topic/corder-completed', (greeting) => {
        showMessage(greeting.body);
    });
    stompClient.subscribe('/topic/dish-preparation-started', (greeting) => {
        showMessage(greeting.body);
    });
    stompClient.subscribe('/topic/dish-preparation-completed', (greeting) => {
        showMessage(greeting.body);
    });
};


stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}


function showMessage(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});
