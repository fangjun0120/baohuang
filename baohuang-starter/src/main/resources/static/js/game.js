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
            for (const player of message.playerInfo) {
                if (!this.players.has(player.index)) {
                    this.players.set(player.index, new Player(player))
                }
                this.players.get(player.index).sync(player)
            }
        }
        if (message.playerOptions) {

        }
    }

    set isOneOverFour(value) {
        console.log("oneOverFour")
    }

    set isRevolution(value) {
        console.log("revolution")
    }
}

class Player {

    constructor(playerInfo) {
        this.userId = playerInfo.userId
        this.username = playerInfo.username
        this.portrait = playerInfo.portrait
        this.index = playerInfo.index
        drawPortrait(getPortraitByIndex(this.index), this.portrait, this.username, false)
    }

    sync(playerInfo) {
        this.isKing = playerInfo.isKing
        this.hasRevolution = playerInfo.hasRevolution
        this.state = playerInfo.state
        this.cardList = playerInfo.cardList
        this.pass = playerInfo.pass
        this.lastHand = playerInfo.lastHand
        drawPortrait(getPortraitByIndex(this.index), this.portrait, this.username, this.state === 3)
        if (this.cardList) {
            drawCards(getDeckRegion(), this.cardList, selected, "mid");
        }
        if (this.lastHand) {
            drawCards(getCardRegionByIndex(playerInfo.index), this.cardList, selected, "mid");
        }
        if (this.state < 2 || this.state === 3) {
            drawButton("取消", "确定")
        } else {
            clearButton()
        }
    }
}