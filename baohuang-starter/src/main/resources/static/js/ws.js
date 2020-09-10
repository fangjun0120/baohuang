function wsConnect() {
    stompClient = Stomp.client("ws://localhost:8080/endpoint/ws");

    // headers, connectCallback, errorCallback
    stompClient.connect({}, function() {
        wsSubscribe('/topic/player/' + roomId, function(response) {
            console.log("received message " + response);
            showResponse(response.body);
        }, {});
    }, function(error) {
        console.log("connect error: " + error);
    });
}

function wsDisconnect() {
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

function wsSubscribe(topic, callback, headers) {
    return stompClient.subscribe(topic, callback, headers);
}

function wsSendMessage() {
    let message = $('#to-message').val();
    send("/app/chat/" + roomId, {}, message);
}

function wsSendSystemMessage(message) {
    send("/app/system/" + roomId, {}, message);
}

function showResponse(message) {
    let area = $("#message-area");
    if (area.val() == null || area.val().length === 0) {
        area.val(message);
    } else {
        area.val(area.val() + "\n" + message);
    }
}