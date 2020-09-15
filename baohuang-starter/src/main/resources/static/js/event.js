function onSystemMessage(message) {
    if (game == null) {
        game = new Game(message.gameId)
    }
    if (thisPlayer == null) {
        let playerInfo = message.playerInfo.find(p => p.userId === userId)
        let player = new Player(playerInfo)
        thisPlayer = player
        game.addPlayer(player)
    }
    game.sync(message)
    if (message.playerOptions) {
        if (message.playOptions.data) {
            bootbox.alert({
                size: "small",
                title: message.playerOptions.message,
                message: message.playerOptions.data,
                callback: function() {}
            })
        } else {
            bootbox.confirm(message.playerOptions.message, function (result) {
                let option = { "value": message.playerOptions.value, "response": result }
                wsSendSystemMessage(null, option)
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
    if (thisPlayer.state < 2 || thisPlayer.state === 3) {
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
    let index = getIndex(x, y, region, cardList);
    //console.log(index);
    if (index < 0) {
        return;
    }
    if (selected.has(index)) {
        selected.delete(index);
    } else {
        selected.add(index);
    }
    clearRegion(region);
    drawCards(region, cardList, selected, "mid");
}

function onClickPass() {
    if (game.stage === 1) {
        let feedback = { "userId": userId, "ready": false }
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
    // let hand = [];
    // let newCardList = [];
    // for (let index = 0; index < cardList.length; index++) {
    //     if (selected.has(index)) {
    //         hand.push(cardList[index]);
    //     } else {
    //         newCardList.push(cardList[index]);
    //     }
    // }
    // cardList = newCardList;
    // let region = getDeckRegion();
    // clearRegion(region);
    // drawCards(region, cardList, selected, "mid");
    // submit
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