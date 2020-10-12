class Game {

    constructor(id) {
        this.id = id
        this.players = new Map()
    }

    addPlayer(player) {
        this.players.set(player.index, player)
        this.players.get(player.index).sync(player)
    }

    sync(message) {
        this.stage = message.stage
        this.agentCard = message.agentCard
        this.isOneOverFour = message.isOneOverFour
        this.isRevolution = message.isRevolution
        this.currentPlayer = message.currentPlayer
        if (message.playerInfo) {
            let updateSet = new Set()
            for (const player of message.playerInfo) {
                if (!this.players.has(player.index)) {
                    this.players.set(player.index, new Player(player))
                } else {
                    this.players.get(player.index).sync(player)
                }
                updateSet.add(player.index)
            }
            // 不在说明退出了
            for (const pi of this.players.keys()) {
                if (!updateSet.has(pi)) {
                    this.players.get(pi).clear()
                    this.players.delete(pi)
                }
            }
        }
        let currentPlayerState = thisPlayer.state
        if (currentPlayerState === 0 || currentPlayerState === 1) {
            drawButton("Cancel", "Ready")
        } else if (currentPlayerState === 3) {
            drawButton("Cancel", "Submit")
        } else {
            clearButton()
        }
        if (message.playerOptions) {

        }
    }

    set isOneOverFour(value) {

    }

    set isRevolution(value) {

    }
}

class Player {

    constructor(playerInfo) {
        this.userId = playerInfo.userId
        this.username = playerInfo.username
        this.portrait = playerInfo.portrait
        this.index = playerInfo.index
        this.state = playerInfo.state
        drawPortrait(getPortraitByIndex(playerInfo.index), playerInfo.portrait, playerInfo.username, this.state === 0 || this.state === 3)
    }

    // 需要操作的时候画红框
    sync(playerInfo) {
        this.isKing = playerInfo.isKing
        this.hasRevolution = playerInfo.hasRevolution
        this.state = playerInfo.state
        this.cardList = playerInfo.cardList
        this.pass = playerInfo.pass
        this.lastHand = playerInfo.lastHand
        drawPortrait(getPortraitByIndex(this.index), this.portrait, this.username, this.state === 0 || this.state === 3)
        if (this.cardList) {
            drawCards(getDeckRegion(), this.cardList, selected, "mid");
        }
        if (this.lastHand) {
            drawCards(getCardRegionByIndex(playerInfo.index), this.lastHand, selected, "mid");
        } else {
            clearRegion(getCardRegionByIndex(playerInfo.index))
        }
    }

    clear() {
        clearRegion(getPortraitByIndex(this.index))
        clearRegion(getCardRegionByIndex(this.index))
    }
}