// 各区域布局，横向分为20份，纵向分为25份

function getRegion(x, y, width, height) {
  return {
    x: canvas.width / 20.0 * x,
    y: canvas.height / 25.0 * y,
    width: canvas.width / 20.0 * width,
    height: canvas.height / 25.0 * height
  }
}

function getCardRegionByIndex(index) {
  if (0 === getRelativeIndex(index)) {
    return getMainCardRegion()
  }
  if (1 === getRelativeIndex(index)) {
    return getButtonLeftCardRegion()
  }
  if (2 === getRelativeIndex(index)) {
    return getTopLeftCardRegion()
  }
  if (-1 === getRelativeIndex(index)) {
    return getButtonRightCardRegion()
  }
  if (-2 === getRelativeIndex(index)) {
    return getTopRightCardRegion()
  }
}

function getPortraitByIndex(index) {
  if (0 === getRelativeIndex(index)) {
    return getMainPortraitRegion()
  }
  if (1 === getRelativeIndex(index)) {
    return getButtonLeftPortraitRegion()
  }
  if (2 === getRelativeIndex(index)) {
    return getTopLeftPortraitRegion()
  }
  if (-1 === getRelativeIndex(index)) {
    return getButtonRightPortraitRegion()
  }
  if (-2 === getRelativeIndex(index)) {
    return getTopRightPortraitRegion()
  }
}

function getRelativeIndex(index) {
  let i = thisPlayer.index - index
  if (i > 2) {
    return i - 5
  }
  if (i < -2) {
    return i + 5
  }
  return i
}

function getDeckRegion() {
  return getRegion(3, 18, 17, 6)
}

function getMainPortraitRegion() {
  return getRegion(0, 18, 3, 6)
}

function getMainCardRegion() {
  return getRegion(7, 13, 5, 4)
}

function getTopLeftPortraitRegion() {
  return getRegion(0, 3, 2, 4)
}

function getTopLeftDeckRegion() {
  return getRegion(2, 3, 2, 4)
}

function getTopLeftCardRegion() {
  return getRegion(4, 3, 5, 4)
}

function getTopRightPortraitRegion() {
  return getRegion(18, 3, 2, 4)
}

function getTopRightDeckRegion() {
  return getRegion(16, 3, 2, 4)
}

function getTopRightCardRegion() {
  return getRegion(11, 3, 5, 4)
}

function getButtonLeftPortraitRegion() {
  return getRegion(0, 8, 2, 4)
}

function getButtonLeftDeckRegion() {
  return getRegion(2, 8, 2, 4)
}

function getButtonLeftCardRegion() {
  return getRegion(4, 8, 5, 4)
}

function getButtonRightPortraitRegion() {
  return getRegion(18, 8, 2, 4)
}

function getButtonRightDeckRegion() {
  return getRegion(16, 8, 2, 4)
}

function getButtonRightCardRegion() {
  return getRegion(11, 8, 5, 4)
}

function getControlRegion() {
  return getRegion(13, 16, 4, 1)
}

function getSubmitButtonRegion() {
  let region = getControlRegion();
  return {
    x: region.x + region.width * 0.55,
    y: region.y,
    width: region.width * 0.4,
    height: region.height
  }
}

function getPassButtonRegion() {
  let region = getControlRegion();
  return {
    x: region.x + region.width * 0.05,
    y: region.y,
    width: region.width * 0.4,
    height: region.height
  }
}