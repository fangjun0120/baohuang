// 点击事件
function onClick(event) {
    let region = getDeckRegion();
    if (isInRegion(event.offsetX, event.offsetY, region)) {
        onCardSelect(event.offsetX, event.offsetY, region);
        return;
    }
    region = getPassButtonRegion();
    if (isInRegion(event.offsetX, event.offsetY, region)) {
        onClickPass();
    }
    region = getSubmitButtonRegion();
    if (isInRegion(event.offsetX, event.offsetY, region)) {
        onClickSubmit();
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
    context.clearRect(region.x, region.y, region.width, region.height);
    drawCards(region, cardList, selected, "mid");
}

function onClickPass() {
    console.log("pass");
}

function onClickSubmit() {
    let hand = [];
    let newCardList = [];
    for (let index = 0; index < cardList.length; index++) {
        if (selected.has(index)) {
            hand.push(cardList[index]);
        } else {
            newCardList.push(cardList[index]);
        }
    }
    console.log(hand);
    selected.clear();
    cardList = newCardList;
    let region = getDeckRegion();
    context.clearRect(region.x, region.y, region.width, region.height);
    drawCards(region, cardList, selected, "mid");
}

function connect() {
    // get user info, name, portrait
    drawPortrait(getMainPortraitRegion(), "../static/images/portrait0.jpg", "大哥的大");
}

function submit() {

}

function onPlayerConnected() {

}

function onPlayerDisconnected() {

}

function onCardUpdate() {

}