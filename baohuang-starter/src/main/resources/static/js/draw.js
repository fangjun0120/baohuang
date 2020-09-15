//重叠的露出部分比例
function getOverrideRatio() {
    return 0.3;
}

// 渲染所有牌，牌宽高比 3：4
function drawCards(region, cards, selectedSet, align) {
    clearRegion(region)
    let cardHeight = region.height * 0.75;
    let cardWidthDelta = cardHeight * 0.75 * getOverrideRatio();
    let cardListWidth = cardWidthDelta * (cards.length - 1) + cardHeight * 0.75;
    // 宽度是否超了
    if (cardListWidth > region.width) {
        console.log("too long")
        cardListWidth = region.width;
        cardWidthDelta = cardListWidth / (cards.length - 1 + 1 / getOverrideRatio());
        cardHeight = cardWidthDelta / getOverrideRatio() / 0.75;
    }
    let xStart = region.x;
    if (align === "right") {
        xStart = xStart + region.width - cardListWidth;
    } else if (align === "mid") {
        xStart = xStart + region.width * 0.5 - cardListWidth * 0.5;
    }
    if (selectedSet != null) {
        mainCardDelta = cardWidthDelta;
        mainCardXOffset = xStart;
    }
    for (let i = 0; i < cards.length; i++) {
        let x = xStart + i * cardWidthDelta;
        let y = region.y + region.height * 0.5 - cardHeight * 0.5;
        if (selectedSet != null && selectedSet.has(i)) {
            y = region.y;
        }
        context.drawPokerCard(x, y, cardHeight, cards[i].suit, cards[i].rank);
        if (cards[i].isAgent) {
            context.strokeStyle('green');
            context.strokeRect(region.x, region.y, region.width, region.height);
        }
    }
}

function drawPortrait(region, image, name, isCurrent) {
    clearRegion(region)
    let width = Math.min(region.height * 0.5, region.width * 0.75);
    let x = region.x + region.width * 0.5 - width * 0.5;
    let y = region.y + region.height * 0.5 - width * 0.75;
    let img = new Image();
    img.onload = function() {
        context.drawImage(img, x, y, width, width);
    }
    img.src = image;
    let y2 = region.y + region.height * 0.5 + width * 0.5;
    context.font = Math.floor(width * 0.25 * 0.8) + "px Arial";
    context.fillText(name, x, y2);
    if (isCurrent) {
        context.strokeStyle('red');
        context.strokeRect(region.x, region.y, region.width, region.height);
    }
}

function drawCardBack(region) {
    let height = Math.min(region.height * 0.75, region.width / 3 * 4.0);
    let x = region.x + region.width * 0.5 - height * 0.75 * 0.5;
    let y = region.y + region.height * 0.5 - height * 0.5;
    context.drawPokerBack(x, y, height);
}

function drawButton(cancelText, submitText) {
    let pass = getPassButtonRegion();
    clearRegion(pass);
    context.strokeRect(pass.x, pass.y, pass.width, pass.height);
    drawText(pass.x + pass.width * 0.5, pass.y + pass.height * 0.5, cancelText, pass.height * 0.8);
    let submit = getSubmitButtonRegion();
    clearRegion(submit);
    context.strokeRect(submit.x, submit.y, submit.width, submit.height);
    drawText(submit.x + submit.width * 0.5, submit.y + submit.height * 0.5, submitText, submit.height * 0.8);
}

function drawText(centerX, centerY, text, size) {
    context.font = Math.floor(size) + "px Arial";
    context.textAlign = "center";
    context.fillText(text, centerX, centerY);
}

function clearButton() {
    clearRegion(getPassButtonRegion());
    clearRegion(getSubmitButtonRegion())
}

function clearRegion(region) {
    context.clearRect(region.x, region.y, region.width, region.height);
}

function drawBox(region) {
    context.strokeRect(region.x, region.y, region.width, region.height);
}