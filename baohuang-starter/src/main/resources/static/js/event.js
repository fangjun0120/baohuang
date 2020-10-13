function onConnect() {
    wsSendSystemMessage(null, option)
}

function onSystemMessage(message) {
    console.log(message)
    if (game == null) {
        game = new Game(message.gameId)
    }
    if (thisPlayer == null) {
        let playerInfo = message.playerInfo.find(p => "" + p.userId === userId)
        let player = new Player(playerInfo)
        thisPlayer = player
        game.addPlayer(player)
    }
    game.sync(message)
    if (message.playerOptions) {
        if (message.playerOptions.data) {
            bootbox.alert({
                size: "small",
                title: message.playerOptions.message,
                message: message.playerOptions.data,
                callback: function() {
                    let feedback = { "userId": userId }
                    submit(feedback, null)
                }
            })
            if (message.playerOptions.value === 2) {
                addRankRow()
            }
        } else {
            bootbox.confirm(message.playerOptions.message, function (result) {
                let option = { "value": message.playerOptions.value, "response": result }
                let feedback = { "userId": userId }
                submit(feedback, option)
            })
        }
    }
}

// 点击事件
function onClick(event) {
    let region = getDeckRegion();
    if (isInRegion(event.offsetX, event.offsetY, region)) {
        onCardSelect(event.offsetX, event.offsetY, region);
        return;
    }
    if (thisPlayer != null && thisPlayer.state < 2 || thisPlayer.state === 3) {
        region = getPassButtonRegion();
        if (isInRegion(event.offsetX, event.offsetY, region)) {
            onClickPass();
        }
        region = getSubmitButtonRegion();
        if (isInRegion(event.offsetX, event.offsetY, region)) {
            onClickSubmit();
        }
    }
}

function isInRegion(x, y, region) {
    if (y < region.y || y > region.y + region.height) {
        return false;
    }
    if (x < region.x || x > region.x + region.width) {
        return false;
    }
    return true;
}

// 给定点位于第几张牌的区域
function getIndex(x, y, region, cardList) {
    if (y < region.y || y > region.y + region.height) {
        return -1;
    }
    let relativeX = x - mainCardXOffset;
    if (relativeX < 0 || relativeX > mainCardDelta * (cardList.length + 3)) {
        return -1;
    }
    if (relativeX > mainCardDelta * (cardList.length - 1)) {
        return cardList.length - 1;
    }
    return Math.floor(relativeX / mainCardDelta);
}

function onCardSelect(x, y, region) {
    let index = getIndex(x, y, region, thisPlayer.cardList);
    if (index < 0) {
        return;
    }
    if (selected.has(index)) {
        selected.delete(index);
    } else {
        selected.add(index);
    }
    clearRegion(region);
    drawCards(region, thisPlayer.cardList, selected, "mid");
}

function onClickPass() {
    console.log("on click submit")
    if (game.stage === 1) {
        let feedback = { "userId": userId, "ready": false }
        submit(feedback, null)
        return
    }
    if (game.stage === 6) {
        let feedback = { "userId": userId, "pass": true }
        submit(feedback, null)
        return
    }
    selected.clear()
}

function onClickSubmit() {
    if (game.stage === 1) {
        let feedback = { "userId": userId, "ready": true }
        submit(feedback, null)
        return
    }
    let hand = [];
    for (let index of selected) {
        hand.push(thisPlayer.cardList[index])
    }
    let feedback = { "userId": userId, "selectedCards": hand }
    submit(feedback, null)
    selected.clear();
}

function submit(feedback, option) {
    let message = {}
    message.gameId = game.id
    message.source = "" + userId
    message.timestamp = new Date().getTime()
    message.stage = game.stage
    if (feedback != null) {
        message.playerCallback = feedback
    }
    if (option != null) {
        message.playerOptionResponse = option
    }
    wsSendSystemMessage(message)
}

function onPlayerAdded(index, name) {
    $('#head-' + index).text(name)
    clearTable()
}

function onPlayerLeft(index) {
    $('#head-' + index).text("" + index)
    clearTable()
}

function addRankRow() {
    let index = $('#rank-area tbody tr').length + 1
    $('#rank-area tbody tr').last().after(
        ['<tr><td>', index, '</td><td>',
            game.players[0].score, '</td><td>',
            game.players[1].score, '</td><td>',
            game.players[2].score, '</td><td>',
            game.players[3].score, '</td><td>',
            game.players[4].score, '</td></tr>'].join()
    )
}

function clearTable() {
    $('#rank-area tbody').empty()
}