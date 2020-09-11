package jfang.games.baohuang.domain.card;

import lombok.Getter;

/**
 * 一句游戏的各个阶段
 *  1. 初始，等待各玩家加入
 *  2. 洗牌，发牌
 *  3. 买 7
 *  4. 选主公
 *  3. 一打四，起义
 *  4. 进行中，一手牌一手牌执行
 *  5. 结束，统计分数
 *  6. 阶段记分
 *
 * @author Jun
 * @date 2020/4/29
 */
public enum GameStageEnum {

    INIT(1),
    SHUFFLE(2),
    BUY_SEVEN(3),
    SELECT_KING(4),
    REVOLUTION(5),
    RUNNING(6),
    END(7),
    REPORT(8);

    @Getter
    private int value;

    GameStageEnum(int value) {
        this.value = value;
    }
}
