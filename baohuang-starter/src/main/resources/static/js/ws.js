var stompClient = null;

function connect() {
    stompClient = Stomp.client("ws://localhost:8080/endpoint/ws");

    var headers = {"name": $("#name").val()}

    // headers, connectCallback, errorCallback
    stompClient.connect(headers, function() {
        console.log("connect callback");
        subscribe('/topic/player', function(response) {
            console.log("received message " + response);
            showResponse(response.body);
        }, {});
    }, function(error) {
        console.log("connect error: " + error);
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

// header is a map, message is a string
// JSON.stringify(object)
// JSON.parse(string)
function send(url, headers, message) {
    stompClient.send(url, headers, message);
}

function subscribe(topic, callback, headers) {
    return stompClient.subscribe(topic, callback, headers);
}

function sendMessage() {
    var message = $('#to-message').val();
    send("/app/chat", {}, message);
}

function showResponse(message) {
    var area = $("#message-area");
    if (area.val() == null || area.val().length === 0) {
        area.val(message);
    } else {
        area.val(area.val() + "\n" + message);
    }
}